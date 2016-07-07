package com.dungeon.game.item.equipable.weapon;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.MeleeGraphic;
import com.dungeon.game.item.equipable.weapon.part.Part;
import com.dungeon.game.item.equipable.weapon.part.sword.blade.SwordBlade;
import com.dungeon.game.item.equipable.weapon.part.sword.guard.SwordGuard;
import com.dungeon.game.item.equipable.weapon.part.sword.hilt.SwordHilt;
import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.item.equipable.weapon.swing.SwingSet;
import com.dungeon.game.item.equipable.weapon.swing.sword.Rest;
import com.dungeon.game.item.equipable.weapon.swing.sword.SwordSwing;
import com.dungeon.game.world.World;

public class Sword extends Melee {
	
	public Part blade;
	public Part guard;
	public Part hilt;
	
	public Sword(World world, int level) {
		super(world, "sword.png");
		
		try {
			blade = (Part) SwordBlade.parts[(int) (Math.random()*SwordBlade.NUM)].newInstance(world, level);
			guard = (Part) SwordGuard.parts[(int) (Math.random()*SwordGuard.NUM)].newInstance(world, level);
			hilt = (Part) SwordHilt.parts[(int) (Math.random()*SwordHilt.NUM)].newInstance(world, level);
		} catch (Exception e) {
			e.printStackTrace();
		}

		blade.begin(this);
		guard.begin(this);
		hilt.begin(this);
		
		if(!blade.sprite.getTextureData().isPrepared()) blade.sprite.getTextureData().prepare();
		Pixmap bladeMap = blade.sprite.getTextureData().consumePixmap();
		if(!guard.sprite.getTextureData().isPrepared()) guard.sprite.getTextureData().prepare();
		Pixmap guardMap = guard.sprite.getTextureData().consumePixmap();
		if(!hilt.sprite.getTextureData().isPrepared()) hilt.sprite.getTextureData().prepare();
		Pixmap hiltMap = hilt.sprite.getTextureData().consumePixmap();
		
		Pixmap spr = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		spr.drawPixmap(bladeMap, 0, 0);
		spr.drawPixmap(hiltMap, 0, 0);
		spr.drawPixmap(guardMap, 0, 0);
		sprite = new Texture(spr);
		spr.dispose();
		//for some reason all the pixmaps are all disposed already... thx libgdx!
		
		
		name = blade.name;
		
		hasHit = false;
		
		damage = blade.damage + guard.damage + hilt.damage;
		speed = blade.speed + guard.speed + hilt.speed;
		knockback = blade.knockback + guard.knockback + hilt.knockback;
		weight = blade.weight + guard.weight + hilt.weight;
		
		desc = "The most common and widely used melee weapon.\n\n Damage: "+ Math.floor(damage*10)/10f + "\n Speed: "+ Math.floor(speed*10)/10f + "\n Knockback: "+ Math.floor(knockback*10)/10f + "\n Weight: "+ Math.floor(weight*10)/10f;
		
		graphic = new MeleeGraphic(world, this, new Polygon(new float[]{24,6,26,8,2,32,0,32,0,30}), 30, 2);
		
//		swings = BLADE_SWINGS[blade.id]; // do we have to clone it? I guess not
//		for(int i = 0; i < GUARD_SWINGS[guard.id].length; i++){
//			swings.addSwing(GUARD_SWINGS[guard.id][i]);
//		}
//		for(int i = 0; i < HILT_SWINGS[hilt.id].length; i++){
//			swings.addSwing(HILT_SWINGS[hilt.id][i]);
//		}
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
		return "Sword\n\nThe accepted standard for melee weapons, mainly due to how common it is. Swords are generally considered very reliable, despite the diverse range of blades. "
				+ "This reliability is another reason for their wide acceptance. Even those who have become experts of other types of weapons and have rarely tocuhed a sword conceed "
				+ "that the sword is an excellently desgined and highly reliable weapon.\n\nSwords can either stab or swing, and a specific swords ability to do either is usually "
				+ "determined by the blade. A sword with a thin blade is usually better suited to stabbing while a sword with a broad blade is generally better for swinging. The "
				+ "extremes on either end are the rapier, which is so thin it cannot be used for swings at all, and the broad sword, which is so heavy and rigid that it is impossible "
				+ "to build enough momentum for a proper stab. Some other blades are limited by other factors, such as the scimitar and cutlass. These swords have curved blades, "
				+ "so there is no point at the end of the blade for stabs. A sword can preform around three or four different swings in a row before returning to the rest position. "
				+ "Speaking with a sword trainer allows you to change swings.\n\nSomeone who chooses to wield a sword must also consider the hilt of a sword. Guards and grips can "
				+ "change the damage a sword is capable of inflicting, the speed at which the blade can be swung, or even which swings can be used by the sword. If a grip or guard "
				+ "does not compliment the blade, the sword may become unwieldy and clunky. Certain merchants know how to disassemble a sword into its different parts and fit on new "
				+ "parts. Using their service is necessary to maximizing the efficiency of your sword.\n\nThe metals of a sword can be enchanted with most melee enchantments. When "
				+ "enchanted, a sword will softly glow with the color corresponding to the school of the enchantment. These types of enchantments are bound in the blade, and if "
				+ "a sword is disassembled, the blade will carry the enchantment. Besides enchanting the sword directly, some rare parts contain gem holders. If a gem is enchanted "
				+ "and then fitted by a jewler, the sword will inherient this enchantment. Gems can only be removed from these slot by a jewler, so if a sword is disassembled a gem "
				+ "will remain in the part the holder was located.\n\n\"My sword shall lead me to glory!\" -final words of Tanturin, the mythical warrior";
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
		for(int i = 0; i < guard.bannedSwings.length; i++){
			if(!bannedSwings.contains(guard.bannedSwings[i]))bannedSwings.add(guard.bannedSwings[i]);
		}
		for(int i = 0; i < hilt.bannedSwings.length; i++){
			if(!bannedSwings.contains(hilt.bannedSwings[i]))bannedSwings.add(hilt.bannedSwings[i]);
		}
		
		ArrayList<String> allowedSwings = new ArrayList<String>();
		for(int i = 0; i < blade.allowedSwings.length; i++){
			if(!allowedSwings.contains(blade.allowedSwings[i]) && !bannedSwings.contains(blade.allowedSwings[i]))allowedSwings.add(blade.allowedSwings[i]);
		}
		for(int i = 0; i < guard.allowedSwings.length; i++){
			if(!allowedSwings.contains(guard.allowedSwings[i]) && !bannedSwings.contains(blade.allowedSwings[i]))allowedSwings.add(guard.allowedSwings[i]);
		}
		for(int i = 0; i < hilt.allowedSwings.length; i++){
			if(!allowedSwings.contains(hilt.allowedSwings[i]) && !bannedSwings.contains(blade.allowedSwings[i]))allowedSwings.add(hilt.allowedSwings[i]);
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
		return new SwingSet(world, this, swings, blade.repeatable && guard.repeatable && hilt.repeatable);
	}

	@Override
	public String getDamageText() {
		return "Weapon Damage: " + Math.round(damage*10)/10f + "   \n Blade: " + blade.damage + "   \n Guard: " + guard.damage + "   \n Hilt: " + hilt.damage;
	}

	@Override
	public String getSpeedText() {
		return "Weapon Speed: " + Math.round(speed*10)/10f + "   \n Blade: " + blade.speed + "   \n Guard: " + guard.speed + "   \n Hilt: " + hilt.speed;
	}

	@Override
	public String getKnockText() {
		return "Weapon Knockback: " + Math.round(knockback*10)/10f + "   \n Blade: " + blade.knockback + "   \n Guard: " + guard.knockback + "   \n Hilt: " + hilt.knockback;
	}

	@Override
	public String getWeightText() {
		return "Weapon Weight: " + Math.round(weight*10)/10f + "   \n Blade: " + blade.weight + "   \n Guard: " + guard.weight + "   \n Hilt: " + hilt.weight;
	}
}
