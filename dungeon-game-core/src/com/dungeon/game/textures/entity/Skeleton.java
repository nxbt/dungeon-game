package com.dungeon.game.textures.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;

public class Skeleton extends ProceduralTexture {

	public Skeleton() {
		super(32, 32, new Object[0]);
	}

	@Override
	public void generateTexture(Object[] args) {
		Pixmap texMap = new Pixmap(32,32,Pixmap.Format.RGBA8888); //create pixmap
		
		Color boneColor = new Color(0.9f, 0.9f, 0.9f, 1);
		
		for(int i = 0; i < 8; i++){
			int sX = (int) (Math.random()*32);
			int sY = (int) (Math.random()*32);
			int eX = (int) (sX+16f-Math.random()*32f);
			int eY = (int) (sY+16f-Math.random()*32f);
			texMap.setColor(new Color((float) (boneColor.r*(0.95f+0.1f*Math.random())), (float) (boneColor.g*(0.95f+0.1f*Math.random())), (float) (boneColor.b*(0.95f+0.1f*Math.random())), 1));
			texMap.drawLine(sX, sY, eX, eY);
			texMap.fillCircle(sX, sY, 1);
			texMap.fillCircle(eX, eY, 1);
		}
		
		texture = new Texture(texMap);
	}

}
