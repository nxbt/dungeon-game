package com.dungeon.game.entity.particle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public abstract class Particle extends Static {
	
	protected int duration;
	
	protected float dx;
	
	protected float dy;
	
	protected float alpha;

	public Particle() {
		super(null, 0, 0, 32, 32, "slot.png");
		layer = 4;
		clickable = false;
		rotate = true;
	}
	
	public void update() {
		calc();
	}

	@Override
	public void calc() {
		x+=dx;
		y+=dy;
		duration--;
		if(duration == 0){
			killMe = true;
			dispose();
		}

	}
	
	public void dead(){}
	
	public void set(World world, float x, float y, int duration, float dx, float dy){
		this.world = world;
		
		this.x = x;
		this.y = y;
		
		this.duration = duration;
		
		this.dx = dx;
		this.dy = dy;
		
		killMe = false;
		
		alpha = 1;
		
	}
	
	public abstract void dispose();
	

	
	public void draw(SpriteBatch batch) {
		batch.setColor(1, 1, 1, alpha);
		batch.draw(/*Texture*/ sprite,/*x*/ x-originX+dOffX,/*y*/ y-originY+dOffY,/*originX*/originX,/*originY*/originY,/*width*/ dWidth,/*height*/ dHeight,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),flipX,flipY);
		batch.setColor(1, 1, 1, 1);
	}

}
