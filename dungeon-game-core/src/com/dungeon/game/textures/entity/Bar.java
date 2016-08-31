package com.dungeon.game.textures.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;
import com.dungeon.game.utilities.Spritesheet;

public class Bar extends ProceduralTexture {

	public Bar(int rotation) {
		super(32, 8, new Object[]{rotation});
	}

	@Override
	public void generateTexture(Object[] args) {
		Pixmap texMap = new Pixmap(32, 8,Pixmap.Format.RGBA8888); //create pixmap
		Color barColor = new Color(0.6f, 0.6f, 0.6f, 1);
		
		texMap.setColor(new Color(barColor.r*0.8f, barColor.g*0.8f, barColor.b*0.8f, 1));
		texMap.fillRectangle(3, 0, 2, 8);
		texMap.fillRectangle(11, 0, 2, 8);
		texMap.fillRectangle(19, 0, 2, 8);
		texMap.fillRectangle(27, 0, 2, 8);
		
		texMap.setColor(barColor);
		texMap.fillRectangle(0, 3, 32, 2);
		
		texture = new Texture(Spritesheet.rotatePixmap(texMap, (Integer)(args[0])));

	}

}
