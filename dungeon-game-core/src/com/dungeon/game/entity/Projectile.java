package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public abstract class Projectile extends Dynamic {
	
	private static int OFFSET = 40;
	
	protected float d_originX;
	protected float d_originY;
	public Projectile(int x, int y, float angle, float power) {
		super((int) (x+Math.cos((angle+135)/180*Math.PI)*OFFSET), (int) (y+Math.sin((angle+135)/180*Math.PI)*OFFSET));
		dx = (float) Math.cos((angle+135)/180*Math.PI)*power;
		dy = (float) Math.sin((angle+135)/180*Math.PI)*power;
		this.angle = angle;
		
	}

	@Override
	public void init() {
		solid = false;
		// TODO Auto-generated method stub

	}

	@Override
	public void calc(World world) {
		// TODO Auto-generated method stub

	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(/*Texture*/ sprite,/*x*/ x+d_offx,/*y*/ y+d_offy,/*originX*/d_originX,/*originY*/d_originY,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),false,false);
	}

}
