package com.dungeon.game.item.equipable.weapon;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.weapon.MeleeGraphic;
import com.dungeon.game.item.equipable.weapon.part.Part;
import com.dungeon.game.item.equipable.weapon.part.claw.main.ClawMain;
import com.dungeon.game.item.equipable.weapon.swing.claw.ClawSwing;
import com.dungeon.game.world.World;

public class Claw extends Melee {
	
	public Part claw;

	public Claw(World world, int level) {
		super(world, "sword.png");
		constructor(level, (int) (Math.random()*ClawMain.NUM));
	}
	
	public Claw(World world, int level, int clawNum) {
		super(world, "sword.png");
		constructor(level, clawNum);
	}
	
	public void constructor(int level, int clawNum) {
		try {
			claw = (Part) ClawMain.parts[clawNum].newInstance(world, level);
		} catch (Exception e) {
			e.printStackTrace();
		}

		claw.begin(this);
		
		if(!claw.sprite.getTextureData().isPrepared()) claw.sprite.getTextureData().prepare();
		Pixmap clawMap = claw.sprite.getTextureData().consumePixmap();
		
		Pixmap spr = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		spr.drawPixmap(clawMap, 0, 0);
		sprite = new Texture(spr);
		
		name = ((ClawMain) claw).getName();
		
		hasHit = false;
		
		damage = claw.damage;
		speed = claw.speed;
		knockback = claw.knockback;
		weight = claw.weight;
		
		desc = "My claws are sharp and long like yours. \n\n Damage: "+ Math.floor(damage*10)/10f + "\n Speed: "+ Math.floor(speed*10)/10f + "\n Knockback: "+ Math.floor(knockback*10)/10f + "\n Weight: "+ Math.floor(weight*10)/10f;
		
		graphic = new MeleeGraphic(world, this, claw.hitbox, 30, 2);
		
		swingClass = ClawSwing.class;
		swings = getStartSwings();
		
		hitEffects.add(new Stun(world, 30));
	}
	
	public String getDesc() {
		return "It's clearly a claw...";
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
	
	public Part[] getParts(){
		return new Part[]{claw};
	}

}
