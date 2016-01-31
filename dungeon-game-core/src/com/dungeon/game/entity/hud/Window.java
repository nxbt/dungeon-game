package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public abstract class Window extends Hud {
	static final NinePatch WINDOW = new NinePatch(new Texture("window.png"), 1, 1, 14, 1);
	
	private ExitButton exitButton;
	
	boolean drag;
	
	float dragOffX;
	float dragOffY;
	
	public Window(World world, float x, float y) {
		super(world, x, y);
		
		d_width = 100;
		d_height = 200;
		
		exitButton = new ExitButton(world, this);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void calc() {
		if(drag){
			x = world.mouse.x-dragOffX;
			y = world.mouse.y-dragOffY;
			
			if(world.hudEntities.indexOf(this) != 0) {
				world.hudEntities.remove(this);
				world.hudEntities.add(0, this);
			}
		}
		
		if(world.mouse.lb_released){
			drag = false;
		}
		
		if(x < 0) x = 0;
		if(y < -d_height+14) y = -d_height+14;
		if(x+d_width > world.cam.WIDTH) x = world.cam.WIDTH-d_width;
		if(y+d_height > world.cam.HEIGHT) y = world.cam.HEIGHT-d_height;
		
		if(world.hudEntities.indexOf(this) != world.hudEntities.indexOf(exitButton)+1) {
			world.hudEntities.remove(exitButton);
			world.hudEntities.add(world.hudEntities.indexOf(this), exitButton);
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && world.hudEntities.indexOf(this) == 1) {
			close();
		}
		
		if(world.player.fightMode)close();
		
		subCalc();
	}

	public void hovered() {
		if(world.mouse.x>x&&world.mouse.x<x+d_width&&world.mouse.y>y+d_height-14&&world.mouse.y<y+d_height&&world.mouse.lb_pressed){
			dragOffX = (world.mouse.x-x);
			dragOffY = (world.mouse.y-y);
			drag = true;
		}
		else{
			subHovered();
		}
	}
	
	public void draw(SpriteBatch batch) {
		WINDOW.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
		
		subDraw(batch);
	}
	

	
	public void open() {
		world.hudEntities.add(0, this);
		world.hudEntities.add(0, exitButton);
	}
	
	public void close() {
		world.hudEntities.remove(this);
		world.hudEntities.remove(exitButton);
		drag = false;
	}
	
	protected void subCalc(){
		
	}
	protected void subHovered(){
		
	}
	protected void subDraw(SpriteBatch batch){
		
	}
}
