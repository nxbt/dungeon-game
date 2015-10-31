package com.dungeon.game;
//package com.dungeon.game;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//
//public class Cursor {
//	private int state;
//	private static final int CROSS = 0;
//	private static final int INVEN = 1;
//	private static final int HOVER = 2;
//	private static final int HIDE = 3;
//	private static final Texture TEXCROSS = new Texture("Crosshair.png"); 
//	private static final Texture TEXINVEN = new Texture("Inven.png");
//	public Texture texture;
//	public Cursor(){
//		this.texture = TEXCROSS;
//	}
//	
//	public void changeState(int state){
//		this.state = state;
//	}
//	
//	public void update(){
//		switch(state){
//		case CROSS: this.texture = TEXCROSS;
//		break;
//		case INVEN: this.texture = TEXINVEN;
//		break;
//		}
//	}
//	public Texture getTexture(){
//		return texture;
//	}
//}
