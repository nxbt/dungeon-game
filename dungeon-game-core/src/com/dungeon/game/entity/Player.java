package com.dungeon.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.ManaRegen;
import com.dungeon.game.effect.StamRegen;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.entity.hud.EffectHudBackground;
import com.dungeon.game.item.Arrow;
import com.dungeon.game.item.Bow;
import com.dungeon.game.item.Equipable;
import com.dungeon.game.item.Hat;
import com.dungeon.game.item.Inventory;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.LifePotion;
import com.dungeon.game.item.Medium;
import com.dungeon.game.item.Sword;
import com.dungeon.game.item.Wand;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Player extends Character {
	public final int REACH = 4*Tile.TS;
	
	public Item hat;

	private Weapon leftEquiped;
	private Weapon rightEquiped;
	
	private float[] leftPos;
	private float[] rightPos;
	
	private boolean attacking;
	
	public ArrayList<EffectGraphic> effectGraphics;
	
	public Player(World world, int x, int y) {
		super(world, x, y);

		effectGraphics = new ArrayList<EffectGraphic>();
		addEffect(new StamRegen(world, -1, 0.1f));
		addEffect(new ManaRegen(world, -1, 0.1f));
		
		vision = 10;
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
		
//		hitbox = new Polygon(new float[]{30,16,28,23,23,28,16,30,9,28,4,23,2,16,4,9,9,4,16,2,23,4,28,9});
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		
		origin_x = 16;
		origin_y = 16;
		
		d_width = 32;
		d_height = 32;
		
		d_offx = 0;
		d_offy = 0;
		
		sprite = new Texture("Person.png");
		
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
		
		inv = new Inventory(world, invLayout, "invBack.png", 10, 100, 0, 240, 288, 16);
		
		hat = inv.slot[35].item; 
		inv.slot[0].item = new LifePotion(world);
		inv.slot[0].item.stack = 3;
//		inv.slot[35].item = new Hat(world);
//		inv.slot[6].item = new Crap(world);
		inv.slot[19].item = new Arrow(world);
//		inv.slot[20].item = new RubberSword(world);
		inv.slot[21].item = new Sword(world, 5, 10);
		inv.slot[22].item = new  Bow(world, 0.5f, 10);
//		inv.slot[23].item = new OneTaper(world);
		inv.slot[24].item = new Wand(world);
		
		inv.slot[19].item.stack = 6;
//		inv.slot[6].item.stack = 10;
		
		light = new Light(this, 1);
	}
	
	public void calc() { //TODO: complete rework of owner code!
		
		for(EffectGraphic eg: effectGraphics){
			if(!world.hudEntities.contains(eg)){
				for(int i = 0; i < world.hudEntities.size(); i++){
					if(world.hudEntities.get(i) instanceof EffectHudBackground){
						world.hudEntities.add(i,eg);
						break;
					}
				}
			}
		}
		
		for(int i = world.hudEntities.size()-1;i>=0;i--){
			if(world.hudEntities.get(i) instanceof EffectGraphic&&!effectGraphics.contains(world.hudEntities.get(i)))world.hudEntities.remove(world.hudEntities.get(i));
		}
		
		for(EffectGraphic eg: effectGraphics){
			eg.update();
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))inv.slot[0].consume(this);
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))inv.slot[1].consume(this);
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))inv.slot[2].consume(this);
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4))inv.slot[3].consume(this);
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5))inv.slot[4].consume(this);
		target_angle = (float) (180/Math.PI*Math.atan2(world.mouse.y+world.cam.y-world.cam.HEIGHT/2-(y), world.mouse.x+world.cam.x-world.cam.WIDTH/2-(x)));
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !attacking && world.mouse.slot.item == null) {
			fight_mode = !fight_mode;
			
			if(fight_mode) {
				if(leftEquiped != null) equip(leftEquiped);
				if(rightEquiped != null) equip(rightEquiped);
				
				if(world.hudEntities.indexOf(inv.graphic) > -1) {
					inv.graphic.close();
				}
			}
			else {
				if(leftEquiped != null) unequip(leftEquiped);
				if(rightEquiped != null) unequip(rightEquiped);
			}
		}
		
		if(fight_mode) {
			if(leftEquiped != null && inv.slot[30].item==null) {
				unequip(leftEquiped);
				
				leftEquiped = null;
			}
			else if(leftEquiped == null && inv.slot[30].item != null) {
				leftEquiped = (Weapon) inv.slot[30].item;
				
				equip(leftEquiped);
			}
			else if(leftEquiped != inv.slot[30].item) {
				unequip(leftEquiped);
				
				leftEquiped = (Weapon) inv.slot[30].item;
				
				equip(leftEquiped);
			}
			
			if(rightEquiped != null && inv.slot[31].item==null) {
				unequip(rightEquiped);
				
				rightEquiped = null;
			}
			else if(rightEquiped == null && inv.slot[31].item != null) {
				rightEquiped = (Weapon) inv.slot[31].item;
				
				equip(rightEquiped);
			}
			else if(rightEquiped != inv.slot[31].item) {
				unequip(rightEquiped);
				
				rightEquiped = (Weapon) inv.slot[31].item;
				
				equip(rightEquiped);
			}
		}
		
		if(inv.slot[35].item != null) ((Equipable) inv.slot[35].item).update(world, this);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.E) && !fight_mode) {
			if(world.hudEntities.indexOf(inv.graphic) == -1) {
				inv.graphic.open();
			}
			else if(world.hudEntities.contains(inv.graphic))inv.graphic.close();
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
		
		if(leftEquiped != null && fight_mode){
			if(((Weapon) inv.slot[30].item).isInUse())attacking = true;
			leftPos = ((Weapon) inv.slot[30].item).getPos(world.mouse.lb_down, world.mouse.lb_pressed);
			((Weapon)inv.slot[30].item).graphic.calc();
			if(inv.slot[30].item instanceof Medium){
				if(Gdx.input.isKeyJustPressed(Input.Keys.O))((Medium)inv.slot[30].item).preSpell();
				if(Gdx.input.isKeyJustPressed(Input.Keys.P))((Medium)inv.slot[30].item).nextSpell();
				if(((Medium)inv.slot[30].item).cooldown>0)((Medium)inv.slot[30].item).cooldown--;
			}
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

	@Override
	public void post() {
		if(leftEquiped != null && fight_mode){
			float xMove = (float) (Math.cos((angle+leftPos[1])/180*Math.PI)*leftPos[0]);
			float yMove = (float) (Math.sin((angle+leftPos[1])/180*Math.PI)*leftPos[0]);
			
			((Weapon)(inv.slot[30].item)).graphic.x = (float) (x)+xMove;
			((Weapon)(inv.slot[30].item)).graphic.y = (float) (y)+yMove;
			((Weapon)(inv.slot[30].item)).graphic.angle = angle-135+leftPos[2];
		}
		if(killMe){
			if(leftEquiped!=null)unequip(leftEquiped);
			if(rightEquiped!=null)unequip(rightEquiped);
		}
	}
}

