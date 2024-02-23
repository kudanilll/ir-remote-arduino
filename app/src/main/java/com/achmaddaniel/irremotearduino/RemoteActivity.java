package com.achmaddaniel.irremotearduino;

import com.achmaddaniel.irremotearduino.databinding.ActivityRemoteBinding;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Context;
import android.widget.Toast;

public class RemoteActivity extends AppCompatActivity {
	
	// Power IR Pattern
	private final int[] pattern = { // arduino: 0x511D424F
		9000, 4500, 560, 560, 560, 560, 560, 560, 560, 1690, 560, 1690, 560, 1690, 560, 560, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 560, 560, 1690, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 1690, 560, 560, 560, 1690, 560, 1690, 560, 4500, 560, 4500, 9000
	};
	
	private ActivityRemoteBinding binding;
	private Vibrator vibrator = null;
	private ConsumerIrManager consumer;
	private boolean enabled = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate and get instance of binding
		binding = ActivityRemoteBinding.inflate(getLayoutInflater());
		// Set content view to binding's root
		setContentView(binding.getRoot());
		
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		consumer = (ConsumerIrManager)getSystemService(Context.CONSUMER_IR_SERVICE);
		
		// Check if this device has 'IR SERVICE'
		if(consumer != null)
			if(consumer.hasIrEmitter())
				enabled = true;
		
		binding.button.setOnClickListener((view) -> {
			if(enabled) {
				vibrator.vibrate(60);
				consumer.transmit(37000, pattern);
			} else
				Toast.makeText(this, "This device does not have 'IR SERVICE'", Toast.LENGTH_SHORT).show();
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.binding = null;
	}
}