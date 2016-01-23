package com.dungeon.game.item;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.MeleeGraphic;
import com.dungeon.game.entity.Projectile;
import com.dungeon.game.world.World;

public class Sword extends Melee {

	private final int REST = 0;
	private final int WINDUP1 = 1;
	private final int WINDUP2 = 2;
	private final int WINDUP3 = 3;
	private final int SWING1 = 4;
	private final int SWING2 = 5;
	private final int SWING3 = 6;
	private final int WINDDOWN1 = 7;
	private final int WINDDOWN2 = 8;
	private final int WINDDOWN3 = 9;
	
	private final float REST_DIST = 14;
	private final float REST_PANG = 82;
	private final float REST_ANGL = -10;
	
	private final float WINDUP1_TIME = 10;
	private final float WINDUP1_DIST = 24;
	private final float WINDUP1_PANG = 70;
	private final float WINDUP1_ANGL = 35;
	
	private final float SWING1_TIME = 12;
	private final float SWING1_DIST = 14;
	private final float SWING1_PANG = -55;
	private final float SWING1_ANGL = -50;
	private final float SWING1_CDWN = 30;
	private final float SWING1_STAM = 10;
	
	private final float WINDDOWN1_TIME = 20;
	
	private final float WINDUP2_TIME = 10;
	private final float WINDUP2_DIST = 16;
	private final float WINDUP2_PANG = -75;
	private final float WINDUP2_ANGL = -80;
	
	private final float SWING2_TIME = 15;
	private final float SWING2_DIST = 26;
	private final float SWING2_PANG = 80;
	private final float SWING2_ANGL = 45;
	private final float SWING2_CDWN = 30;
	private final float SWING2_STAM = 15;
	
	private final float WINDDOWN2_TIME = 14;
	
	private final float WINDUP3_TIME = 15;
	private final float WINDUP3_DIST = 12;
	private final float WINDUP3_PANG = 30;
	private final float WINDUP3_ANGL = -7;
	
	private final float SWING3_TIME = 4;
	private final float SWING3_DIST = 28;
	private final float SWING3_PANG = 6;
	private final float SWING3_ANGL = -3;
	private final float SWING3_CDWN = 20;
	private final float SWING3_STAM = 7;

	private final float WINDDOWN3_TIME = 14;
	
	protected float[] dmgMult;
	protected float[] knockMult;
	
	public Sword(World world, int damage, int cooldown, int speed) {
		super(world, damage, cooldown,speed, new Texture("sword.png"));
		desc = "Real sword I swear! \n\n Damage: "+damage+"\n Cooldown: "+cooldown;
		knockratio = 0.4f;
		knockstr = 10;
		dmgMult = new float[]{0.7f,1,1.5f};
		knockMult = new float[]{1,1.3f,0.7f};		
		graphic = new MeleeGraphic(world, this, new Polygon(new float[]{24,6,26,8,2,32,0,32,0,30}), 30, 2);

	}

	@Override
	public void init() {
		name = "Sword";
		
		hasHit = false;

	}
	
