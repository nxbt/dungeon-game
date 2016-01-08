package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Dynamic;

public class Sword extends Meele {

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
	
	public boolean hasHit;
	
	public Sword(int damage, int cooldown, int speed) {
		super(damage, cooldown,speed, new Texture("Sword.png"));
		desc = "Real sword I swear! \n\n Damage: "+damage+"\n Cooldown: "+cooldown;
		knockratio = 0.6f;
		knockstr = 10;
	}

	@Override
	public void init() {
		name = "Sword";
		
		hasHit = false;
	}
	
	public int[] getPos(boolean mousedown, boolean mousepressed){
		int distance=0;
		int polarAngle= 0;
		int angle=0;
		stageTimer++;
		int constant=2;
		int index = stageTimer*constant;
		switch(stage){
		case REST:
			if(mousepressed){
				stage=WINDUP1;
				stageTimer = 0;
			}
			break;
			
		case WINDUP1:
			if(index<10){
				distance=(int) (5*(index/10f)); //5
				polarAngle = (int) (30*(index/10f)); //30
				angle = (int) (55*(index/10f)); //55
			}else{
				distance = 5;
				polarAngle = 30;
				angle = 55;
			}
			if(index>10 && !mousedown){
				stageTimer = 0;
				stage = SWING1;
				if(hasHit) hasHit = false;
			}
			break;
		case SWING1:
			if(index<30){
				distance = (int) (25*(index/30f))+5; // 30
				polarAngle = (int) (-90*(index/30f))+30; // -60
				angle = (int) (-95*(index/30f))+55; // -40
			}else{
				if(!hasHit) hasHit = true;
				distance = 30;
				polarAngle = -60;
				angle = -40;
			}
			if(index>30&&mousepressed){
				stageTimer = 0;
				stage = WINDUP2;
			}
			if(index>90){
				stageTimer = 0;
				stage = WINDDOWN1;
			}
			break;
		case WINDDOWN1:
			distance = (int) (-30*(index/40f))+30; // 0
			polarAngle = (int) (60*(index/40f))-60; // 0
			angle = (int) (40*(index/40f))-40; // 0
			if(index>40){
				stageTimer = 0;
				stage = REST;
			}
			break;
		case WINDUP2:
			if(index<10){
				distance=(int) (0*(index/10f))+30; //30
				polarAngle = (int) (-20*(index/10f))-60; //-80
				angle = (int) (-20*(index/10f))-40; //-60
			}else{
				distance = 30;
				polarAngle = -80;
				angle = -60;
			}
			if(index>10 && !mousedown){
				stageTimer = 0;
				stage = SWING2;
				if(hasHit) hasHit = false;
			}
			break;
		case SWING2:
			if(index<30){
				distance = (int) (-25*(index/30f))+30; // 5
				polarAngle = (int) (115*(index/30f))-80; // 35
				angle = (int) (100*(index/30f))-60; // 40
			}else{
				if(!hasHit) hasHit = true;
				distance = 5;
				polarAngle = 35;
				angle = 40;
			}
			if(index>30&&mousepressed){
				stageTimer = 0;
				stage = WINDUP3;
			}
			if(index>90){
				stageTimer = 0;
				stage = WINDDOWN2;
			}
			break;
		case WINDDOWN2:
			distance = (int) (-5*(index/20f))+5; // 0
			polarAngle = (int) (-35*(index/20f))+35; // 0
			angle = (int) (-40*(index/20f))+40; // 0
			if(index>20){
				stageTimer = 0;
				stage = REST;
			}
			break;
		case WINDUP3:
			if(index<30){
				distance = (int) (15*((index)/30f))+5; //20
				polarAngle = (int) (-85*index/30f)+35; //-50
				angle = (int) (-40*(index/30f))+50; //10
			}else{
				distance = 20;
				polarAngle = -50;
				angle = 10;
			}
			if(index>30 && !mousedown){
				stageTimer = 0;
				stage = SWING3;
				if(hasHit) hasHit = false;
			}
			break;
		case SWING3:
			if(index<10){
				distance = (int) (10*((index)/10f))+20; //30
				polarAngle = (int) (20*((index)/10f))-50; //-30
				angle = (int) (0*((index)/10f))+10; //10
				
			}else{
				if(!hasHit) hasHit = true;
				distance = 30;
				polarAngle = -30;
				angle = 10;
			}
			if(index>30){
				stageTimer = 0;
				stage = WINDDOWN3;
			}
			break;
		case WINDDOWN3:
			distance = (int) (-30*((index)/30f))+30; //0
			polarAngle = (int) (30*index/30f)-30; //0
			angle = (int) (-10*(index/30f))+10; //0
			if(index>30){
				stageTimer = 0;
				stage = REST;
			}
			break;
		}
		
		return new int[]{distance,polarAngle,angle};
		
	}

	@Override
	public boolean isInUse() {
		return stage == WINDUP1||stage == WINDUP2||stage == WINDUP3||stage == SWING1||stage == SWING2||stage == SWING3;
	}

	public boolean inAttack() {
		return !hasHit && (stage == SWING1 || stage == SWING2 || stage == SWING3);
	}
	
	public void hit(Dynamic e) {
		hasHit = true;

		float weaponangle = graphic.angle+135;
		System.out.println(weaponangle);
		float angleModifier;
		if(stage == SWING1)angleModifier = -90;
		else if(stage == SWING2)angleModifier = 90;
		else angleModifier = 0;
		float xSword = (float) (Math.cos((weaponangle+angleModifier)/180*Math.PI)*knockstr);
		float ySword = (float) (Math.sin((weaponangle+angleModifier)/180*Math.PI)*knockstr);
		float xOwner = (float) (Math.cos((weaponangle)/180*Math.PI)*knockstr);
		float yOwner = (float) (Math.sin((weaponangle)/180*Math.PI)*knockstr);
		float xknock = xSword*(1-knockratio)+xOwner*(knockratio);
		float yknock = ySword*(1-knockratio)+yOwner*(knockratio);
		cur_knockback=new float[]{xknock,yknock};
		e.dx += cur_knockback[0];
		e.dy += cur_knockback[1];
	}
}
