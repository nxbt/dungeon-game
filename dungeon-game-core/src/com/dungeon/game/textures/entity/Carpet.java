package com.dungeon.game.textures.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;
import com.dungeon.game.utilities.MathUtils;

public class Carpet extends ProceduralTexture {
	
	private static final int RANDOM = -1;
	private static final int PLAIN = 0;
	private static final int CHECKERED = 1;
	private static final int ORNIMENTAL = 2;
	private static final int ABSTRACT = 3;
	
	private int type;
	
	
	public Carpet(int width, int height, int type){
		super(16*width, 16*height, new Object[]{type, width, height});
		
	}
	
	public void generateTexture(Object[] args) {
		type = ((Integer) args[0]);
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		if(type == -1)type = (int)(Math.random()*4);
		switch(type){
			case 0:
				generatePlain(texMap);
			break;
			case 1:
				generateChecks(texMap);
			break;
			case 2:
				generateOrnimental(texMap);
			break;
			case 3:
				generateAbstract(texMap);
			break;
				
		}
		texture = new Texture(texMap);
	}
	
	public void generatePlain(Pixmap texMap){
		Color carpetColor = new Color((float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), 1);
		int noiseSeed = (int)(Math.random()*1000);
		for(int i = 0; i < width; i++){
			for(int k = 0; k < height; k++){
				if(i == 0 || k == 0 || i == width - 1 || k == height - 1){
					float num = MathUtils.reductiveNoise2d(noiseSeed, i, k, new float[]{8,4,2,1}, new float[]{4,3,2,1});
					texMap.setColor(new Color(carpetColor.r*(0.7f+0.2f*num), carpetColor.g*(0.7f+0.2f*num), carpetColor.b*(0.7f+0.2f*num), 1));
					
				}else{
					float num = MathUtils.reductiveNoise2d(noiseSeed, i, k, new float[]{8,4,2,1}, new float[]{4,3,2,1});
					texMap.setColor(new Color(carpetColor.r*(0.9f+0.2f*num), carpetColor.g*(0.9f+0.2f*num), carpetColor.b*(0.9f+0.2f*num), 1));
				}
				texMap.drawPixel(i, height - 1 - k); //is this right?
			}
		}
	}
	
	public void generateChecks(Pixmap texMap){
		Color color1 = new Color((float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), 1);
		Color color2 = new Color((float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), 1);
		Color borderColor = new Color((color1.r+color2.r)/2f, (color1.g+color2.g)/2f, (color1.b+color2.b)/2f, 1);
		int noiseSeed = (int)(Math.random()*1000);
		for(int i = 0; i < width; i++){
			for(int k = 0; k < height; k++){
				int xZone = i/16;
				int yZone = k/16;
				int check = (xZone+yZone)%2==0?0:1;
				Color activeColor;
				if(check == 0)activeColor = new Color(color1.r, color1.g, color1.b, 1);
				else activeColor = new Color(color2.r, color2.g, color2.b, 1);
				if(i % 16 == 0 || i % 16 == 15 || k % 16 == 0 || k % 16 == 15){
					activeColor.r = borderColor.r*0.9f;
					activeColor.g = borderColor.g*0.9f;
					activeColor.b = borderColor.b*0.9f;
					if(i == 0 || k == 0 || i == width - 1 || k == height - 1){
						activeColor.r*=0.8f;
						activeColor.g*=0.8f;
						activeColor.b*=0.8f;
					}
					
				}
				float num = MathUtils.reductiveNoise2d(noiseSeed, i, k, new float[]{8,4,2,1}, new float[]{4,3,2,1});
				texMap.setColor(new Color(activeColor.r*(0.9f+0.2f*num), activeColor.g*(0.9f+0.2f*num), activeColor.b*(0.9f+0.2f*num), 1));
				texMap.drawPixel(i, height - 1 - k);
			}
		}
		
	}
	
	public void generateOrnimental(Pixmap texMap){
		Color backGroundColor = new Color((float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), 1);
		Color borderColor = new Color((float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), 1);
		Color highLightColor1 = new Color((float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), 1);
		Color highLightColor2 = new Color((float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), 1);
		
		boolean goesUp = height == width?Math.random() < 0.5f:height > width;
		int borderSize = goesUp?width/5:height/5;
		int noiseSeed = (int)(Math.random()*1000);
		for(int i = 0; i < width; i++){
			for(int k = 0; k < height; k++){
				//so that the ornimental rug reflects across it's axis
				int actingI = i;
				if(goesUp && i > width / 2)actingI = width - 1 - i;
				int actingK = k;
				if(!goesUp && k > height / 2)actingK = height - 1 - k;
				Color activeColor;
				if((actingI < borderSize && actingI < actingK) || (actingI > width - 1 - borderSize && width - 1 - actingI < actingK)){
					//left/right border
					activeColor = borderColor;
				}else if(actingK < borderSize || actingK > height - 1 - borderSize){
					//top/bottom border
					activeColor = borderColor;
				}else{
					activeColor = backGroundColor;
				}
				float num = MathUtils.reductiveNoise2d(noiseSeed, i, k, new float[]{8,4,2,1}, new float[]{4,3,2,1});
				texMap.setColor(new Color(activeColor.r*(0.9f+0.2f*num), activeColor.g*(0.9f+0.2f*num), activeColor.b*(0.9f+0.2f*num), 1));
				texMap.drawPixel(i, height - 1 - k);
			}
		}
	}
	
	public void generateAbstract(Pixmap texMap){
		
	}

}
