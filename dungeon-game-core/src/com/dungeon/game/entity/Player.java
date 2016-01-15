package com.dungeon.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Arrow;
import com.dungeon.game.item.Bow;
import com.dungeon.game.item.Crap;
import com.dungeon.game.item.Equipable;
import com.dungeon.game.item.Hat;
import com.dungeon.game.item.Inventory;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.RubberSword;
import com.dungeon.game.item.Stick;
import com.dungeon.game.item.Sword;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Player extends Dynamic {
	public final int REACH = 4*Tile.TS;
	
	public Inventory inv;
	
	public Item hat;

	private Weapon leftEquiped;
	private Weapon rightEquiped;
	
	private float[] leftPos;
	private float[] rightPos;
	
	private boolean attacking;
	
	public Player(int x, int y) {
		super(x, y);
	}
	
	public void init() {
		name = "Player";
		
		torq = 10;
		
		maxLife = 100;
		maxStam = 100;
		maxMana = 100;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		acel = 1.5f;
		mvel = 5;
		fric = 0.5f;
		
		hitbox = new Polygon(new float[]{30,16,28,23,23,28,16,30,9,28,4,23,2,16,4,9,9,4,16,2,23,4,28,9});
		
		origin_x = 16;
		origin_y = 16;
		
		d_width = 32;
		d_height = 32;
		
		d_offx = 0;
		d_offy = 0;
		
		sprite = new Texture("person.png");
		
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
		
		inv.slot[35].item = new Hat();
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
		inv.slot[19].item = new Arrow();
		inv.slot[20].item = new RubberSword();
		inv.slot[30].item = new Sword(10, 10,10);
		inv.slot[31].item = new  Bow(10, 10, 10);
		
		light = new Light(this, 1);
	}
	
	public void calc(World world) {
		target_angle = (float) (180/Math.PI*Math.atan2(world.mouse.y+world.cam.y-world.cam.HEIGHT/2-(y), world.mouse.x+world.cam.x-world.cam.WIDTH/2-(x)));
		
		if(leftEquiped != null && inv.slot[30].item==null) {
			unequip(world, leftEquiped);
			
			leftEquiped = null;
		}
		else if(leftEquiped == null && inv.slot[30].item != null) {
			leftEquiped = (Weapon) inv.slot[30].item;
			
			equip(world, leftEquiped);
		}
		else if(leftEquiped != inv.slot[30].item) {
			unequip(world, leftEquiped);
			
			leftEquiped = (Weapon) inv.slot[30].item;
			
			equip(world, leftEquiped);
		}
		
		if(rightEquiped != null && inv.slot[31].item==null) {
			unequip(world, rightEquiped);
			
			rightEquiped = null;
		}
		else if(rightEquiped == null && inv.slot[31].item != null) {
			rightEquiped = (Weapon) inv.slot[31].item;
			
			equip(world, rightEquiped);
		}
		else if(rightEquiped != inv.slot[31].item) {
			unequip(world, rightEquiped);
			
			rightEquiped = (Weapon) inv.slot[31].item;
			
			equip(world, rightEquiped);
		}
		
		if(inv.slot[35].item != null) ((Equipable) inv.slot[35].item).update(world, this);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			if(world.hudEntities.indexOf(inv.graphic) == -1) {
				inv.graphic.open(world);
			}
			else inv.graphic.close(world);
		}
		
		boolean inp_lt = false;
		boolean inp_rt = false;
		boolean inp_up = false;
		boolean inp_dn = false;
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) inp_lt = true;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) inp_rt = true;
		if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) inp_up = true;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) inp_dn = true;
		
		if(inp_up && inp_rt) move_angle = 45;
		else if(inp_up && inp_lt) move_angle = 135;
		else if(inp_dn && inp_rt) move_angle = -45;
		else if(inp_dn && inp_lt) move_angle = -135;
		else if(inp_up) move_angle = 90;
		else if(inp_dn) move_angle = -90;
		else if(inp_rt) move_angle = 0;
		else if(inp_lt) move_angle = 180;
		
		attacking = false;
		
		if(leftEquiped != null){
			if(((Weapon) inv.slot[30].item).isInUse())attacking = true;
			leftPos = ((Weapon) inv.slot[30].item).getPos(world.mouse.lb_down, world.mouse.lb_pressed);
		}
		if(attacking){
			mvel = 2.5f;
			torq = 3;
		}
		else{
			mvel = 5;
			torq = 10;
		}
	}
	
	public void post(World world) {
		if(leftEquiped != null){
			float xMove = (float) (Math.cos((angle+leftPos[1])/180*Math.PI)*leftPos[0]);
			float yMove = (float) (Math.sin((angle+leftPos[1])/180*Math.PI)*leftPos[0]);
			
			((Weapon)(inv.slot[30].item)).graphic.x = (float) (x)+xMove;
			((Weapon)(inv.slot[30].item)).graphic.y = (float) (y)+yMove;
			((Weapon)(inv.slot[30].item)).graphic.angle = angle-135+leftPos[2];
		}
	}
}

