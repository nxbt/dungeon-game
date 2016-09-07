package com.dungeon.game;

import com.badlogic.gdx.Gdx;

public class KeyListener {
	public static boolean[] keysPressed = new boolean[255];
	public static boolean[] keysJustPressed = new boolean[255];
	
	public static void update() {
		for(int i = 0; i < 255; i++) {
			if(keysJustPressed[i]) keysJustPressed[i] = false;
			if(!keysPressed[i] && Gdx.input.isKeyPressed(i)) {
				keysJustPressed[i] = true;
				keysPressed[i] = true;
			}
			else if(!Gdx.input.isKeyPressed(i)) keysPressed[i] = false;
		}
	}
}
