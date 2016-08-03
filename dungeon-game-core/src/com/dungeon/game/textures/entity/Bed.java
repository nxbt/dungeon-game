package com.dungeon.game.textures.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;
import com.dungeon.game.utilities.MathUtils;

public class Bed extends ProceduralTexture {
	
	public Bed() {
		super(64, 32, new int[0]);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateTexture(int[] args) {
		
		int seed = (int) (Math.random()*1000);
		
		Pixmap texMap = new Pixmap(32,64,Pixmap.Format.RGBA8888); //create pixmap
		Color coversColor = new Color(0.3f+0.7f*(float)Math.random(), 0.3f+0.7f*(float)Math.random(), 0.3f+0.7f*(float)Math.random(), 1);
		Color pillowColor = new Color(0.6f+0.4f*(float)Math.random(), 0.6f+0.4f*(float)Math.random(), 0.6f+0.4f*(float)Math.random(), 1);
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 64; k++){
				float num = 0.9f+0.1f*MathUtils.noise2d(seed, i, k, 4);
				System.out.println(num);
				if(k < 48){ // covers
					if(i == 0 || i == 31 || k == 0 || k == 47){ // covers outline
						texMap.setColor(new Color(coversColor.r*0.7f*num, coversColor.g*0.7f*num, coversColor.b*0.7f*num, 1));
					}else{
						texMap.setColor(new Color(coversColor.r*num, coversColor.g*num, coversColor.b*num, 1));
					}
				}else{ // pillow
					if(i == 0 || i == 31 || k == 63){ // pillow outline
						texMap.setColor(new Color(pillowColor.r*0.7f*num, pillowColor.g*0.7f*num, pillowColor.b*0.7f*num, 1));
					}else{
						texMap.setColor(new Color(pillowColor.r*num, pillowColor.g*num, pillowColor.b*num, 1));
					}
					
				}
				texMap.drawPixel(i, 63-k);
			}
		}
		
		
		texture = new Texture(texMap); //create texture

	}

}
