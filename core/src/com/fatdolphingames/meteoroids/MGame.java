package com.fatdolphingames.meteoroids;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.fatdolphingames.helpers.AssetLoader;
import com.fatdolphingames.screens.SplashScreen;

public class MGame extends Game implements ApplicationListener {
	
	@Override
	public void create () {
		AssetLoader.start();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
