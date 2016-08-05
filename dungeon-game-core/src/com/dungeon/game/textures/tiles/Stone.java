package com.dungeon.game.textures.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtils;

public class Stone extends ProceduralTile {
	
	private static final Color[] stoneColors = new Color[]{
		new Color(66f / 255f, 66f / 255f, 66f / 255f,1),
		new Color(227f / 255f, 152f / 255f, 51f / 255f,1)
	};

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
		Random rand = new Random(seed+1);
		rand.nextFloat();
		Color stoneColor = stoneColors[(int) (rand.nextFloat()*(stoneColors.length))];
		float numR = 0.8f+0.4f*rand.nextFloat();
		float numG = 0.8f+0.4f*rand.nextFloat();
		float numB = 0.8f+0.4f*rand.nextFloat();
		stoneColor = new Color(stoneColor.r*numR, stoneColor.g*numG, stoneColor.b*numB, 1);
		
		int[][] nodes = new int[16][2];
		int[][] nodeZones = new int[16][2];
		int index = 0;
		for(int i = x*32-16; i < x*32+48; i+=16){
			for(int k = y*32-16; k < y*32+48; k+=16){
				int specialX, specialY;
				curX = i;
				curY = k;
				specialX = curX+2+MathUtils.getRandomFromSeedAndCords(seed,(int)(curX/16),(int)(curY/16)).nextInt(12);
				specialY = curY+2+MathUtils.getRandomFromSeedAndCords(seed*2,(int)(curX/16),(int)(curY/16)).nextInt(12);
				nodes[index] = new int[]{specialX,specialY};
				nodeZones[index] = new int[]{(int)(curX/16),(int)(curY/16)};
				index++;
			}
		}
		
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 32; k++){
				curX = x*32+i;
				curY = y*32+k;

				int distX = curX + (int)(MathUtils.noise1d(seed, curY, 5)*5); //not pleased with this creates a repeating texture
				int distY = curY + (int)(MathUtils.noise1d(seed, curX, 5)*5);
				float[] nodeDists = new float[nodes.length];
				boolean isCrack = false;
				for(int j = 0; j < nodes.length; j++){
					int[] node = nodes[j];
					float dist = (float) Math.sqrt((distX-node[0])*(distX-node[0])+(distY-node[1])*(distY-node[1]));
					nodeDists[j] = dist;
				}
				int closestNode = 0;
				for(int j = 1; j < nodeDists.length; j++){
					if(nodeDists[j] < nodeDists[closestNode])closestNode = j;
				}
				for(int j = 0; j < nodeDists.length; j++){
					if(j != closestNode && Math.abs(nodeDists[j] - nodeDists[closestNode]) <= 1){
						isCrack = true;
						break;
					}
				}
				if(isCrack){
					float noise = MathUtils.noise2d(seed, curX, curY,1);
					texMap.setColor(new Color(stoneColor.r*(0.55f + 0.1f * noise), stoneColor.g*(0.55f + 0.1f * noise), stoneColor.b*(0.55f + 0.1f * noise), 1));
				}
				else{				
					float colorNum = MathUtils.getRandomFromSeedAndCords(seed, nodeZones[closestNode][0], nodeZones[closestNode][1]).nextFloat();
					Color tileColor = new Color(stoneColor.r*0.8f + colorNum*0.2f, stoneColor.g*0.8f + colorNum*0.2f, stoneColor.b*0.8f + colorNum*0.2f, 1);
					float noise = MathUtils.reductiveNoise2d(MathUtils.getRandomFromSeedAndCords(seed, nodeZones[closestNode][0], nodeZones[closestNode][1]).nextInt(1000), curX, curY, new float[]{8,4,2,1}, new float[]{4,3,2,1});
					texMap.setColor(new Color(tileColor.r*(0.8f+0.4f*noise), tileColor.g*(0.8f+0.4f*noise), tileColor.b*(0.8f+0.4f*noise), 1));
				}
				texMap.drawPixel(i, 31-k);
			}
		}
		texture = new Texture(texMap);

	}

}
