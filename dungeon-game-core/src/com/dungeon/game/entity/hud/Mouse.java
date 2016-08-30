package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Drop;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.hud.window.Window;
import com.dungeon.game.entity.particle.Ember;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Mouse extends Hud  implements InputProcessor {
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
	
	public int scroll;
	
	public boolean onHud;
	public boolean canPickup;
	private boolean canPlace;
	
	public boolean shift_down;
	
	private float sensitivity;
	
	public Slot slot;
	
	public Mouse(World world, float x, float y){
		super(world, x, y, 16, 16, "crosshair.png");
		
		Gdx.input.setCursorCatched(true);
		
		Gdx.input.setInputProcessor(this);
		
		sprite = CROSS;
		
		dWidth = 16;
		dHeight = 16;
		
		dOffX = -8;
		dOffY = -8;
		
		sensitivity = 0.5f;
		
		slot = new Slot(world, new int[] {0, 0, 0}, null);
		slot.renderSlot = false;
		clickable = false;
	}

	@Override
	public void calc() {
		x += Gdx.input.getDeltaX() * sensitivity;
		y -= Gdx.input.getDeltaY() * sensitivity;
		
		int screenWidth = (int) world.cam.width;
		int screenHeight = (int) world.cam.height;
		
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
		
		if(lb_down)for(int i = 0; i < 40; i++)world.entities.add(Ember.get(world, x+world.cam.x-world.cam.width/2, y+world.cam.y-world.cam.height/2, 90, 2));
		
		onHud = false;
		int toMoveToFront=0;
		for(int i = 0; i < world.hudEntities.size(); i++) {
			Hud ent = world.hudEntities.get(i);
			if(ent.isHovered()){
				if((lb_pressed||rb_pressed) && ent instanceof Window)toMoveToFront = i;
				ent.hovered();
				onHud = true;
				break;
			}
		}
		
		canPickup = (!onHud &&  Math.sqrt(Math.pow((x+world.cam.x-world.cam.width/2) - (world.player.x + world.player.dWidth/2), 2) + Math.pow((y+world.cam.y-world.cam.height/2) - (world.player.y + world.player.dHeight/2), 2)) <= world.player.REACH) && !world.player.fightMode;
		for(int i = 0; i< world.curFloor.tm.length;i++){
			for(int k = 0; k <world.curFloor.tm[i].length;k++){
				if(world.curFloor.tm[i][k].data==1){
					float[] verticies = new float[]{k*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS,(k)*Tile.TS,(i+1)*Tile.TS};
					if(Intersector.intersectSegmentPolygon(new Vector2(x+world.cam.x-world.cam.width/2,y+world.cam.y-world.cam.height/2), new Vector2(world.player.x,world.player.y), new Polygon(verticies))) canPickup = false;
				}
			}
		}
		canPlace = canPickup;
				
		world.hudEntities.add(0,world.hudEntities.get(toMoveToFront));
		world.hudEntities.remove(toMoveToFront+1);
		if(!onHud){
			for(int i = 0; i < world.entities.size(); i++) {
				Entity ent = world.entities.get(i);
				if(ent.isHovered()){
					if(canPlace)ent.hovered();
					canPlace = false;
					break;
				}
			}
		}
		
		if(slot.item != null && canPlace) {
			if(lb_pressed) {
				world.entities.add(new Drop(world, (x + world.cam.x-world.cam.width/2-Item.SIZE/2+16), (y+ world.cam.y-world.cam.height/2-Item.SIZE/2+16), slot));
				lb_pressed = false;
			}
			else if(rb_pressed) {
				Slot temp = new Slot(world, new int[] {0,0,0}, null);
				
				temp.item = slot.item.clone();
				temp.item.stack = 1;
				
				slot.item.stack--;
				
				if(slot.item.stack == 0) slot.item = null;
				
				world.entities.add(new Drop(world, (x + world.cam.x-world.cam.width/2-Item.SIZE/2+16), (y+ world.cam.y-world.cam.height/2-Item.SIZE/2+16), temp));
				rb_pressed = false;
				
				
			}
		}
		
		if (!world.player.fightMode) {
			sprite = ARROW;
			
			dOffX = -2;
			dOffY = -14;
		}
		else {
			sprite = CROSS;
			
			dOffX = -8;
			dOffY = -8;
		}
	}

	public void draw(SpriteBatch batch) {
		if(slot.item != null) {
			slot.draw(batch, (int)x-Item.SIZE/2, (int)y-Item.SIZE/2);
		}
		else super.draw(batch);
	}
	
	public void post() {
		scroll = 0;
	}

	@Override
	public boolean scrolled(int amount) {
		scroll += amount;
		
		return amount != 0;
	}
	
	@Override
	public boolean keyDown(int arg0) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}
}
