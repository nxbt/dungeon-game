package com.dungeon.game.textures.entity.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;

public class Ember extends ProceduralTexture {
	
	private static Color[] colors = new Color[]{
			new Color(1, 1, 50f / 255f, 1),
			new Color(1, 95f / 255f, 40f / 255f, 1),
			new Color(1, 140f / 255f, 40f / 255f, 1),
			new Color(1, 40f / 255f, 10f / 255f, 1)
	};

	public Ember() {
		super(2, 2, new Object[0]);
	}

	@Override
	public void generateTexture(Object[] args) {
		Pixmap texMap = new Pixmap(2,2,Pixmap.Format.RGBA8888); //create pixmap
		
		Color emberColor = colors[(int) (Math.random()*colors.length)];
		
		for(int x = 0; x < 2; x++){
			for(int y = 0; y < 2; y++){
				if(Math.random() > 0.5f){
					texMap.setColor(new Color((float) (emberColor.r * (0.8f+Math.random()*0.4f)), (float) (emberColor.g * (0.8f+Math.random()*0.4f)), (float) (emberColor.b * (0.8f+Math.random()*0.4f)), 0.7f + (float)(Math.random()*0.3f)));
					texMap.drawPixel(x, 1 - y);
				}
			}
		}
		texture = new Texture(texMap);

	}

}
