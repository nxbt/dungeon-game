package com.dungeon.game.textures.entity;

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
		type = (int) args[0];
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		if(type == -1)type = (int)(Math.random()*4);
		swtich(type){
			case 0:
				generatePlain(texMap, (int) args[1], (int) args[2]);
			break;
			case 1:
				generateChecks(texMap, (int) args[1], (int) args[2]);
			break;
			case 2:
				generateOrnimental(texMap, (int) args[1], (int) args[2]);
			break;
			case 3:
				generateAbstract(texMap, (int) args[1], (int) args[2]);
			break;
				
		}
		texture = new Texture(texMap)
	}
	
	public void generatePlain(Pixmap texMap, int width, int height){
		Color carpetColor = new Color((float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), 1);
		int noiseSeed = (int)(Math.random()*1000);
		for(int i = 0; i < width; i++){
			for(int k = 0; k < height; k++){
				if(i == 0 || k == 0 || i == width - 1 || k == height - 1){
					float num = MathUtils.reductiveNoise2d(noiseSeed, i, k, new float[]{8,4,2,1}, new float[]{4,3,2,1});
					texMap.setColor(new Color(carpetColor.r*(0.6f+0.2f*num), carpetColor.g*(0.6f+0.2f*num), carpetColor.b*(0.6f+0.2f*num), 1);
					
				}else{
					float num = MathUtils.reductiveNoise2d(noiseSeed, i, k, new float[]{8,4,2,1}, new float[]{4,3,2,1});
					texMap.setColor(new Color(carpetColor.r*(0.9f+0.2f*num), carpetColor.g*(0.9f+0.2f*num), carpetColor.b*(0.9f+0.2f*num), 1);
				}
				texMap.drawPixel(i, height - 1 - k); //is this right?
			}
		}
	}
	
	public void generateChecks(Pixmap texMap, int width, int height){
		Color color1 = new Color((float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), 1);
		Color color2 = new Color((float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), (float)(0.3f+0.3f*Math.random()), 1);
	}
	
	public void generateOrnimental(Pixmap texMap, int width, int height){
		
	}
	
	public void generateAbstract(Pixmap texMap, int width, int height){
		
	}

}