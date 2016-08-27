package com.dungeon.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dungeon.game.DungeonGame;
import com.dungeon.game.GeneratorTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		boolean launchGenTest = false;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.useGL30 = true;
		if(launchGenTest) new LwjglApplication(new GeneratorTest(), config);
			else new LwjglApplication(new DungeonGame(), config);
	}
}
