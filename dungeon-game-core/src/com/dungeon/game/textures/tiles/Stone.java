package com.dungeon.game.textures.tiles;

import java.util.ArrayList;

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
		
		ArrayList<int[]> nodes = new ArrayList<int[]>();
		for(int i = -16; i < 48; i++){
			for(int k = -16; k < 48; k++){
				curX = x*32+i;
				curY = y*32+k;
				if(MathUtils.getRandomFromSeedAndCords(seed, curX, curY).nextFloat() < 0.01){
					nodes.add(new int[]{curX, curY});
				}
			}
		}
		
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 32; k++){
				curX = x*32+i;
				curY = y*32+k;
				float[] nodeDists = new float[nodes.size()];
				boolean isCrack = false;
				for(int j = 0; j < nodes.size(); j++){
					int[] node = nodes.get(j);
					float dist = (float) Math.sqrt((curX-node[0])*(curX-node[0])+(curY-node[1])*(curY-node[1]));
					nodeDists[j] = dist;
				}
				
				for(int j = 0; j < nodeDists.length; j++){
					for(int l = 0; l < nodeDists.length; l++){
						if(l != j && Math.abs(nodeDists[j] - nodeDists[l]) < 2)isCrack = true;
					}
				}
				if(isCrack)texMap.setColor(Color.BLACK);
				else texMap.setColor(Color.DARK_GRAY);
				texMap.drawPixel(i, 31-k);
			}
		}
		texture = new Texture(texMap);

	}

}
