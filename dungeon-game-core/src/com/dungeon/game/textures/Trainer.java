package com.dungeon.game.textures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Trainer extends Person {

	public Trainer(){
		super();
		generateHelmetAndSword();
	}
	
	private void generateHelmetAndSword(){
		Pixmap texMap = texture.getTextureData().consumePixmap();
		int rayNum = 500;
		for(int i = 0; i < rayNum; i++){
			float angle = (float) (2f*Math.PI*i/rayNum);
			texMap.setColor(new Color(0.3f, 0.3f, 0.3f, 1));
			int length = 13;
			if((angle > Math.PI/24f && angle < Math.PI / 4f) || (angle > Math.PI*7f/4f && angle < Math.PI*47f/24f))length = 7;
			for(int k = 0; k < length; k++){
				texMap.setColor(0.1f+0.2f*k/13f, 0.1f+0.2f*k/13f, 0.1f+0.2f*k/13f, 1);
				texMap.drawPixel(16+(int)Math.round(Math.cos(angle)*k), 16+(int)Math.round(Math.sin(angle)*k));
			}
//			texMap.drawLine(16, 16, 16+(int)(Math.cos(angle)*length), 16+(int)(Math.sin(angle)*length));
		}
		texture = new Texture(texMap);
	}
}
