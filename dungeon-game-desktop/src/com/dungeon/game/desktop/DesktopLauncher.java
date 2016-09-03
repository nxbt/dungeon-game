package com.dungeon.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dungeon.game.DungeonGame;
import com.dungeon.game.GeneratorTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		boolean HYPERSPEED_ENGADGE = true;
		boolean launchGenTest = false;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		if(HYPERSPEED_ENGADGE)config.foregroundFPS = 1000;
//		config.fullscreen = false;
//		config.forceExit = true;  
		config.vSyncEnabled = false;
		if(launchGenTest) new LwjglApplication(new GeneratorTest(), config);
			else new LwjglApplication(new DungeonGame(), config);
	}
}
