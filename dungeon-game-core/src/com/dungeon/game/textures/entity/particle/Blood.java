package com.dungeon.game.textures.entity.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;

public class Blood extends ProceduralTexture {

	public Blood() {
		super(4, 4, new Object[0]);
	}

	@Override
	public void generateTexture(Object[] args) {
		Pixmap texMap = new Pixmap(4,4,Pixmap.Format.RGBA8888); //create pixmap
		
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 4; y++){
				if(!((x == 0 && y == 0) || (x == 0 && y == 3) || (x == 3 && y == 0) || (x == 3 && y == 3)) && Math.random() > 0.3){
					texMap.setColor(new Color((float)(0.6f + Math.random()*0.3f), (float)(Math.random()*0.1f), (float)(Math.random()*0.1f), 0.5f + (float)(Math.random()*0.5f)));
					texMap.drawPixel(x, 3 - y);
				}
			}
		}
		texture = new Texture(texMap);

	}

}
