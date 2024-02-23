package com.achmaddaniel.irremotearduino;

import com.achmaddaniel.irremotearduino.databinding.ActivityRemoteBinding;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Context;

public class RemoteActivity extends AppCompatActivity {
	
	private final int MIN_NORMALIZED_VALUE = 1000;
	private final int MAX_NORMALIZED_VALUE = 9000;
	private final int[] powerIRPattern = { // arduino: 0x511D424F
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
				//consumer.transmit(37000, powerIRPattern);
				int[] rgb = { 255, 255, 0 };
				consumer.transmit(37000, convertRGBToIRPattern(rgb));
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.binding = null;
	}
	
	private int[] convertRGBToIRPattern(int[] rgb) {
		int[] pattern = new int[2 * rgb.length];
		for(int i = 0; i < rgb.length; i++) {
			pattern[i] = normalized(rgb[i], 0, 255, MIN_NORMALIZED_VALUE, MAX_NORMALIZED_VALUE);
			pattern[i + rgb.length] = 0;
		}
		return pattern;
	}
	
	private int normalized(int value, int fromLow, int fromHigh, int toLow, int toHigh) {
		return (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow) + toLow;
	}
}