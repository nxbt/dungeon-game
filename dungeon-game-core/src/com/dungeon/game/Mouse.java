package com.dungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

public class Mouse {
	Cursor cursor;
	
	public int x;
	public int y;
	
	public Mouse() {
		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("Crosshair.png")), 0, 0);
		
		this.x = Gdx.input.getX();
		this.y = Gdx.graphics.getHeight() - Gdx.input.getY();
	}
	
	public void update() {
		Gdx.graphics.setCursor(cursor);
		
		this.x = Gdx.input.getX();
		this.y = Gdx.graphics.getHeight() - Gdx.input.getY();
	}

}
