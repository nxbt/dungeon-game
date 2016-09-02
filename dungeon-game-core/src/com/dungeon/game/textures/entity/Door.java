package com.dungeon.game.textures.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;
import com.dungeon.game.utilities.Spritesheet;

public class Door extends ProceduralTexture {

	public Door(int rotation, boolean locked) {
		super(32, 8, new Object[]{rotation, locked});
	}

	@Override
	public void generateTexture(Object[] args) {
		Pixmap texMap = new Pixmap(32, 8,Pixmap.Format.RGBA8888); //create pixmap
		Color doorColor = new Color(163f / 255f, 60f / 255f, 8f / 255f, 1);
		texMap.setColor(doorColor);
		texMap.fillRectangle(0, 0, 32, 8);
		texMap.setColor(new Color(doorColor.r*0.8f, doorColor.g*0.8f, doorColor.b*0.8f, 1));
		texMap.drawRectangle(0, 0, 32, 8);
		
			texMap.setColor(Color.BLACK);
			texMap.drawLine(13, 6, 18, 6);
			texMap.drawLine(14, 5, 17, 5);
			texMap.drawLine(15, 4, 16, 4);
			texMap.drawLine(14, 3, 17, 3);
			texMap.drawLine(14, 2, 17, 2);
			texMap.drawLine(15, 1, 16, 1);
		}
		
		texture = new Texture(Spritesheet.rotatePixmap(texMap, (Integer)(args[0])));
	}

}
