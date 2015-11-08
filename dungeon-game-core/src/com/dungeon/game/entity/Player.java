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
			new int[] {0, 8, 8},
			new int[] {0, 48, 8},
			new int[] {0, 88, 8},
			new int[] {0, 128, 8},
			new int[] {0, 168, 8},
			new int[] {0, 8, 48},
			new int[] {0, 48, 48},
			new int[] {0, 88, 48},
			new int[] {0, 128, 48},
			new int[] {0, 168, 48},
			new int[] {0, 8, 88},
			new int[] {0, 48, 88},
			new int[] {0, 88, 88},
			new int[] {0, 128, 88},
			new int[] {0, 168, 88}
		};
		inv = new Inventory(invLayout, "invBack.png");
		
		inv.slot[0].item = new Crap();
		inv.slot[1].item = new Crap();
		inv.slot[2].item = new Crap();
		inv.slot[3].item = new Crap();
		inv.slot[4].item = new Crap();
		inv.slot[5].item = new Crap();
		inv.slot[6].item = new Crap();
		inv.slot[7].item = new Stick();
		inv.slot[8].item = new Stick();
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