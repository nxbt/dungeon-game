package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Drop;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Mouse extends Hud {
	public final Texture CROSS = new Texture("crosshair.png");
	public final Texture ARROW = new Texture("pointer.png");
	
	public boolean lb_pressed;
	public boolean lb_released;
	public boolean lb_down;
	
	public boolean rb_pressed;
	public boolean rb_released;
	public boolean rb_down;
	
	public boolean mb_pressed;
	public boolean mb_released;
	public boolean mb_down;
	
	public boolean onHud;
	public boolean canPickup;
	private boolean canPlace;
	
	public boolean shift_down;
	
	private float sensitivity;
	
	public Slot slot;
	
	public Mouse(World world, int x, int y){
		super(world, x, y);
	}

	@Override
	public void init() {
		Gdx.input.setCursorCatched(true);
		
		sprite = CROSS;
		
		d_width = 16;
		d_height = 16;
		
		d_offx = -8;
		d_offy = -8;
		
		sensitivity = 0.5f;
		
		slot = new Slot(world, new int[] {0, 0, 0}, null);
		slot.renderSlot = false;
	}

	@Override
	public void calc() {
		x += Gdx.input.getDeltaX() * sensitivity;
		y -= Gdx.input.getDeltaY() * sensitivity;
		
		int screenWidth = (int) world.cam.WIDTH;
		int screenHeight = (int) world.cam.HEIGHT;
		
		if(x < 0) x = 0;
		else if(x > screenWidth) x = screenWidth;
		if(y < 0) y = 0;
		else if(y > screenHeight) y = screenHeight;
		
		if(!shift_down && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) shift_down = true;
		else if(shift_down && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) shift_down = false;
		
		//Check status of left mouse button
		if(Gdx.input.isButtonPressed(0)&&!lb_down){
			lb_down = true;
			lb_pressed = true;
			lb_released = false;
		}
		else if(!Gdx.input.isButtonPressed(0)&&lb_down){
			lb_down = false;
			lb_released = true;
			lb_pressed = false;
		}else{
			lb_pressed = false;
			lb_released = false;
		}
		
		//Check status of right mouse button
		if(Gdx.input.isButtonPressed(1)&&!rb_down){
			rb_down = true;
			rb_pressed = true;
			rb_released = false;
		}
		else if(!Gdx.input.isButtonPressed(1)&&rb_down){
			rb_down = false;
			rb_released = true;
			rb_pressed = false;
		}else{
			rb_pressed = false;
			rb_released = false;
		}
		
		//Check status of middle mouse button
		if(Gdx.input.isButtonPressed(2)&&!mb_down){
			mb_down = true;
			mb_pressed = true;
			mb_released = false;
		}
		else if(!Gdx.input.isButtonPressed(2)&&mb_down){
			mb_down = false;
			mb_released = true;
			mb_pressed = false;
		}else{
			mb_pressed = false;
			mb_released = false;
		}
		
		onHud = false;
		int toMoveToFront=0;
		for(int i = 0; i < world.hudEntities.size(); i++) {
			Hud ent = world.hudEntities.get(i);
			if(!(world.hudEntities.get(i) instanceof HudBackground) &&x > ent.x && x < ent.x+ent.d_width && y > ent.y && y < ent.y+ent.d_height){
				if((lb_pressed||rb_pressed) && ent instanceof Window)toMoveToFront = i;
				ent.hovered();
				onHud = true;
				break;
			}
		}
		
		canPickup = (!onHud &&  Math.sqrt(Math.pow((x+world.cam.x-world.cam.WIDTH/2) - (world.player.x + world.player.d_width/2), 2) + Math.pow((y+world.cam.y-world.cam.HEIGHT/2) - (world.player.y + world.player.d_height/2), 2)) <= world.player.REACH) && !world.player.fight_mode;
		for(int i = 0; i< world.curFloor.tm.length;i++){
			for(int k = 0; k <world.curFloor.tm[i].length;k++){
				if(world.curFloor.tm[i][k].data==1){
					float[] verticies = new float[]{k*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS,(k)*Tile.TS,(i+1)*Tile.TS};
					if(Intersector.intersectSegmentPolygon(new Vector2(x+world.cam.x-world.cam.WIDTH/2,y+world.cam.y-world.cam.HEIGHT/2), new Vector2(world.player.x,world.player.y), new Polygon(verticies))) canPickup = false;
				}
			}
		}
		canPlace = canPickup;
				
		world.hudEntities.add(0,world.hudEntities.get(toMoveToFront));
		world.hudEntities.remove(toMoveToFront+1);
		if(!onHud){
			for(int i = 0; i < world.entities.size(); i++) {
				Entity ent = world.entities.get(i);
				Polygon itemHBox = ent.getHitbox();
				if(Intersector.isPointInPolygon(itemHBox.getVertices(), 0,itemHBox.getVertices().length,x+world.cam.x-world.cam.WIDTH/2,y+world.cam.y-world.cam.HEIGHT/2)){
					ent.hovered();
					canPlace = false;
					break;
				}
			}
		}
		
		if(slot.item != null && canPlace) {
			if(lb_pressed) {
				world.entities.add(new Drop(world, (int)(x + world.cam.x-world.cam.WIDTH/2-Item.SIZE/2+16), (int)(y+ world.cam.y-world.cam.HEIGHT/2-Item.SIZE/2+16), slot));
				lb_pressed = false;
			}
			else if(rb_pressed) {
				Slot temp = new Slot(world, new int[] {0,0,0}, null);
				
				temp.item = slot.item.clone();
				temp.item.stack = 1;
				
				slot.item.stack--;
				
				if(slot.item.stack == 0) slot.item = null;
				
				world.entities.add(new Drop(world, (int)(x + world.cam.x-world.cam.WIDTH/2-Item.SIZE/2+16), (int)(y+ world.cam.y-world.cam.HEIGHT/2-Item.SIZE/2+16), temp));
				rb_pressed = false;
				
				
			}
		}
		
		if (!world.player.fight_mode) {
			sprite = ARROW;
			
			d_offx = -2;
			d_offy = -14;
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
	
	public void post() {}
}
