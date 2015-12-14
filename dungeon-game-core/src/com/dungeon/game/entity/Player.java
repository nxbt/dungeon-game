package com.dungeon.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.item.*;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Player extends Dynamic {
	public final int REACH = 4*Tile.TS;
	
	public Inventory inv;
	
	public Item hat;
	
	public Player(int x, int y) {
		super(x, y);
	}
	
	public void init() {
		name = "Player";
		
		maxLife = 100;
		maxStamina = 100;
		maxMana = 100;
		
		life = 33;
		stamina = 66;
		mana = 50;
		
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
		
		int[][] invLayout = new int[][] {
			//consumables
			new int[] {1, 8, 8},
			new int[] {1, 48, 8},
			new int[] {1, 88, 8},
			new int[] {1, 128, 8},
			new int[] {1, 168, 8},
			//inventory
			new int[] {0, 8, 48},
			new int[] {0, 48, 48},
			new int[] {0, 88, 48},
			new int[] {0, 128, 48},
			new int[] {0, 168, 48},
			new int[] {0, 8, 88},
			new int[] {0, 48, 88},
			new int[] {0, 88, 88},
			new int[] {0, 128, 88},
			new int[] {0, 168, 88},
			new int[] {0, 8, 128},
			new int[] {0, 48, 128},
			new int[] {0, 88, 128},
			new int[] {0, 128, 128},
			new int[] {0, 168, 128},
			new int[] {0, 8, 168},
			new int[] {0, 48, 168},
			new int[] {0, 88, 168},
			new int[] {0, 128, 168},
			new int[] {0, 168, 168},
			new int[] {0, 8, 208},
			new int[] {0, 48, 208},
			new int[] {0, 88, 208},
			new int[] {0, 128, 208},
			new int[] {0, 168, 208},
			//weapons
			new int[] {2, 208, 8},
			new int[] {2, 248, 8},
			//Armor
			new int[] {7, 208, 48},
			new int[] {6, 208, 88},
			new int[] {5, 208, 128},
			new int[] {4, 208, 168},
			new int[] {3, 208, 208},
			//Rings and Amulet
			new int[] {9, 248, 48},
			new int[] {9, 248, 88},
			new int[] {9, 248, 128},
			new int[] {9, 248, 168},
			new int[] {8, 248, 208},
		};
		
		inv = new Inventory(invLayout, "invBack.png", 10, 100, 0, 240, 288, 16);
		
		hat = inv.slot[35].item; 
		
		inv.slot[5].item = new Hat();
		inv.slot[6].item = new Crap();
		inv.slot[7].item = new Crap();
		inv.slot[8].item = new Crap();
		inv.slot[9].item = new Crap();
		inv.slot[10].item = new Crap();
		inv.slot[11].item = new Crap();
		inv.slot[12].item = new Crap();
		inv.slot[13].item = new Stick();
		inv.slot[14].item = new Stick();
		inv.slot[15].item = new Crap();
		inv.slot[16].item = new Crap();
		inv.slot[17].item = new Crap();
		inv.slot[18].item = new Crap();
		inv.slot[19].item = new Crap();
		
		light = new Light(this, 100);
	}
	
	public void calc(World world) {
		if(inv.slot[35].item != null) ((Equipable) inv.slot[35].item).update(world, this);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			if(world.hudEntities.indexOf(inv.graphic) == -1) {
				inv.graphic.open(world);
			}
			else inv.graphic.close(world);
		}
		
		if(world.mouse.x > x-world.cam.x+world.cam.WIDTH/2 && world.mouse.x < x+width-world.cam.x+world.cam.WIDTH/2 && world.mouse.y > y-world.cam.y+world.cam.HEIGHT/2 && world.mouse.y < y+height-world.cam.y+world.cam.HEIGHT/2) {
			if(world.mouse.rb_pressed) {
				if(world.hudEntities.indexOf(inv.graphic) == -1) {
					inv.graphic.open(world);
				}
				else inv.graphic.close(world);
			}
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) inp_lt = true;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) inp_rt = true;
		if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) inp_up = true;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) inp_dn = true;
	}
}