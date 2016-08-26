package com.dungeon.game.entity.character;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.regen.StamRegen;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.entity.hud.EffectHudBackground;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.ammo.Arrow;
import com.dungeon.game.item.consumable.LifePotion;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.FlashLight;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.item.equipable.Lantern;
import com.dungeon.game.item.equipable.armor.WoolShirt;
import com.dungeon.game.item.equipable.weapon.Axe;
import com.dungeon.game.item.equipable.weapon.Bow;
import com.dungeon.game.item.equipable.weapon.Medium;
import com.dungeon.game.item.equipable.weapon.Sword;
import com.dungeon.game.item.equipable.weapon.Wand;
import com.dungeon.game.item.equipable.weapon.Weapon;
import com.dungeon.game.light.Light;
import com.dungeon.game.textures.entity.Person;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Player extends Character {
	public final int REACH = 4*Tile.TS;
	
	public ArrayList<EffectGraphic> effectGraphics;
	
	public boolean[] actionState; // 0 = fightMode, 1 = talking, 2 = invOpen
	
	public Entity focusedEntity;
	
	public Player(World world, float x, float y) {
		super(world, x, y, 32, 32, "person.png");
		
		Sprite spr = new Sprite(sprite);
		spr.setColor(new Color(1, 0, 0, 1));
		sprite = spr.getTexture();
		sprite = new Person().texture;
		
		speechColor = Color.BLUE;
		
		light = new Light(world, x, y, 20, 100, 0, this);
		
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
		genVisBox();
		
		origin_x = 16;
		origin_y = 16;
		
		d_offx = 0;
		d_offy = 0;
		
		solid = true;
		
		int[][] invLayout = new int[][] {
			//consumables
			new int[] {1, 8, 8}, //1
			new int[] {1, 48, 8}, //2
			new int[] {1, 88, 8}, //3
			new int[] {1, 128, 8}, //4
			new int[] {1, 168, 8}, //5
			//inventory
			new int[] {0, 8, 48}, //6
			new int[] {0, 48, 48}, //7
			new int[] {0, 88, 48}, //8
			new int[] {0, 128, 48}, //9
			new int[] {0, 168, 48}, //10
			new int[] {0, 8, 88}, //11
			new int[] {0, 48, 88}, //12
			new int[] {0, 88, 88}, //13
			new int[] {0, 128, 88}, //14
			new int[] {0, 168, 88}, //15
			new int[] {0, 8, 128}, //16
			new int[] {0, 48, 128}, //17
			new int[] {0, 88, 128}, //18
			new int[] {0, 128, 128}, //19
			new int[] {0, 168, 128}, //20
			new int[] {0, 8, 168}, //21
			new int[] {0, 48, 168}, //22
			new int[] {0, 88, 168}, //23
			new int[] {0, 128, 168}, //24
			new int[] {0, 168, 168}, //25
			new int[] {0, 8, 208}, //26
			new int[] {0, 48, 208}, //27
			new int[] {0, 88, 208}, //28
			new int[] {0, 128, 208}, //29
			new int[] {0, 168, 208}, //30
			//weapons
			new int[] {2, 208, 8}, //31
			new int[] {2, 248, 8}, //32
			//Armor
			new int[] {7, 208, 48}, //33
			new int[] {6, 208, 88}, //34
			new int[] {5, 208, 128}, //35
			new int[] {4, 208, 168}, //36
			new int[] {3, 208, 208}, //37
			//Rings and Amulet
			new int[] {9, 248, 48}, //38
			new int[] {9, 248, 88}, //39
			new int[] {9, 248, 128}, //40
			new int[] {9, 248, 168}, //41
			new int[] {8, 248, 208}, //42
		};
		
		
		inv = new Inventory(world, invLayout, 10, 100);
		equipSlots = new Slot[]{inv.slot[30],inv.slot[31],inv.slot[32],inv.slot[33],inv.slot[34],inv.slot[35],inv.slot[36],inv.slot[37],inv.slot[38],inv.slot[39],inv.slot[40],inv.slot[41]};
		equipItems = new Equipable[equipSlots.length];
		inv.slot[6].item = new FlashLight(world);
		inv.slot[31].item = new Lantern(world);
		inv.slot[1].item = new LifePotion(world);
		inv.slot[5].item = new Arrow(world);
		inv.slot[5].item.stack = 10;
		inv.addItem(new Sword(world, 1));
		inv.addItem(new Sword(world, 1));
		inv.addItem(new Sword(world, 1));
		inv.addItem(new Sword(world, 1));
		inv.addItem(new Axe(world, 1));
		inv.addItem(new Axe(world, 1));
		inv.addItem(new Axe(world, 1));
		inv.addItem(new Axe(world, 1));
		inv.addItem(new Bow(world, 10, 10));
		inv.addItem(new Wand(world));
		inv.addItem(new WoolShirt(world, speechColor));
		
		inv.slot[1].item.stack = 5;
		
		actionState = new boolean[] {false, false, false};
		
		effectGraphics = new ArrayList<EffectGraphic>();

		addEffect(new StamRegen(world, -1, 0.1f));
		
		vision = 10;
		hearing = 10;
		gold = 0;
		clickable = false;
	}
	
	public void calc() {
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
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))inv.slot[0].consume(this);
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))inv.slot[1].consume(this);
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))inv.slot[2].consume(this);
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4))inv.slot[3].consume(this);
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5))inv.slot[4].consume(this);
		
		if(actionState[2]) target_angle = angle;
		else if(Hud.class.isInstance(focusedEntity)) target_angle = (float) (180/Math.PI*Math.atan2(focusedEntity.y+world.cam.y-world.cam.height/2-(y), focusedEntity.x+world.cam.x-world.cam.width/2-(x)));
		else if(focusedEntity != null) target_angle = (float) (180/Math.PI*Math.atan2(focusedEntity.y-y, focusedEntity.x-x));
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !attacking && world.mouse.slot.item == null) {
			toggleFightMode();
			actionState[0] = !actionState[0];
		}
		
		if(inv.slot[35].item != null) ((Equipable) inv.slot[35].item).update(this);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.E) && !fightMode) {
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
		
		if(equipItems[0] != null && fightMode) {
			if(((Hand) inv.slot[30].item).isInUse())attacking = true;
			((Hand) inv.slot[30].item).getPos(world.mouse.lb_down, world.mouse.lb_pressed);
			((Hand)inv.slot[30].item).graphic.calc();
			if(inv.slot[30].item instanceof Medium) {
				if(Gdx.input.isKeyJustPressed(Input.Keys.O))((Medium)inv.slot[30].item).preSpell();
				if(Gdx.input.isKeyJustPressed(Input.Keys.P))((Medium)inv.slot[30].item).nextSpell();
				if(((Medium)inv.slot[30].item).cooldown>0)((Medium)inv.slot[30].item).cooldown--;
			}
		}
		if(equipItems[1] != null && fightMode) {
			if(((Hand) inv.slot[31].item).isInUse())attacking = true;
			((Hand) inv.slot[31].item).getPos(world.mouse.rb_down, world.mouse.rb_pressed);
			((Hand)inv.slot[31].item).graphic.calc();
			if(inv.slot[31].item instanceof Medium) {
				if(Gdx.input.isKeyJustPressed(Input.Keys.O))((Medium)inv.slot[31].item).preSpell();
				if(Gdx.input.isKeyJustPressed(Input.Keys.P))((Medium)inv.slot[31].item).nextSpell();
				if(((Medium)inv.slot[31].item).cooldown>0)((Medium)inv.slot[31].item).cooldown--;
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
		
		if(!actionState[2] && world.hudEntities.indexOf(inv.graphic) >= 0) actionState[2] = true;
		else if(actionState[2] && world.hudEntities.indexOf(inv.graphic) == -1) actionState[2] = false;
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
		if(equipItems[0] != null && fightMode && equipItems[0] instanceof Weapon){
			((Hand)equipItems[0]).graphic.updatePos(true);
		}else if(equipItems[0] != null && !(equipItems[0] instanceof Weapon))((Hand)equipItems[0]).graphic.updatePos(true);
		if(equipItems[1] != null && fightMode && equipItems[1] instanceof Weapon){
			((Hand)equipItems[1]).graphic.updatePos(false);
		}else if(equipItems[1] != null && !(equipItems[1] instanceof Weapon))((Hand)equipItems[1]).graphic.updatePos(true);
		if(killMe){
			if(equipItems[0]!=null)unequip((Hand) equipItems[0]);
			if(equipItems[1]!=null)unequip((Hand) equipItems[1]);
		}
	}
}
