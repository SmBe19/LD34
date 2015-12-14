package com.smeanox.games.ld34.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.LD34;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Consts.DEV_WIDTH;
		config.height = Consts.DEV_HEIGHT;
		config.vSyncEnabled = true; // Setting to false disables vertical sync
		config.foregroundFPS = 60; // Setting to 0 disables foreground fps throttling
		config.backgroundFPS = 60; // Setting to 0 disables background fps throttling
		new LwjglApplication(new LD34(), config);
	}
}
