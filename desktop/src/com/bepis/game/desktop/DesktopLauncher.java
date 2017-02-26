package com.bepis.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bepis.game.Carl;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
//		config.width = 1600;
//		config.height = 900;
		
		
		
		config.foregroundFPS = 60;
		config.backgroundFPS = 15;
		
		new LwjglApplication(new Carl(), config);
	}
}
