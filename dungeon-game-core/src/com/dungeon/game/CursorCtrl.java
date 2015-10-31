package com.dungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

public class CursorCtrl {
	Cursor cursor;
	
	public int x;
	public int y;
	
	public CursorCtrl() {
		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("Crosshair.png")), 0, 0);
		
		this.x = Gdx.input.getX();
		this.y = Gdx.input.getY();
	}
	
	public void update() {
		Gdx.graphics.setCursor(cursor);
		
		this.x = Gdx.input.getX();
		this.y = Gdx.input.getY();
	}

}
