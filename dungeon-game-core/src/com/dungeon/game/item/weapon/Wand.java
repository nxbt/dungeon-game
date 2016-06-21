package com.dungeon.game.item.weapon;

import java.util.ArrayList;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.weapon.MediumGraphic;
import com.dungeon.game.spell.Fireball;
import com.dungeon.game.spell.Heal;
import com.dungeon.game.spell.Spell;
import com.dungeon.game.spell.TangleVines;
import com.dungeon.game.world.World;

public class Wand extends Medium {

	public Wand(World world) {
		super(world, "wand.png");
		
		name = "Wand";
		desc = "You're a wizard, Harry.\n\nI'm a WHAT?";
		
		numSpells = 3;
		
		this.dmgMod = 1;
		
		spells = new Spell[numSpells];
		spells[0] = new Fireball(world);
		spells[1] = new Heal(world);
		spells[2] = new TangleVines(world);
		
		graphic = new MediumGraphic(world, this, new Polygon(new float[]{28,2,30,4,4,30,2,28}), 30, 2);
	}
	
	@Override
	public float[] getPos(boolean mousedown, boolean mousepressed) {
		int distance = 14;
		int polarAngle = 82;
		int angle = -10;
		stageTimer++;
		int constant=2;
		int index = stageTimer*constant;
		switch(stage){
		
		}
		
		if(mousepressed&&cooldown == 0&&owner.use_mana(spells[spell].mana)){
			((MediumGraphic) graphic).cast(spells[spell],this);
			cooldown = spells[spell].cooldown;
		}
		
		if(!leftSide) return new float[]{distance,polarAngle*-1,angle*-1};
		
		return new float[]{distance,polarAngle,angle};
	}

	@Override
	public boolean isInUse() {
		return false;
	}
	
	public String getDesc() {
		return "Well 'just Harry' your A WIZZARD! \n\n " + desc;
	}

	@Override
	public void reset() {}
	
	public ArrayList<Effect> getEffects() {
		ArrayList<Effect> effects = new ArrayList<Effect>();
		effects.add(new Stun(world, 30));
		return effects;
	}
}
