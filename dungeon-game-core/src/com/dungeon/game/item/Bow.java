package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dungeon.game.entity.RangedGraphic;
import com.dungeon.game.world.Tile;

public class Bow extends Ranged {
	
	private final int REST = 0;
	private final int WINDUP = 1;
	private final int FIRE = 2;
	private final int WINDDOWN = 3;
	
	private final String texturePath;
	
	private Texture[] textures;

	public Bow(int damage, int cooldown, int speed) {
		super(damage, cooldown, speed, new Texture("Bow.png"));
		strength = 10;
		texturePath = "Bow.png";
		Texture tempSheet = new Texture(texturePath);
		
		int sheetWidth = tempSheet.getWidth()/Item.SIZE;
		int sheetHeight = tempSheet.getHeight()/Item.SIZE;
		
		TextureRegion[] spritesheet = new TextureRegion[sheetWidth * sheetHeight];
		
		for(int i = 0; i < sheetHeight; i++) {
			for(int k = 0; k < sheetWidth; k++) {
				spritesheet[i*sheetWidth+k] = new TextureRegion(new Texture(texturePath),k*Item.SIZE,i*Item.SIZE,Item.SIZE,Item.SIZE);
			}
		}
		
		textures  = new Texture[spritesheet.length];
		if (!tempSheet.getTextureData().isPrepared()) {
		    tempSheet.getTextureData().prepare();
		}
		Pixmap wholePixmap = tempSheet.getTextureData().consumePixmap();
		
		for(int i = 0; i < textures.length; i++){
			Pixmap tempMap = new Pixmap(Item.SIZE, Item.SIZE, Pixmap.Format.RGBA8888);
			for (int x = 0; x < spritesheet[i].getRegionWidth(); x++) {
			    for (int y = 0; y < spritesheet[i].getRegionHeight(); y++) {
			        int colorInt = wholePixmap.getPixel(spritesheet[i].getRegionX() + x, spritesheet[i].getRegionY() + y);
			        tempMap.drawPixel(x, y, colorInt);
			    }
			}
			textures[i] = new Texture(tempMap);
			tempMap.dispose();
		}
		wholePixmap.dispose();
		changeSprite(textures[0]);
	}

	@Override
	public boolean isInUse() {
		if(stage == WINDUP||stage == FIRE)return true;
		return false;
	}

	@Override
	public void init() {
		name = "Bow";

	}

	@Override
	public int[] getPos(boolean mousedown, boolean mousepressed) {
		int distance=0;
		int polarAngle= 0;
		int angle=0;
		stageTimer++;
		int constant=2;
		int index = stageTimer*constant;
		switch(stage){
		case REST:
		if(!sprite.equals(textures[0])){
			changeSprite(textures[0]);
		}
			if(mousepressed){
				stage=WINDUP;
				stageTimer = 0;
			}
			break;
			
		case WINDUP:
			if(index<45){
				if(!sprite.equals(textures[1])){
					changeSprite(textures[1]);
				}
			}
			else{
				if(!sprite.equals(textures[2])){
					changeSprite(textures[2]);
				}
			}
			
			if(!mousedown){
				stageTimer = 0;
				stage = FIRE;
				((RangedGraphic) graphic).fire(strength*Math.min(index, 45)/45);
			}
			break;
		case FIRE:
			if(!sprite.equals(textures[0])){
				changeSprite(textures[0]);
			}
			if(index>10){
				stageTimer = 0;
				stage = REST;
			}
			break;
		}

		return new int[]{distance,polarAngle,angle};
	}

}
