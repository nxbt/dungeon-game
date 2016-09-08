package com.dungeon.game.item.equipable.weapon;

import java.util.ArrayList;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.weapon.RangedGraphic;
import com.dungeon.game.item.ammo.Arrow;
import com.dungeon.game.item.equipable.weapon.part.Part;
import com.dungeon.game.world.World;

public class Bow extends Ranged {
	
	public static final int REST = 0;
	public static final int WINDUP = 1;
	public static final int FIRE = 2;
//	private static final int WINDDOWN = 3;
	
	public Bow(World world, float dmgMod, int speed) {
		super(world, "bow.png");
		
		name = "Bow";
		
		this.speed = speed;
		this.dmgMod = dmgMod;
		
		strength = 10;
		knockstr = 10;

		desc = "The standard for all  ranged weapons.\n\n Damage modifier: " + Math.floor(dmgMod*1000)/10f + "%";

		graphic = new RangedGraphic(world, this, new Polygon(new float[]{2,2,30,30,8,26}), 4, 28);
	}

	@Override
	public boolean isInUse() {
		if(stage == WINDUP||stage == FIRE)return true;
		return false;
	}

	@Override
	public float[] getPos(boolean mousedown, boolean mousepressed) {
		int distance=30;
		int polarAngle= 10;
		int angle=0;
		stageTimer++;
		int constant=2;
		int index = stageTimer*constant;
		switch(stage){
		case REST:
		if(!sprite.equals(textures[0])){
			changeSprite(0);
		}
			if(mousedown && owner.inv.contains(new Arrow(world)) != null&&owner.use_stam(10)){
				owner.inv.contains(new Arrow(world)).item.stack--;
				if(owner.inv.contains(new Arrow(world)).item.stack==0)owner.inv.contains(new Arrow(world)).item = null;
				stage=WINDUP;
				stageTimer = 0;
			}
			break;
			
		case WINDUP:
			if(index<45) {
				if(!sprite.equals(textures[1])){
					changeSprite(1);
				}
			}
			else {
				if(!sprite.equals(textures[2])){
					changeSprite(2);
				}
			}
			
			if(!mousedown) {
				stageTimer = 0;
				stage = FIRE;
				((RangedGraphic) graphic).fire(strength*Math.min(index, 45)/45);
			}
			break;
		case FIRE:
			if(!sprite.equals(textures[0])) {
				changeSprite(0);
			}
			if(index>10) {
				stageTimer = 0;
				stage = REST;
			}
			break;
		}
		
//		if(!leftSide) return new float[]{distance,polarAngle*-1,angle*-1};
		graphic.graphic_angle = angle;
		graphic.graphic_pAngle = polarAngle;
		graphic.graphic_dist = distance;
		return new float[]{distance,polarAngle,angle};
	}
	
	public String getDesc() {
		return "The standard bow class weapon. Most weapons in the bow class must be pulled back by holding the use button before firing and require consuming one arrow class ammo item to fire. The further pulled back the bow is, the faster the arrow released will travel and the more damage it will inflict to any target it come in contact with. The damage modifier is applied to the damage of the arrow fired.\n\n\"Only once you can place an arrow anywhere you can see have you become an archer.\" -Garsin, the first archer\n\n Damage modifier: " + Math.floor(dmgMod*1000)/10f + "%";
	}

	@Override
	public void reset() {
		stage = REST;
		stageTimer = 0;
		
		distance = 30;
		angle = 0;
		polarAngle = 10;
		
		changeSprite(0);
	}

	public ArrayList<Effect> getEffects() {
		ArrayList<Effect> effects = new ArrayList<Effect>();
		effects.add(new Stun(world, 30));
		return effects;
	}
	
	public Part[] getParts(){
		return null;
	}
}
