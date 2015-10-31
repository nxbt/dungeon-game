package com.dungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Cursor {
	public int x;
	public int y;
	private int state;
	private static final int CROSS = 0;
	private static final int INVEN = 1;
	private static final int HOVER = 2;
	private static final int HIDE = 3;
	private static final Texture TEXCROSS = new Texture("Crosshair.png"); 
	public Texture texture;
	public Cursor(){
		this.texture = TEXCROSS;
		this.x = 0;
		this.x = 0;
	}
	
	public void changeState(int state){
		this.state = state;
	}
	
	public void update(){
		this.x = Gdx.input.getX();
		this.y = Gdx.graphics.getHeight()-Gdx.input.getY();
		System.out.println("X: "+this.x);
		System.out.println("Y: "+this.y);
		
	};
}
