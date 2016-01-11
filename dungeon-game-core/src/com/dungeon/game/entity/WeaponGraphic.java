package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Weapon;

public abstract class WeaponGraphic extends Static {

	public float d_originX;
	public float d_originY;
	
	protected float originX;
	protected float originY;
	

	protected Weapon weapon;
	
	public WeaponGraphic(Weapon weapon) {
		super(0, 0); // x and y don't matter, they are set every frame
		sprite = weapon.sprite;
		
		this.width = Item.SIZE;
		this.height = Item.SIZE;
	
		this.d_width = Item.SIZE;
		this.d_height = Item.SIZE;
		// TODO Auto-generated constructor stub
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(/*Texture*/ sprite,/*x*/ x+d_offx,/*y*/ y+d_offy,/*originX*/d_originX,/*originY*/d_originY,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),false,false);
	}

}
