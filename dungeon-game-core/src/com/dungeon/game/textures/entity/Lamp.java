package com.dungeon.game.textures.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;

public class Lamp extends ProceduralTexture {

	public Lamp() {
		super(32, 32, new int[0]);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateTexture(int[] args) {
		Pixmap texMap = new Pixmap(32,32,Pixmap.Format.RGBA8888); //create pixmap
		
		Color shadeColor = new Color(0.8f,0.8f,0.8f,1);
		for(int i = 7; i < 14; i+=1){
			texMap.setColor(new Color(shadeColor.r*(1.5f-i/14f),shadeColor.g*(1.5f-i/14f),shadeColor.b*(1.5f-i/14f),1));
			texMap.drawCircle(15, 16, i);
			texMap.drawCircle(16, 15, i);
			texMap.drawCircle(17, 16, i);
			texMap.drawCircle(16, 17, i);
		}
		texture = new Texture(texMap);

	}

}
