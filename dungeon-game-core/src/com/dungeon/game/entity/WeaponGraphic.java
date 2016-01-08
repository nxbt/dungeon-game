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
		//hitBox should be changed to a global private variable and set in the constructor!
		Polygon hitBoxSword = new Polygon(new float[]{x+width*0.6f,y+height*0.2f,x+width*0.8f,y+height*0.4f,x,y+height*1.1f,x-width*0.1f,y+height});
		hitBoxSword.setOrigin(x+width*0.9f,y+height*0.1f);
		hitBoxSword.rotate(angle);
		hitBoxSword.dirty();
		hitBoxSword = new Polygon(hitBoxSword.getTransformedVertices());
		Polygon hitBoxEntity;
		for(Entity e: world.entities){
			hitBoxEntity = new Polygon(new float[]{e.x,e.y,e.x+e.width,e.y,e.x+e.width,e.y+e.height,e.x,e.y+e.height});
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
