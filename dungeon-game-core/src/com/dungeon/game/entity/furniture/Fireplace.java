package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.light.Light;
import com.dungeon.game.textures.tiles.FirePlace;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Fireplace extends Static {
	
	private int rotation;
	private FirePlace texture;

	public Fireplace(World world, float x, float y, int orientation) {
		super(world, x, y, 32, 32, "fireplace.png");
		
		Pixmap tempMap = new Pixmap(32, 32, Pixmap.Format.RGB888);
		if(!textures[0].getTextureData().isPrepared()) textures[0].getTextureData().prepare();
		Pixmap textPixmap = textures[0].getTextureData().consumePixmap();
		tempMap.drawPixmap(textPixmap, 0, 0);
		Pixmap rotated = Spritesheet.rotatePixmap(tempMap,orientation);
		sprite = new Texture(rotated);
		tempMap.dispose();
		//maybe seed code must change?
		this.rotation = orientation;
		solid = false;
		hitbox = new Polygon(new float[]{-2,-2,34,-2,34,34,-2,34});
		light = new Light(world, x, y, 500, 100, Light.ORANGE, 40, this);
		
		originX = 16;
		originY = 16;
		rotate = true;
		
		if(orientation == 0) {
			light.setOffset(0, -18);
		}
		else if(orientation == 1) {
			light.setOffset(-18, 0);
		}
		else if(orientation == 2) {
			light.setOffset(0, 18);
		}
		else if(orientation == 3) {
			light.setOffset(18, 0);
		}
		genVisBox();
	}

	@Override
	public void calc() {
//		texture.update();
//		sprite = texture.texture;
	}

	@Override
	public void post() {
	}
	
	public void genTexture(int seed){ // continuity is shit!
		texture =  new FirePlace(seed, (int)(x)/Tile.TS, (int)y/Tile.TS, rotation);
		sprite = texture.texture;
	}

}
