package com.dungeon.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Slot;
import com.dungeon.game.item.Stick;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Mouse extends Hud {
	
	public boolean lb_pressed;
	public boolean rb_pressed;
	private boolean down;
	
	public boolean onHud;
	public boolean canPickup;
	private boolean canPlace;
	
	public Slot slot;
	
	public Mouse(int x, int y) {
		super(x, y);
	}

	@Override
	public void init() {
		Gdx.input.setCursorCatched(true);
		
		sprite = new Texture("Crosshair.png");
		
		d_width = 16;
		d_height = 16;
		
		d_offx = -8;
		d_offy = -8;
		
		slot = new Slot(new int[] {0, 0, 0}, null);
		
		slot.item = new Stick();
	}

	@Override
	public void calc(World world) {
		x += Gdx.input.getDeltaX();
		y -= Gdx.input.getDeltaY();
		
		int screenWidth = (int) world.cam.WIDTH;
		int screenHeight = (int) world.cam.HEIGHT;
		
		if(x < 0) x = 0;
		else if(x > screenWidth) x = screenWidth;
		if(y < 0) y = 0;
		else if(y > screenHeight) y = screenHeight;
		
		if(Gdx.input.isButtonPressed(0) && !down) {
			down = true;
			lb_pressed = true;
		}
		else if(Gdx.input.isButtonPressed(1) && !down) {
			down = true;
			rb_pressed = true;
		}
		else {
			lb_pressed = false;
			rb_pressed = false;
		}
		if(!(Gdx.input.isButtonPressed(0) || Gdx.input.isButtonPressed(1))) {
			down = false;
		}
		
		onHud = false;
		
		for(Entity ent: world.hudEntities) {
			if(x > ent.x && x < ent.x+ent.d_width && y > ent.y && y < ent.y+ent.d_height) onHud = true;
		}
		
		canPickup = (!onHud &&  Math.sqrt(Math.pow((x+world.cam.x-world.cam.WIDTH/2) - (world.player.x + world.player.d_width/2), 2) + Math.pow((y+world.cam.y-world.cam.HEIGHT/2) - (world.player.y + world.player.d_height/2), 2)) <= world.player.REACH);
		for(int i = 0; i< world.curFloor.tm.length;i++){
			for(int k = 0; k <world.curFloor.tm[i].length;k++){
				if(world.curFloor.tm[k][i].data==1){
					float[] verticies = new float[]{i*Tile.TS,k*Tile.TS,(i+1)*Tile.TS,k*Tile.TS,(i+1)*Tile.TS,(k+1)*Tile.TS,(i)*Tile.TS,(k+1)*Tile.TS};
					if(Intersector.intersectSegmentPolygon(new Vector2(x+world.cam.x-world.cam.WIDTH/2,y+world.cam.y-world.cam.HEIGHT/2), new Vector2(world.player.x + world.player.d_width/2,world.player.y + world.player.d_height/2), new Polygon(verticies))) canPickup = false;
				}
			}
		}
		canPlace = canPickup;
		if(canPlace) {
			for(Entity ent: world.entities) {
				if(x > ent.x-world.cam.x+world.cam.WIDTH/2 && x < ent.x+ent.d_width-world.cam.x+world.cam.WIDTH/2 && y > ent.y-world.cam.y+world.cam.HEIGHT/2 && y < ent.y+ent.d_height-world.cam.y+world.cam.HEIGHT/2) canPlace = false;
			}
		}
		
		if(slot.item != null && canPlace) {
			if(lb_pressed) {
				world.entities.add(new Drop((int)(x + world.cam.x-world.cam.WIDTH/2-Item.SIZE/2), (int)(y+ world.cam.y-world.cam.HEIGHT/2-Item.SIZE/2), slot));
				lb_pressed = false;
			}
			else if(rb_pressed) {
				Slot temp = new Slot(new int[] {0,0,0}, null);
				
				temp.item = slot.item.clone();
				temp.item.stack = 1;
				
				slot.item.stack--;
				
				if(slot.item.stack == 0) slot.item = null;
				
				world.entities.add(new Drop((int)(x + world.cam.x-world.cam.WIDTH/2-Item.SIZE/2), (int)(y+ world.cam.y-world.cam.HEIGHT/2-Item.SIZE/2), temp));
				rb_pressed = false;
				
				
			}
		}
	}

	public void draw(SpriteBatch batch) {
		if(slot.item != null) {
			slot.draw(batch, (int)x-Item.SIZE/2, (int)y-Item.SIZE/2);
		}
		else batch.draw(sprite, x+d_offx, y+d_offy, d_width, d_height);
	}
}