	public float[] getPos(boolean mousedown, boolean mousepressed){
		float distance=0;
		float polarAngle= 0;
		float angle=0;
		stageTimer++;
		float constant=1;
		int index = (int) (stageTimer*constant);
		switch(stage){
		case REST:
			distance = REST_DIST;
			polarAngle = REST_PANG;
			angle = REST_ANGL;
			
			if(mousedown && owner.use_stam(SWING1_STAM)){
				stage=WINDUP1;
				stageTimer = 0;
			}
			break;
			
		case WINDUP1:
			if(index<WINDUP1_TIME){
				distance=(int) ((WINDUP1_DIST-REST_DIST)*(index/WINDUP1_TIME))+REST_DIST;
				polarAngle = (int) ((WINDUP1_PANG-REST_PANG)*(index/WINDUP1_TIME))+REST_PANG;
				angle = (int) ((WINDUP1_ANGL-REST_ANGL)*(index/WINDUP1_TIME))+REST_ANGL;
			} else {
				distance = WINDUP1_DIST;
				polarAngle = WINDUP1_PANG;
				angle = WINDUP1_ANGL;
			}
			if(index>WINDUP1_TIME && !mousedown){
				stageTimer = 0;
				stage = SWING1;
				if(hasHit) hasHit = false;
			}
			break;
		case SWING1:
			if(index<SWING1_TIME){
				distance=(int) ((SWING1_DIST-WINDUP1_DIST)*(index/SWING1_TIME))+WINDUP1_DIST;
				polarAngle = (int) ((SWING1_PANG-WINDUP1_PANG)*(index/SWING1_TIME))+WINDUP1_PANG;
				angle = (int) ((SWING1_ANGL-WINDUP1_ANGL)*(index/SWING1_TIME))+WINDUP1_ANGL;
			} else {
				if(!hasHit){
					hasHit = true;
				}
				distance = SWING1_DIST;
				polarAngle = SWING1_PANG;
				angle = SWING1_ANGL;
			}
			if(index>SWING1_TIME && mousedown && owner.use_stam(SWING2_STAM)){
				stageTimer = 0;
				stage = WINDUP2;
			}
			if(index>SWING1_CDWN + SWING1_TIME ){
				stageTimer = 0;
				stage = WINDDOWN1;
			}
			break;
		case WINDDOWN1:
			distance=(int) ((REST_DIST-SWING1_DIST)*(index/WINDDOWN1_TIME))+SWING1_DIST;
			polarAngle = (int) ((REST_PANG-SWING1_PANG)*(index/WINDDOWN1_TIME))+SWING1_PANG;
			angle = (int) ((REST_ANGL-SWING1_ANGL)*(index/WINDDOWN1_TIME))+SWING1_ANGL;
			if(index>WINDDOWN1_TIME){
				stageTimer = 0;
				stage = REST;
			}
			break;
		case WINDUP2:
			if(index<WINDUP2_TIME){
				distance=(int) ((WINDUP2_DIST-SWING1_DIST)*(index/WINDUP2_TIME))+SWING1_DIST;
				polarAngle = (int) ((WINDUP2_PANG-SWING1_PANG)*(index/WINDUP2_TIME))+SWING1_PANG;
				angle = (int) ((WINDUP2_ANGL-SWING1_ANGL)*(index/WINDUP2_TIME))+SWING1_ANGL;
			} else {
				distance = WINDUP2_DIST;
				polarAngle = WINDUP2_PANG;
				angle = WINDUP2_ANGL;
			}
			if(index>WINDUP2_TIME && !mousedown){
				stageTimer = 0;
				stage = SWING2;
				if(hasHit) hasHit = false;
			}
			break;
		case SWING2:
			if(index<SWING2_TIME){
				distance=(int) ((SWING2_DIST-WINDUP2_DIST)*(index/SWING2_TIME))+WINDUP2_DIST;
				polarAngle = (int) ((SWING2_PANG-WINDUP2_PANG)*(index/SWING2_TIME))+WINDUP2_PANG;
				angle = (int) ((SWING2_ANGL-WINDUP2_ANGL)*(index/SWING2_TIME))+WINDUP2_ANGL;
			} else {
				if(!hasHit){
					hasHit = true;
				}
				distance = SWING2_DIST;
				polarAngle = SWING2_PANG;
				angle = SWING2_ANGL;
			}
			if(index>SWING2_TIME && mousedown && owner.use_stam(SWING3_STAM)){
				stageTimer = 0;
				stage = WINDUP3;
			}
			if(index>SWING2_CDWN + SWING2_TIME){
				stageTimer = 0;
				stage = WINDDOWN2;
			}
			break;
		case WINDDOWN2:
			distance=(int) ((REST_DIST-SWING2_DIST)*(index/WINDDOWN2_TIME))+SWING2_DIST;
			polarAngle = (int) ((REST_PANG-SWING2_PANG)*(index/WINDDOWN2_TIME))+SWING2_PANG;
			angle = (int) ((REST_ANGL-SWING2_ANGL)*(index/WINDDOWN2_TIME))+SWING2_ANGL;
			if(index>WINDDOWN2_TIME){
				stageTimer = 0;
				stage = REST;
			}
			break;
		case WINDUP3:
			if(index<WINDUP3_TIME){
				distance=(int) ((WINDUP3_DIST-SWING2_DIST)*(index/WINDUP3_TIME))+SWING2_DIST;
				polarAngle = (int) ((WINDUP3_PANG-SWING2_PANG)*(index/WINDUP3_TIME))+SWING2_PANG;
				angle = (int) ((WINDUP3_ANGL-SWING2_ANGL)*(index/WINDUP3_TIME))+SWING2_ANGL;
			} else {
				distance = WINDUP3_DIST;
				polarAngle = WINDUP3_PANG;
				angle = WINDUP3_ANGL;
			}
			if(index>WINDUP3_TIME && !mousedown){
				stageTimer = 0;
				stage = SWING3;
				if(hasHit) hasHit = false;
			}
			break;
		case SWING3:
			if(index<SWING3_TIME){
				distance=(int) ((SWING3_DIST-WINDUP3_DIST)*(index/SWING3_TIME))+WINDUP3_DIST;
				polarAngle = (int) ((SWING3_PANG-WINDUP3_PANG)*(index/SWING3_TIME))+WINDUP3_PANG;
				angle = (int) ((SWING3_ANGL-WINDUP3_ANGL)*(index/SWING3_TIME))+WINDUP3_ANGL;
			} else {
				if(!hasHit){
					hasHit = true;
				}
				distance = SWING3_DIST;
				polarAngle = SWING3_PANG;
				angle = SWING3_ANGL;
			}
			if(index>SWING3_CDWN + SWING3_TIME){
				stageTimer = 0;
				stage = WINDDOWN3;
			}
			break;
		case WINDDOWN3:
			distance=(int) ((REST_DIST-SWING3_DIST)*(index/WINDDOWN3_TIME))+SWING3_DIST;
			polarAngle = (int) ((REST_PANG-SWING3_PANG)*(index/WINDDOWN3_TIME))+SWING3_PANG;
			angle = (int) ((REST_ANGL-SWING3_ANGL)*(index/WINDDOWN3_TIME))+SWING3_ANGL;
			if(index>WINDDOWN3_TIME){
				stageTimer = 0;
				stage = REST;
			}
			break;
		}
		
		return new float[]{distance,polarAngle,angle};
		
	}

