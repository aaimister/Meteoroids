package com.fatdolphingames.meteoroids.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fatdolphingames.meteoroids.MGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Meteoroids";
		config.width = 1080 / 3;
		config.height = 1920 / 3;
		new LwjglApplication(new MGame(), config);
	}
}
