package com.dungeon.game.item.equipable.weapon;

import java.util.ArrayList;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.equipable.weapon.part.Part;
import com.dungeon.game.item.equipable.weapon.swing.Rest;
import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.item.equipable.weapon.swing.SwingSet;
import com.dungeon.game.world.World;

public abstract class Melee extends Weapon {	
	public float damage;
	public float speed;
	
	public float knockback; //str of the knockback of this weapon
	
	public int numSwings;
	
	public boolean hasHit;
	
	public SwingSet swings;
	
	public  Class<?> swingClass;
	
	public Melee(World world, String filename) {
		super(world, filename);
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
	
	public void knockback(Character c) {
		swings.knockback(c);
	}
	
	public void reset() {
		swings.reset();
	}
	
	public abstract String getDamageText();
	
	public abstract String getSpeedText();
	
	public abstract String getKnockText();
	
	public abstract String getWeightText();
	
	public String[] getAllowedSwings(){
		Part[] parts = getParts();
		
		ArrayList<String> bannedSwings = new ArrayList<String>();
		for(Part p: parts){
			for(int i = 0; i < p.bannedSwings.length; i++){
				if(!bannedSwings.contains(p.bannedSwings[i]))bannedSwings.add(p.bannedSwings[i]);
			}
		}
		
		ArrayList<String> allowedSwings = new ArrayList<String>();
		for(Part p: parts){
			for(int i = 0; i < p.allowedSwings.length; i++){
				if(!allowedSwings.contains(p.allowedSwings[i]) && !bannedSwings.contains(p.allowedSwings[i]))allowedSwings.add(p.allowedSwings[i]);
			}
		}
		
		return allowedSwings.toArray(new String[0]);
	}
	
	public SwingSet getStartSwings(){
		Part[] parts = getParts();
		numSwings = 0;
		for(Part p: parts){
			numSwings+=p.numSwings;
		}
		String[] allowedSwings = getAllowedSwings();
		Swing[] swings = new Swing[numSwings+1];
		swings[0] = new Rest(world);
		for(int i = 1; i < numSwings+1; i++) {
			try {
				swings[i] = (Swing) swingClass.getDeclaredMethod("getSwingByName", new Class<?>[]{World.class, String.class}).invoke(null, world, allowedSwings[(int) (Math.random()*allowedSwings.length)]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		boolean repeatable = true;
		for(Part p: parts){
			if(!p.repeatable){
				repeatable = false;
				break;
			}
		}
		return new SwingSet(world, this, swings, repeatable);
	}
}
