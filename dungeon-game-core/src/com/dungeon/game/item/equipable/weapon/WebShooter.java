package com.dungeon.game.item.equipable.weapon;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Tangle;
import com.dungeon.game.entity.weapon.RangedGraphic;
import com.dungeon.game.item.ammo.Arrow;
import com.dungeon.game.item.ammo.Silk;
import com.dungeon.game.item.equipable.weapon.part.Part;
import com.dungeon.game.world.World;

public class WebShooter extends Ranged {
	
	public static final int REST = 0;
	public static final int WINDUP = 1;
	public static final int FIRE = 2;
//	private static final int WINDDOWN = 3;
	
	public WebShooter(World world) {
		super(world, "slot.png");
		
		sprite = new Texture(new Pixmap(32, 32, Pixmap.Format.RGBA8888));
		
		name = "Web Shooter";
		
		speed = 10;
		dmgMod = 0;
		
		strength = 7;
		knockstr = 0;

		desc = "A web launcher.";
		
		ammoType = new Silk(world);

		graphic = new RangedGraphic(world, this, new Polygon(new float[]{2,2,30,30,8,26}), 4, 28);
	}

	@Override
	public boolean isInUse() {
		return stage == WINDUP||stage == FIRE;
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
			if(mousedown && owner.inv.contains(new Silk(world)) != null&&owner.use_stam(10)){
				owner.inv.contains(new Silk(world)).item.stack--;
				if(owner.inv.contains(new Silk(world)).item.stack==0)owner.inv.contains(new Silk(world)).item = null;
				stage=WINDUP;
				stageTimer = 0;
			}
			break;
			
		case WINDUP:
			
			if(!mousedown) {
				stageTimer = 0;
				stage = FIRE;
				((RangedGraphic) graphic).fire(strength*Math.min(index, 45)/45);
			}
			break;
		case FIRE:
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
		return "A Web Shooter, which shoots webs.";
	}

	@Override
	public void reset() {
		stage = REST;
		stageTimer = 0;
		
		distance = 30;
		angle = 0;
		polarAngle = 10;
	}

	public ArrayList<Effect> getEffects() {
		ArrayList<Effect> effects = new ArrayList<Effect>();
		effects.add(new Tangle(world, 300));
		return effects;
	}
	
	public Part[] getParts(){
		return null;
	}
}
