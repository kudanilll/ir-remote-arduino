package com.achmaddaniel.irremotearduino;

import com.achmaddaniel.irremotearduino.databinding.ActivityRemoteBinding;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Context;

public class RemoteActivity extends AppCompatActivity {
	
	private ActivityRemoteBinding binding;
	private Vibrator vibrator = null;
	private ConsumerIrManager consumer;
	private boolean enabled = false;
	private final int[] pattern = {
		9000, 4500, 560, 560, 560, 560, 560, 560, 560, 1690, 560, 1690, 560, 1690, 560, 560, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 560, 560, 1690, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560, 1690, 560, 560, 560, 1690, 560, 1690, 560, 4500, 560, 4500, 9000
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate and get instance of binding
		binding = ActivityRemoteBinding.inflate(getLayoutInflater());
		// set content view to binding's root
		setContentView(binding.getRoot());
		
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		consumer = (ConsumerIrManager)getSystemService(Context.CONSUMER_IR_SERVICE);
		if(consumer != null)
			if(consumer.hasIrEmitter())
				enabled = true;
		
		binding.button.setOnClickListener((view) -> {
			if(enabled) {
				vibrator.vibrate(60);
				consumer.transmit(37000, pattern);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.binding = null;
	}
}