package com.dungeon.game.textures.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Goon extends Person {
	
	public Goon(){
		super();
	}
	
	public void generateTexture(Object[] args){ // not done yet
		super.generateTexture(args);
		Pixmap texMap = texture.getTextureData().consumePixmap();
		
		Color hatColor = new Color((float) (0.3f + Math.random()*0.7f), (float) (0.3f + Math.random()*0.7f), (float) (0.3f + Math.random()*0.7f), 1);

		
		//his helmet
		int rayNum = 500;
		for(int i = 0; i < rayNum; i++){
			float angle = (float) (2f*Math.PI*i/rayNum);
			texMap.setColor(new Color(0.3f, 0.3f, 0.3f, 1));
			int length = 13;
			if(angle < Math.PI / 4f || angle > Math.PI*7f/4f)length = 11;
			for(int k = 0; k < length; k++){
				texMap.setColor(hatColor.r*(0.5f+0.1f*k/13f), hatColor.g*(0.5f+0.1f*k/13f), hatColor.b*(0.5f+0.1f*k/13f), 1);
				texMap.drawPixel(16+(int)Math.round(Math.cos(angle)*k), 16+(int)Math.round(Math.sin(angle)*k));
			}
		}
		
		texture = new Texture(texMap);
	}

}
