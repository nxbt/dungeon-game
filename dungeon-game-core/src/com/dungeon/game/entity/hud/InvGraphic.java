package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.Inventory;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.World;

public class InvGraphic extends Hud {
	Slot[] slot;
	
	Inventory inv;
	
	private int dragBar_x;
	private int dragBar_y;
	private int dragBar_width;
	private int dragBar_height;
	private boolean drag;
	private int dragOff_x;
	private int dragOff_y;
	
	public InvGraphic(String sprite, Inventory inv, int x, int y, int dragX, int dragY, int dragWidth, int dragHeight) {
		super(x, y);
		
		this.slot = inv.slot;
		
		this.sprite = new Texture(sprite);
		
		this.inv = inv;
		
		d_width = this.sprite.getWidth();
		d_height = this.sprite.getHeight();
		dragBar_x = dragX;
		dragBar_y = dragY;
		dragBar_width = dragWidth;
		dragBar_height = dragHeight;
	}

	@Override
	public void init() {
	}

	@Override
	public void calc(World world) {
		if(drag){
			x = world.mouse.x-dragOff_x;
			y = world.mouse.y-dragOff_y;
			
			if(world.hudEntities.indexOf(this) != 0) {
				world.hudEntities.remove(this);
				world.hudEntities.add(0, this);
			}
		}
		inv.update(world);
	}
	
	@Override
	public void hovered(World world) {
		if(world.mouse.x>x+dragBar_x&&world.mouse.x<x+dragBar_x+dragBar_width&&world.mouse.y>y+dragBar_y&&world.mouse.y<y+dragBar_y+dragBar_height&&world.mouse.lb_pressed){
			dragOff_x = (int) (world.mouse.x-x);
			dragOff_y = (int) (world.mouse.y-y);
			drag = true;
		}
		else{
			if(world.mouse.lb_released){
				drag = false;
			}
		inv.hovered(world);
		}
	}
	
	public void open(World world) {
		world.hudEntities.add(0, this);
	}
	
	public void close(World world) {
		world.hudEntities.remove(this);
		drag = false;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(sprite, x, y, d_width, d_height);
		
		for(Slot s: slot) {
			s.draw(batch, (int)x, (int)y);
		}
	}
}