	@Override
	public boolean isInUse() {
		return stage == WINDUP1||stage == WINDUP2||stage == WINDUP3||stage == SWING1||stage == SWING2||stage == SWING3||stage == WINDDOWN1||stage == WINDDOWN2||stage == WINDDOWN3;
	}

	public boolean inAttack() {
		return !hasHit && (stage == SWING1 || stage == SWING2 || stage == SWING3);
	}
	
	public void hit(Character e, Projectile projectile) {
		
		e.knownEntities.add(owner);
		hasHit = true;
		float weaponangle = graphic.angle+135;
		float angleModifier;
		float cur_dmgMult = 0;
		float cur_knockMult = 0;
		float knock = 0;
		if(stage == SWING1){
			angleModifier = -90;
			cur_dmgMult = dmgMult[0];
			cur_knockMult = knockMult[0];
		}
		else if(stage == SWING2){
			angleModifier = 90;
			cur_dmgMult = dmgMult[1];
			cur_knockMult = knockMult[1];
		}
		else{
			angleModifier = 0;
			cur_dmgMult = dmgMult[2];
			cur_knockMult = knockMult[2];
		}
		if(e.damage(damage*cur_dmgMult,hitEffects())>0){
			
			float xSword = (float) (Math.cos((weaponangle+angleModifier)/180f*Math.PI)*knockstr);
			float ySword = (float) (Math.sin((weaponangle+angleModifier)/180f*Math.PI)*knockstr);
			float xOwner = (float) (Math.cos((weaponangle)/180*Math.PI)*knockstr);
			float yOwner = (float) (Math.sin((weaponangle)/180*Math.PI)*knockstr);
			Vector2 knockVec = new Vector2();
			knockVec.x = (xSword*(1-knockratio)+xOwner*(knockratio))*cur_knockMult;
			knockVec.y = (ySword*(1-knockratio)+yOwner*(knockratio))*cur_knockMult;
			e.acel(knockVec, false);
		}
	}
	
	public ArrayList<Effect> hitEffects(){
		ArrayList<Effect> effects = new ArrayList<Effect>();
		effects.add(new Stun(world, 30));
		return effects;
		
	}
}
