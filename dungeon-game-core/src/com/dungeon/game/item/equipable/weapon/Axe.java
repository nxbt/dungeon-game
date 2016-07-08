package com.dungeon.game.item.equipable.weapon;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.weapon.MeleeGraphic;
import com.dungeon.game.item.equipable.weapon.part.Part;
import com.dungeon.game.item.equipable.weapon.part.axe.blade.AxeBlade;
import com.dungeon.game.item.equipable.weapon.part.axe.handle.AxeHandle;
import com.dungeon.game.item.equipable.weapon.part.axe.tip.AxeTip;
import com.dungeon.game.item.equipable.weapon.swing.axe.AxeSwing;
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

		swingClass = AxeSwing.class;
		swings = getStartSwings();
		hitEffects.add(new Stun(world, 30));
	}
	
	public String getDesc() {
		return "The standard axe class weapon. Most axe class weapons can hit multiple targets in one swing. This particular axe has a two swing combo; a fronthand swing across the body, followed by an overhead smash.\n\n\"They say axes were once used to chop something other than heads.\" -Hubber, fabled sellsword\n\n Damage: " + Math.floor(damage*10)/10f;
	}

	@Override
	public String getDamageText() {
		return "Weapon Damage: " + Math.round(damage*10)/10f + "   \n Blade: " + blade.damage + "   \n Guard: " + tip.damage + "   \n Hilt: " + handle.damage;
	}

	@Override
	public String getSpeedText() {
		return "Weapon Speed: " + Math.round(speed*10)/10f + "   \n Blade: " + blade.speed + "   \n Guard: " + tip.speed + "   \n Hilt: " + handle.speed;
	}

	@Override
	public String getKnockText() {
		return "Weapon Knockback: " + Math.round(knockback*10)/10f + "   \n Blade: " + blade.knockback + "   \n Guard: " + tip.knockback + "   \n Hilt: " + handle.knockback;
	}

	@Override
	public String getWeightText() {
		return "Weapon Weight: " + Math.round(weight*10)/10f + "   \n Blade: " + blade.weight + "   \n Guard: " + tip.weight + "   \n Hilt: " + handle.weight;
	}
	
	public Part[] getParts(){
		return new Part[]{blade, tip, handle};
	}
}
