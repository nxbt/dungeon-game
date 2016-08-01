package com.dungeon.game.textures.tiles;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class FirePlace extends ProceduralTile {

	public FirePlace(int seed, int x, int y, int rotation) {
		super(new int[]{seed, x, y, rotation});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateTexture(int[] args) {
		seed = args[0];
		x = args[1];
		y = args[2];
		int rotation = args[3];
		Pixmap texMap = new Brick(seed, x, y, (rotation == 0 || rotation == 2)?2:1, 0).texture.getTextureData().consumePixmap();
		
		texture = new Texture(texMap);

	}

}
