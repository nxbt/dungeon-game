package com.dungeon.game.textures.tiles;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtils;

public class Dirt extends ProceduralTile {

	private Color color;
	private Color lightColor;
	private Color darkColor;
	
	private static final Color[] colors = new Color[]{
			new Color(130f/ 255f, 60f / 255f, 20f / 255f, 1),
			new Color(175f/ 255f, 75f / 255f, 32f / 255f, 1),
			new Color(105f/ 255f, 45f / 255f, 10f / 255f, 1),
			new Color(87f/ 255f, 8f / 255f, 8f / 255f, 1),
			new Color(43f/ 255f, 128f / 255f, 11f / 255f, 1),
			new Color(227f/ 255f, 159f / 255f, 41f / 255f, 1)
	};

	public Dirt(int seed, int x, int y) {
		super(new int[]{seed, x, y});
	}

	@Override
	public void generateTexture(int args[]) {
		seed = args[0];
		x = args[1];
		y = args[2];
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		Random colorRand = new Random(seed);
		ArrayList<Color> remainingColors = new ArrayList<Color>();
		for(Color c: colors)remainingColors.add(c);
		color = remainingColors.remove(colorRand.nextInt(remainingColors.size()));
		lightColor = remainingColors.remove(colorRand.nextInt(remainingColors.size()));
		darkColor = remainingColors.remove(colorRand.nextInt(remainingColors.size()));
		
		
		int curX, curY;
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 32; k++){
				curX = x*32+i;
				curY = y*32+k;
				float[] ran = getDirtColor(curX, curY);
				float[] ran1 = getDirtColor(curX-1, curY);
				float[] ran2 = getDirtColor(curX, curY-1);
				float[] ran3 = getDirtColor(curX+1, curY);
				float[] ran4 = getDirtColor(curX, curY+1);
				texMap.setColor(new Color((ran[0]*3 + ran1[0] + ran2[0] + ran3[0] + ran4[0])/7f,(ran[1]*3 + ran1[1] + ran2[1] + ran3[1] + ran4[1])/7f,(ran[2]*3 + ran1[2] + ran2[2] + ran3[2] + ran4[2])/7f, 1));
				
				//generate stones... somehow...
//				loop:
//					for(int j = -5; j < 5; j++){
//						for(int l = -5; l < 5; l++){
//							Random checker = MathUtils.getRandomFromSeedAndCords(seed, curX+j, curY+l);
//							checker.nextFloat();
//							checker.nextFloat();
//							if(checker.nextFloat() < 0.001f){
//								int stoneSize = checker.nextInt(5);
//								float dist = (float) Math.sqrt(j*j+l*l);
//								if(dist < stoneSize){
//									texMap.setColor(new Color(0.25f+dist/10f,0.25f+dist/10f,0.25f+dist/10f, 1));
//									break loop;
//								}
//							}
//						}
//					}
				texMap.drawPixel(i, 31-k);
			}
		}
		texture = new Texture(texMap);
	}
	
	private float[] getDirtColor(int xP, int yP){
		Random ran = MathUtils.getRandomFromSeedAndCords(seed,xP,yP);
		
		//check for dark dirt
		int darkDirtRadius = ran.nextInt(5);
		
		int lightDirtRadius = ran.nextInt(5);
		//well i did SOMETHING good here.... not stones but it'll be usefull haja
		for(int y = -Math.max(darkDirtRadius, lightDirtRadius); y < Math.max(darkDirtRadius, lightDirtRadius); y++){
			for(int x = -Math.max(darkDirtRadius, lightDirtRadius); x < Math.max(darkDirtRadius, lightDirtRadius); x++){
				Random checker = MathUtils.getRandomFromSeedAndCords(seed,xP+x,yP+y);
				//check for dark dirt
				if(checker.nextFloat() < 0.03f && Math.sqrt(x*x + y*y) < darkDirtRadius){
					return new float[]{darkColor.r*(0.7f+ran.nextFloat()*0.6f),darkColor.g*(0.7f+ran.nextFloat()*0.6f),darkColor.b*(0.7f+ran.nextFloat()*0.6f)};
				}
				//check for light dirt
				if(checker.nextFloat() < 0.03f && Math.sqrt(x*x + y*y) < lightDirtRadius){
					return new float[]{lightColor.r*(0.7f+ran.nextFloat()*0.6f),lightColor.g*(0.7f+ran.nextFloat()*0.6f),lightColor.b*(0.7f+ran.nextFloat()*0.6f)};
				}
			}
		}
		return new float[]{color.r*(0.7f+ran.nextFloat()*0.6f),color.g*(0.7f+ran.nextFloat()*0.6f),color.b*(0.7f+ran.nextFloat()*0.6f)};
		
	}
	

}
