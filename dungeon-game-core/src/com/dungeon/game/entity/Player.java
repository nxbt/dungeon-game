package com.dungeon.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.item.*;
import com.dungeon.game.world.World;

public class Player extends Dynamic {
	public Inventory inv;
	
	public Player(int x, int y) {
		super(x, y);
		
		int[][] invLayout = new int[][] {
			new int[] {0, 0, 0},
			new int[] {0, 40, 0},
			new int[] {0, 80, 0},
			new int[] {0, 120, 0},
			new int[] {0, 160, 0},
			new int[] {0, 0, 40},
			new int[] {0, 40, 40},
			new int[] {0, 80, 40},
			new int[] {0, 120, 40},
			new int[] {0, 160, 40}
		};
		inv = new Inventory(invLayout);
		
		inv.slot[(int)(Math.random()*10)].item = new Crap();
		inv.slot[(int)(Math.random()*10)].item = new Crap();
		inv.slot[(int)(Math.random()*10)].item = new Crap();
	}
	
	public void init() {
		name = "Player";
		
		acel = 1.5f;
		mvel = 5;
		fric = 1;
		
		width = 26;
		height = 26;
		
		d_width = 32;
		d_height = 32;
		
		d_offx = -3;
		d_offy = -3;
		
		sprite = new Texture("Player.png");
		
		solid = true;
	}
	
	public void calc(World world) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			if(world.hudEntities.indexOf(inv.graphic) == -1) {
				world.hudEntities.add(inv.graphic);
			}
			else world.hudEntities.remove(inv.graphic);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) inp_lt = true;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) inp_rt = true;
		if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) inp_up = true;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) inp_dn = true;
	}
}