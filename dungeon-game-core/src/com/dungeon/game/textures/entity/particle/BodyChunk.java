package com.dungeon.game.textures.entity.particle;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;

public class BodyChunk extends ProceduralTexture {

	public BodyChunk(Pixmap pix, int x, int y) {
		super(4, 4, new Object[]{pix, x, y});
		
	}

	@Override
	public void generateTexture(Object[] args) {
		Pixmap pix = (Pixmap) args[0];
		int x = (Integer) args[1];
		int y = (Integer) args[2];
		Pixmap texMap = new Pixmap(4,4,Pixmap.Format.RGBA8888);
		
		texMap.drawPixmap(pix, x, pix.getHeight() - 4 - y, 4, 4, 0, 0, 4, 4);
		
		texture = new Texture(texMap);

	}

}
