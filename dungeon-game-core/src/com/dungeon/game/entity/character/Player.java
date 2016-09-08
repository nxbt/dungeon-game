package com.dungeon.game.entity.character;

import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.KeyListener;
import com.dungeon.game.effect.regen.StamRegen;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.entity.hud.EffectHudBackground;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.entity.hud.window.InvWindow;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Key;
import com.dungeon.game.item.ammo.Arrow;
import com.dungeon.game.item.ammo.Silk;
import com.dungeon.game.item.consumable.LifePotion;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.item.equipable.Lantern;
import com.dungeon.game.item.equipable.weapon.Bow;
import com.dungeon.game.item.equipable.weapon.Claw;
import com.dungeon.game.item.equipable.weapon.Sword;
import com.dungeon.game.item.equipable.weapon.Wand;
import com.dungeon.game.item.equipable.weapon.WebShooter;
import com.dungeon.game.light.Light;
import com.dungeon.game.textures.entity.Person;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Player extends Character {
	public final int REACH = 4*Tile.TS;
	
	public ArrayList<EffectGraphic> effectGraphics;
	
	public boolean[] actionState; // 0 = fightMode, 1 = talking, 2 = invOpen
	
	public Entity focusedEntity;
	
	private boolean talking;
	
	public Player(World world, float x, float y) {
		super(world, x, y, 32, 32, "person.png");
		
		team = FRIEND;
		
		Sprite spr = new Sprite(sprite);
		spr.setColor(new Color(1, 0, 0, 1));
		sprite = spr.getTexture();
		sprite = new Person().texture;
		
		speechColor = Color.BLUE;
		
		light = new Light(world, x, y, 2, 100, 0, this);
		
		name = "Player";
		
		maxLife = 100;
		maxStam = 100;
		maxMana = 100;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		genVisBox();
		
		originX = 16;
		originY = 16;
		
		inv = new Inventory(world, Inventory.DEFAULT_LAYOUT, 10, 100);
		
		inv.addItem(new Sword(world, 1));
		inv.addItem(new Sword(world, 2));
		inv.addItem(new Sword(world, 3));
		inv.addItem(new Sword(world, 4));
		inv.addItem(new Sword(world, 1));
		inv.addItem(new Sword(world, 1));
		inv.addItem(new Sword(world, 1));
		inv.addItem(new Sword(world, 1));
		inv.addItem(new Sword(world, 1));
		inv.addItem(new LifePotion(world), 10);
		inv.addItem(new Key(world), 10);
		inv.addItem(new Lantern(world));
		inv.addItem(new Bow(world, 1, 10));
		inv.addItem(new Arrow(world), 12);
		inv.addItem(new Claw(world, 1));
		inv.addItem(new WebShooter(world));
		inv.addItem(new Silk(world), 12);
		equipSlots = new Slot[]{inv.slot[30],inv.slot[31],inv.slot[32],inv.slot[33],inv.slot[34],inv.slot[35],inv.slot[36],inv.slot[37],inv.slot[38],inv.slot[39],inv.slot[40],inv.slot[41]};
		equipItems = new Equipable[equipSlots.length];
		
		actionState = new boolean[] {false, false, false};
		
		effectGraphics = new ArrayList<EffectGraphic>();

		addEffect(new StamRegen(world, -1, 0.5f));
		
		vision = 20;
		hearing = 10;
		gold = 1000;
		clickable = false;
		
		printPool = PrintPools.humanPool;
	}
	
	public void calc() {
		talking = focusedEntity instanceof Character;
		
		if(focusedEntity == null) focusedEntity = world.mouse;
		
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
		
		if(!talking) {
			if(KeyListener.keysJustPressed[Input.Keys.NUM_1])inv.slot[0].consume(this);
			if(KeyListener.keysJustPressed[Input.Keys.NUM_2])inv.slot[1].consume(this);
			if(KeyListener.keysJustPressed[Input.Keys.NUM_3])inv.slot[2].consume(this);
			if(KeyListener.keysJustPressed[Input.Keys.NUM_4])inv.slot[3].consume(this);
			if(KeyListener.keysJustPressed[Input.Keys.NUM_5])inv.slot[4].consume(this);
		}
		
		if(actionState[2]) target_angle = (float) (body.getAngle()/Math.PI*180f);
		else if(Hud.class.isInstance(focusedEntity)) target_angle = (float) (180/Math.PI*Math.atan2(focusedEntity.y+world.cam.y-world.cam.height/2-(y), focusedEntity.x+world.cam.x-world.cam.width/2-(x)));
		else if(focusedEntity != null) target_angle = (float) (180/Math.PI*Math.atan2(focusedEntity.y-y, focusedEntity.x-x));
		
		if(!talking && KeyListener.keysJustPressed[Input.Keys.SPACE] && !attacking && world.mouse.slot.item == null) {
			toggleFightMode();
			actionState[0] = !actionState[0];
		}
		
		if(inv.slot[35].item != null) ((Equipable) inv.slot[35].item).update(this);
		
		if(KeyListener.keysJustPressed[Input.Keys.E] && !fightMode) {
			if(world.hudEntities.indexOf(inv.graphic) == -1) {
				inv.graphic.open();
			}
			else {
				for(int i = 0; i < world.hudEntities.size(); i++) {
					Entity e = world.hudEntities.get(i);
					if(e instanceof InvWindow)  {
						((InvWindow) e).close();
						i--;
					}
				}
			}
		}
		
		boolean inp_lt = false;
		boolean inp_rt = false;
		boolean inp_up = false;
		boolean inp_dn = false;
		
		if(!talking) {
			if(KeyListener.keysPressed[Input.Keys.LEFT]  || KeyListener.keysPressed[Input.Keys.A]) inp_lt = true;
			if(KeyListener.keysPressed[Input.Keys.RIGHT] || KeyListener.keysPressed[Input.Keys.D]) inp_rt = true;
			if(KeyListener.keysPressed[Input.Keys.UP]    || KeyListener.keysPressed[Input.Keys.W]) inp_up = true;
			if(KeyListener.keysPressed[Input.Keys.DOWN]  || KeyListener.keysPressed[Input.Keys.S]) inp_dn = true;
		}
		
		if(inp_up && inp_rt) move_angle = 45;
		else if(inp_up && inp_lt) move_angle = 135;
		else if(inp_dn && inp_rt) move_angle = -45;
		else if(inp_dn && inp_lt) move_angle = -135;
		else if(inp_up) move_angle = 90;
		else if(inp_dn) move_angle = -90;
		else if(inp_rt) move_angle = 0;
		else if(inp_lt) move_angle = 180;
	}
	
	public void calcLight(){
		if(light!=null){
			light.update();
		}
	}


	@Override
	protected void activations() {
		if(world.mouse.rb_down)rightActivated = true;
		else rightActivated = false;
		if(world.mouse.lb_down)leftActivated = true;
		else leftActivated = false;
	}

	@Override
	public void post() {
		if(killMe){
			if(equipItems[0]!=null)unequip((Hand) equipItems[0]);
			if(equipItems[1]!=null)unequip((Hand) equipItems[1]);
		}
	}
}
