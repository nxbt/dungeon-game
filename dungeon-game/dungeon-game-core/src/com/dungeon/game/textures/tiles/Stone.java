package com.dungeon.game.textures.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.DelaunayTriangulator;
import com.dungeon.game.utilities.MathUtils;

public class Stone extends ProceduralTile {

	public Stone(int seed, int x, int y) {
		super(new int[]{seed, x, y});
	}

	@Override
	public void generateTexture(int[] args) {
		seed = args[0];
		x = args[1];
		y = args[2];
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		int curX, curY;
		float[] verts = new float[]{5,6,9,3,31,7,16,24,6,19,18,4};
		DelaunayTriangulator triangulator = new DelaunayTriangulator();
		System.out.println(triangulator.computeTriangles(verts, false));
		
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 32; k++){
				curX = x*32+i;
				curY = y*32+k;
				boolean edge = MathUtils.perturbedGrid2d(seed, curX, curY, 16, 16, 16);
				if(edge)texMap.setColor(Color.BLACK);
				else texMap.setColor(Color.DARK_GRAY);
				texMap.drawPixel(i, 31-k);
			}
		}
		texture = new Texture(texMap);

	}

}
