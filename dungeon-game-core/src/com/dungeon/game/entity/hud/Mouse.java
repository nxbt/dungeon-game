package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Drop;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Slot;
import com.dungeon.game.item.Stick;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Mouse extends Hud {
	public final Texture CROSS = new Texture("Crosshair.png");
	public final Texture ARROW = new Texture("Inven.png");
	
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
		
		sprite = CROSS;
		
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
		int toMoveToFront=0;
		for(int i = 0; i < world.hudEntities.size(); i++) {
			Hud ent = world.hudEntities.get(i);
			if(x > ent.x && x < ent.x+ent.d_width && y > ent.y && y < ent.y+ent.d_height){
				if(lb_pressed||rb_pressed)toMoveToFront = i;
				ent.hovered(world);
				onHud = true;
				break;
			}
		}
		
		canPickup = (!onHud &&  Math.sqrt(Math.pow((x+world.cam.x-world.cam.WIDTH/2) - (world.player.x + world.player.d_width/2), 2) + Math.pow((y+world.cam.y-world.cam.HEIGHT/2) - (world.player.y + world.player.d_height/2), 2)) <= world.player.REACH);
		for(int i = 0; i< world.curFloor.tm.length;i++){
			for(int k = 0; k <world.curFloor.tm[i].length;k++){
				if(world.curFloor.tm[i][k].data==1){
					float[] verticies = new float[]{k*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS,(k)*Tile.TS,(i+1)*Tile.TS};
					if(Intersector.intersectSegmentPolygon(new Vector2(x+world.cam.x-world.cam.WIDTH/2,y+world.cam.y-world.cam.HEIGHT/2), new Vector2(world.player.x + world.player.d_width/2,world.player.y + world.player.d_height/2), new Polygon(verticies))) canPickup = false;
				}
			}
		}
		canPlace = canPickup;

		world.hudEntities.add(0,world.hudEntities.get(toMoveToFront));
		world.hudEntities.remove(toMoveToFront+1);
		for(int i = 0; i < world.entities.size(); i++) {
			Entity ent = world.entities.get(i);
			if(x + world.cam.x-world.cam.WIDTH/2 > ent.x && x + world.cam.x-world.cam.WIDTH/2 < ent.x+ent.d_width && y + world.cam.y-world.cam.HEIGHT/2 > ent.y && y + world.cam.y-world.cam.HEIGHT/2 < ent.y+ent.d_height){
				ent.hovered(world);
				canPlace = false;
				break;
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
		
		if (onHud) {
			sprite = ARROW;
			
			d_offx = 0;
			d_offy = -16;
		}
		else {
			sprite = CROSS;
			
			d_offx = -8;
			d_offy = -8;
		}
	}

	public void draw(SpriteBatch batch) {
		if(slot.item != null) {
			slot.draw(batch, (int)x-Item.SIZE/2, (int)y-Item.SIZE/2);
		}
		else batch.draw(sprite, x+d_offx, y+d_offy, d_width, d_height);
	}
}
