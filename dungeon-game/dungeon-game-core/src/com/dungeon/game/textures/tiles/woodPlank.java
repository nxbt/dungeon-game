package com.dungeon.game.textures.tiles;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtils;

public class WoodPlank extends ProceduralTile {
	
	private Color color;
	
	private static final Color[] colors = new Color[]{
			new Color(102f / 255f, 51f / 255f, 0f / 255f,1),
			new Color(245f / 255f, 142f / 255f, 69f / 255f,1),
			new Color(122f / 255f, 88f / 255f, 7f / 255f,1),
			new Color(117f / 255f, 24f / 255f, 6f / 255f,1)
	};

	public WoodPlank(int seed, int x, int y, boolean left, boolean right, boolean bot, boolean top) {
		super(new int[]{seed, x, y, left?1:0, right?1:0, top?1:0, bot?1:0});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateTexture(int[] args) {
		seed = args[0];
		x = args[1];
		y = args[2];
		boolean left = args[3] == 1;
		boolean right = args[4] == 1;
		boolean top = args[5] == 1;
		boolean bot = args[6] == 1;
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		
		//variables
		Random seedConstant = new Random(seed);
		seedConstant.nextFloat();
		Random colorRand = new Random(seed);
		ArrayList<Color> remainingColors = new ArrayList<Color>();
		for(Color c: colors)remainingColors.add(c);
		color = remainingColors.remove(colorRand.nextInt(remainingColors.size()));
		int curX;
		int curY;
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 32; k++){
				curX = x*32+i;
				curY = y*32+k;
				Random rand = MathUtils.getRandomFromSeedAndX(seed,(int)(curX/8));
				int yOffset = rand.nextInt(32);
				int barrierZone = (int)((16f+curY+yOffset)/32f);
				int barrier = (int) (barrierZone*32f-8f+16f*MathUtils.getRandomFromSeedAndY(seed*(int)(curX/8),barrierZone).nextFloat());
				if((left && i == 0) || (right && i == 31) || (bot && k == 0) || (top && k == 31)){
					float num = MathUtils.noise2d(seed, curX, curY, 2);
					texMap.setColor(new Color(colors[0].r*0.77f+num*0.03f, colors[0].g*0.77f+num*0.03f, colors[0].b*0.77f+num*0.03f, 1));
				}
				else if((curX)%8==0 || (curX+1)%8==0 || curY+yOffset == barrier || curY+yOffset - 1 == barrier){
					float num = MathUtils.noise2d(seed, curX, curY, 2);
					texMap.setColor(new Color(colors[0].r*0.77f+num*0.03f, colors[0].g*0.77f+num*0.03f, colors[0].b*0.77f+num*0.03f, 1));
				}
				else{
					Color activeColor;
					float num;
					if(curY+yOffset > barrier){
						Random rand1 = MathUtils.getRandomFromSeedAndCords(seed, (int)(curX/8), 1+barrierZone);
						float numPlank = rand1.nextFloat();
						activeColor = new Color(color.r*0.9f + numPlank*0.1f, color.g*0.9f + numPlank*0.1f, color.b*0.9f + numPlank*0.1f, 1);
						num = MathUtils.perturbedSinNoise2d(MathUtils.getRandomFromSeedAndCords(seed, (int)(curX/8), (1+barrierZone)).nextInt(1000), curX, curY, 4, 3, 20);
					}else{
						Random rand1 = MathUtils.getRandomFromSeedAndCords(seed, (int)(curX/8), barrierZone);
						float numPlank = rand1.nextFloat();
						activeColor = new Color(color.r*0.9f + numPlank*0.1f, color.g*0.9f + numPlank*0.1f, color.b*0.9f + numPlank*0.1f, 1);
						num = MathUtils.perturbedSinNoise2d(MathUtils.getRandomFromSeedAndCords(seed, (int)(curX/8), (barrierZone)).nextInt(1000), curX, curY, 4, 3, 20);
					}
					texMap.setColor(new Color(activeColor.r*0.9f+num*0.1f, activeColor.g*0.9f+num*0.1f, activeColor.b*0.9f+num*0.1f, 1));
				}
				texMap.drawPixel(i, 31-k);
			}
		}

		texture = new Texture(texMap);
	}

}
