package com.mygames.flappybird;

import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		View gameView = initializeForView(new FlappyBird(), config);

		if (Build.VERSION.SDK_INT >= 35) {
			gameView.setOnApplyWindowInsetsListener((v, insets) -> {
				// Get the safe area (ignoring the notch/status bar)
				Insets systemBars = insets.getInsets(WindowInsets.Type.systemBars());
				// Apply padding to the view so the game stays in the "safe zone"
				v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
				return insets;
			});
		}

		setContentView(gameView);
	}
}