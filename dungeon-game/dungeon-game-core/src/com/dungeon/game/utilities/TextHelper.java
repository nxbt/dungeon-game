package com.dungeon.game.utilities;

public class TextHelper {
	public static final int FONT_SIZE = 9;
	
	public static int width(String text){
		return text.length()*FONT_SIZE;
	}
	
	public static float alignRight(String text, float x){
		return x-(float)width(text);
	}
	
	public static float alignCenter(String text, float x){
		return x-((float)width(text))/2f;
	}
}
