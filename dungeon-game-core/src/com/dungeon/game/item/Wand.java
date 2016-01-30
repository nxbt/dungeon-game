package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.MediumGraphic;
import com.dungeon.game.entity.Projectile;
import com.dungeon.game.entity.RangedGraphic;
import com.dungeon.game.spell.Fireball;
import com.dungeon.game.spell.Heal;
import com.dungeon.game.spell.Spell;
import com.dungeon.game.world.World;

public class Wand extends Medium {

	public Wand(World world) {
		super(world, new Texture("wand.png"));
		
		name = "Wand";
		desc = "You're a wizard, Harry.\n\nI'm a WHAT?";
		
		numSpells = 2;
		
		this.dmgMod = 1;
		
		spells = new Spell[numSpells];
		spells[0] = new Fireball(world);
		spells[1] = new Heal(world);
		
		graphic = new MediumGraphic(world, this, new Polygon(new float[]{28,2,30,4,4,30,2,28}), 30, 2);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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
		
		return new float[]{distance,polarAngle,angle};
	}

	@Override
	public boolean isInUse() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getDesc() {
		return "Well 'just Harry' your A WIZZARD! \n\n " + desc;
	}
}
