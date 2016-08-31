package com.dungeon.game.textures.entity.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;

public class Footprint extends ProceduralTexture {

	public Footprint() {
		super(8, 4, new Object[0]);	
	}

	@Override
	public void generateTexture(Object[] args) {
		Pixmap texMap = new Pixmap(8,4,Pixmap.Format.RGBA8888); //create pixmap
		
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 4; y++){
				if(!((x == 0 && y == 0) || (x == 0 && y == 3) || (x == 7 && y == 0) || (x == 7 && y == 3))){
					texMap.setColor(new Color(0, 0, 0, 0.3f));
					texMap.drawPixel(x, 3 - y);
				}
			}
		}
		texture = new Texture(texMap);

	}

}
