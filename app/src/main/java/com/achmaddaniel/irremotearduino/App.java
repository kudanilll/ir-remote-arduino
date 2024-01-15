package com.achmaddaniel.irremotearduino;

import com.google.android.material.color.DynamicColors;
import android.app.Application;

public class App extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		DynamicColors.applyToActivitiesIfAvailable(this);
	}
}