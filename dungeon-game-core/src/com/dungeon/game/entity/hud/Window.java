package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public abstract class Window extends Hud {
	private static final NinePatch WINDOW = new NinePatch(new Texture("window.png"), 2, 2, 14, 2);
	
	private static final NinePatch SCROLL_BAR = new NinePatch(new Texture("slot.png"), 2, 2, 2, 2);
	
	private ExitButton exitButton;
	
	protected BitmapFont font;
	
	protected boolean drag;
	
	protected float scroll;
	
	protected float dragOffX;
	protected float dragOffY;
	
	protected float contentHeight;
	
	public Window(World world, float x, float y) {
		super(world, x, y, 32, 32, "slot.png");
		
		scroll = 0;
		
		d_width = 100;
		d_height = 200;
		
		exitButton = new ExitButton(world, this);
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setUseIntegerPositions(false);
		font.setColor(Color.WHITE);
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
		if(x+d_width > world.cam.width) x = world.cam.width-d_width;
		if(y+d_height > world.cam.height) y = world.cam.height-d_height;
		
		if(world.hudEntities.indexOf(this) != world.hudEntities.indexOf(exitButton)+1) {
			world.hudEntities.remove(exitButton);
			world.hudEntities.add(world.hudEntities.indexOf(this), exitButton);
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && world.hudEntities.indexOf(this) == 1) {
			close();
		}
		
		if(world.player.fightMode)close();
		
		super.calc();
	}

	public void hovered() {
		scroll += world.mouse.scroll;
		
		if(world.mouse.x>x&&world.mouse.x<x+d_width&&world.mouse.y>y+d_height-14&&world.mouse.y<y+d_height&&world.mouse.lb_pressed){
			dragOffX = (world.mouse.x-x);
			dragOffY = (world.mouse.y-y);
			drag = true;
		}
		else{
			super.calc();
		}
	}
	
	public void draw(SpriteBatch batch) {
		WINDOW.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
		float scrollThickness = (d_height - 16)/contentHeight;
		if(scrollThickness < 1){
			float scrollHeight = ((scroll * 16) / (contentHeight))*(d_height - 16);
			SCROLL_BAR.draw(batch, x + d_width - 7, y + 2 + (d_height - 16) - scrollHeight - scrollThickness * (d_height - 16), 5, scrollThickness * (d_height - 16));
		}
		//draw the subEntities!
		for(int i = 0; i < subEntities.size(); i++){
			subEntities.get(i).draw(batch);
		}
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
}
