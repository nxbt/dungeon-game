package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.equipable.armor.WoolPants;
import com.dungeon.game.item.equipable.armor.WoolShirt;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public class Dresser extends Container {

	public Dresser(World world, float x, float y, int orientation) {
		super(world, x, y, 64, 32, "dresser.png");
		
		Pixmap tempMap = new Pixmap(64, 32, Pixmap.Format.RGB888);
		
		if(!textures[0].getTextureData().isPrepared()) textures[0].getTextureData().prepare();
		Pixmap textPixmap = textures[0].getTextureData().consumePixmap();
		tempMap.drawPixmap(textPixmap, 0, 0);
		Pixmap rotated = Spritesheet.rotatePixmap(tempMap,orientation);
		sprite = new Texture(rotated);
		tempMap.dispose();
		
		solid = true;
		
		d_width = orientation%2 == 0?64:32;
		d_height = orientation%2 == 0?32:64;
		
		rotate = true;
		
		origin_x = 16;
		origin_y = 32;
		if(orientation%2==1){
			hitbox = new Polygon(new float[]{0,0,32,0,32,64,0,64});
			origin_x = 16;
			origin_y = 32;
		}
		else{
			hitbox = new Polygon(new float[]{0,0,64,0,64,32,0,32});
			origin_x = 32;
			origin_y = 16;
		}
		
		for(int i = 0; i < inv.slot.length; i++){
			if(Math.random() < 0.10){
				if(Math.random() > 0.5)inv.slot[i].item = new WoolShirt(world, new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),0.5f));
				else inv.slot[i].item  = new WoolPants(world, new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),0.5f));
			}
		}
	}
}
