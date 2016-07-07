package com.dungeon.game.item.equipable.weapon;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.MeleeGraphic;
import com.dungeon.game.item.equipable.weapon.part.Part;
import com.dungeon.game.item.equipable.weapon.part.axe.blade.AxeBlade;
import com.dungeon.game.item.equipable.weapon.part.axe.handle.AxeHandle;
import com.dungeon.game.item.equipable.weapon.part.axe.tip.AxeTip;
import com.dungeon.game.item.equipable.weapon.part.sword.blade.SwordBlade;
import com.dungeon.game.item.equipable.weapon.part.sword.guard.SwordGuard;
import com.dungeon.game.item.equipable.weapon.part.sword.hilt.SwordHilt;
import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.item.equipable.weapon.swing.SwingSet;
import com.dungeon.game.item.equipable.weapon.swing.sword.Rest;
import com.dungeon.game.item.equipable.weapon.swing.sword.Slash;
import com.dungeon.game.item.equipable.weapon.swing.sword.Stab;
import com.dungeon.game.item.equipable.weapon.swing.sword.SwordSwing;
import com.dungeon.game.world.World;

public class Axe extends Melee {
	
	public Part blade;
	public Part tip;
	public Part handle;
	
	public Axe(World world, int level) {
		super(world, "axe.png");
		

		
		try {
			blade = (Part) AxeBlade.parts[(int) (Math.random()*AxeBlade.NUM)].newInstance(world, level);
			tip = (Part) AxeTip.parts[(int) (Math.random()*AxeTip.NUM)].newInstance(world, level);
			handle = (Part) AxeHandle.parts[(int) (Math.random()*AxeHandle.NUM)].newInstance(world, level);
		} catch (Exception e) {
			e.printStackTrace();
		}

		blade.begin(this);
		tip.begin(this);
		handle.begin(this);
		
		if(!blade.sprite.getTextureData().isPrepared()) blade.sprite.getTextureData().prepare();
		Pixmap bladeMap = blade.sprite.getTextureData().consumePixmap();
		if(!tip.sprite.getTextureData().isPrepared()) tip.sprite.getTextureData().prepare();
		Pixmap tipMap = tip.sprite.getTextureData().consumePixmap();
		if(!handle.sprite.getTextureData().isPrepared()) handle.sprite.getTextureData().prepare();
		Pixmap handleMap = handle.sprite.getTextureData().consumePixmap();
		
		Pixmap spr = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		spr.drawPixmap(handleMap, 0, 0);
		spr.drawPixmap(bladeMap, 0, 0);
		spr.drawPixmap(tipMap, 0, 0);
		sprite = new Texture(spr);
		spr.dispose();
		
		name = blade.name;
		
		hasHit = false;
		
		damage = blade.damage + tip.damage + handle.damage;
		speed = blade.speed + tip.speed + handle.speed;
		knockback = blade.knockback + tip.knockback + handle.knockback;
		weight = blade.weight + tip.weight + handle.weight;
		
		desc = "A widely used and dependable melee weapon.\n\n Damage: "+ Math.floor(damage*10)/10f + "\n Speed: "+ Math.floor(speed*10)/10f + "\n Knockback: "+ Math.floor(knockback*10)/10f + "\n Weight: "+ Math.floor(weight*10)/10f;
		
		graphic = new MeleeGraphic(world, this, new Polygon(new float[]{24,6,26,8,2,32,0,32,0,30}), 30, 2);
		
//		swings = new SwingSet(world, this, new Swing[]{new Rest(world),
//				new Slash(world), 
//				new Stab(world),
//				}, false);

		swings = getStartSwings();
		hitEffects.add(new Stun(world, 30));
	}
	
	public float[] getPos(boolean mousedown, boolean mousepressed){
		swings.progressWeapon();
		
		return new float[]{distance,polarAngle,angle};
	}
	
	

	@Override
	public boolean isInUse() {
		return swings.isInUse;
	}

	public boolean inAttack() {
		return swings.isInAttack;
	}
	
	public void hit(Character c) {
		swings.hit(c);
	}
	
	public String getDesc() {
		return "The standard axe class weapon. Most axe class weapons can hit multiple targets in one swing. This particular axe has a two swing combo; a fronthand swing across the body, followed by an overhead smash.\n\n\"They say axes were once used to chop something other than heads.\" -Hubber, fabled sellsword\n\n Damage: " + Math.floor(damage*10)/10f;
	}

	@Override
	public void reset() {
		swings.reset();
	}
	
	public String[] getAllowedSwings(){
		ArrayList<String> bannedSwings = new ArrayList<String>();
		for(int i = 0; i < blade.bannedSwings.length; i++){
			if(!bannedSwings.contains(blade.bannedSwings[i]))bannedSwings.add(blade.bannedSwings[i]);
		}
		for(int i = 0; i < tip.bannedSwings.length; i++){
			if(!bannedSwings.contains(tip.bannedSwings[i]))bannedSwings.add(tip.bannedSwings[i]);
		}
		for(int i = 0; i < handle.bannedSwings.length; i++){
			if(!bannedSwings.contains(handle.bannedSwings[i]))bannedSwings.add(handle.bannedSwings[i]);
		}
		
		ArrayList<String> allowedSwings = new ArrayList<String>();
		for(int i = 0; i < blade.allowedSwings.length; i++){
			if(!allowedSwings.contains(blade.allowedSwings[i]) && !bannedSwings.contains(blade.allowedSwings[i]))allowedSwings.add(blade.allowedSwings[i]);
		}
		for(int i = 0; i < tip.allowedSwings.length; i++){
			if(!allowedSwings.contains(tip.allowedSwings[i]) && !bannedSwings.contains(blade.allowedSwings[i]))allowedSwings.add(tip.allowedSwings[i]);
		}
		for(int i = 0; i < handle.allowedSwings.length; i++){
			if(!allowedSwings.contains(handle.allowedSwings[i]) && !bannedSwings.contains(blade.allowedSwings[i]))allowedSwings.add(handle.allowedSwings[i]);
		}
		return allowedSwings.toArray(new String[0]);
	}
	
	public SwingSet getStartSwings(){
		String[] allowedSwings = getAllowedSwings();
		Swing[] swings = new Swing[4];
		swings[0] = new Rest(world);
		swings[1] = SwordSwing.getSwingByName(world, allowedSwings[(int) (Math.random()*allowedSwings.length)]);
		swings[2] = SwordSwing.getSwingByName(world, allowedSwings[(int) (Math.random()*allowedSwings.length)]);
		swings[3] = SwordSwing.getSwingByName(world, allowedSwings[(int) (Math.random()*allowedSwings.length)]);
		return new SwingSet(world, this, swings, blade.repeatable && tip.repeatable && handle.repeatable);
	}

	@Override
	public String getDamageText() {
		return "Weapon Damage: " + Math.round(damage*10)/10f;
	}

	@Override
	public String getSpeedText() {
		return "Weapon Speed: " + Math.round(speed*10)/10f;
	}

	@Override
	public String getKnockText() {
		return "Weapon Knockback: " + Math.round(knockback*10)/10f;
	}

	@Override
	public String getWeightText() {
		return "Weapon Weight: " + Math.round(weight*10)/10f;
	}
}
