package com.dungeon.game.item.weapon;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.MeleeGraphic;
import com.dungeon.game.item.weapon.swing.sword.Rest;
import com.dungeon.game.item.weapon.swing.sword.Slash;
import com.dungeon.game.item.weapon.swing.sword.Stab;
import com.dungeon.game.item.weapon.swing.sword.Swing;
import com.dungeon.game.item.weapon.swing.sword.SwingSet;
import com.dungeon.game.world.World;

public class Axe extends Melee {
	protected float[] dmgMult;
	protected float[] knockMult;
	
	public Axe(World world, float damage, float speed, float weight) {
		super(world, "axe.png");
		
		name = "Axe";
		
		hasHit = false;
		
		desc = "A widely used and dependable melee weapon.\n\n Damage: "+ Math.floor(damage*10)/10f;
		
		this.damage = damage;
		this.speed = speed;
		this.weight = weight;
		
		knockratio = 0.2f;
		knockback = 10;
		
		dmgMult = new float[]{0.7f,1.5f};
		knockMult = new float[]{1,0.3f};		
		graphic = new MeleeGraphic(world, this, new Polygon(new float[]{24,6,26,8,2,32,0,32,0,30}), 30, 2);
		
		swings = new SwingSet(world, this, new Swing[]{new Rest(world),
				new Slash(world), 
				new Stab(world),
				}, false);
		
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
		return null;
	}
	
	public SwingSet getStartSwings(){
		return null;
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
