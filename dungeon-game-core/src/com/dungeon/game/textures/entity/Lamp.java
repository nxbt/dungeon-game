package com.dungeon.game.textures.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;

public class Lamp extends ProceduralTexture {

	public Lamp() {
		super(32, 32, new Object[0]);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateTexture(Object[] args) {
		Pixmap texMap = new Pixmap(32,32,Pixmap.Format.RGBA8888); //create pixmap
		float shadeRed = (float) Math.random();
		float shadeGreen = (float) Math.random();
		float shadeBlue = (float) Math.random();
		Color shadeColor = new Color(0.6f + 0.9f*(shadeRed/(shadeRed+shadeGreen+shadeBlue)),0.6f + 0.9f*(shadeGreen/(shadeRed+shadeGreen+shadeBlue)),0.6f + 0.9f*(shadeBlue/(shadeRed+shadeGreen+shadeBlue)),0.9f);
		Color lightColor = new Color(1, 1, 0.7f, 1);
		Color woodColor = new Color(110f / 255f, 50f / 255f, 10f/ 255f, 1);
		for(int i = 0; i < 16; i+=1){
			if(i < 3){
				texMap.setColor(new Color(lightColor.r,lightColor.g,lightColor.b,1));
				texMap.drawCircle(16, 16, i);
				
			}else if(i < 5){
				texMap.setColor(new Color(woodColor.r,woodColor.g,woodColor.b,1));
				texMap.drawCircle(16, 16, i);
				
			}else if(i >= 7 && i < 17){
				texMap.setColor(new Color(shadeColor.r*(0.3f + 0.75f*(1.5f-i/14f)),shadeColor.g*(0.3f + 0.75f*(1.5f-i/14f)),shadeColor.b*(0.3f + 0.75f*(1.5f-i/14f)),shadeColor.a));
				texMap.drawCircle(15, 16, i);
				texMap.drawCircle(16, 15, i);
				texMap.drawCircle(17, 16, i);
				texMap.drawCircle(16, 17, i);
			}
		}
		texture = new Texture(texMap);

	}

}
