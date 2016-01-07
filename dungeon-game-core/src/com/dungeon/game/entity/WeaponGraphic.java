package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class WeaponGraphic extends Static {
	public float originX;
	public float originY;
	private Weapon weapon;
	public WeaponGraphic(Texture texture,int width, int height, Weapon weapon){
		super(height, height);
		this.width = width;
		this.height = height;
		this.sprite = texture;
		this.d_width = sprite.getWidth();
		this.d_height = sprite.getHeight();
		this.weapon = weapon;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void calc(World world) {
		Polygon hitBoxSword;
		Polygon hitBoxEntity;
		for(Entity e: world.entities){
			if(Intersector.overlapConvexPolygons(hitBoxSword, hitBoxEntity)){
				//target has been hit
			}
		}
	}
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(/*Texture*/ sprite,/*x*/ x+d_offx,/*y*/ y+d_offy,/*originX*/originX,/*originY*/originY,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),false,false);
	}
}
