package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;

public class Sword extends Meele {

	private final int REST = 0;
	private final int WINDUP = 1;
	private final int SWING1 = 2;
	private final int SWING2 = 3;
	private final int SWING3 = 4;
	private final int WINDDOWN = 5;
	
	public Sword(int damage, int cooldown, int speed) {
		super(damage, cooldown,speed, new Texture("Sword.png"));
		desc = "Real sword I swear! \n\n Damage: "+damage+"\n Cooldown: "+cooldown;
	}

	@Override
	public void init() {
		name = "Sword";
		
	}
	
	public int[] getPos(boolean mousedown){
		int distance=0;
		int polarAngle= 0;
		int angle=0;
		stageTimer++;
		int index = stageTimer;
		switch(stage){
		case REST:
			if(mousedown){
				stage=WINDUP;
				stageTimer = 0;
			}
			break;
			
		case WINDUP:
			distance=(int) (5*(index/10f));
			polarAngle = (int) (30*(index/10f));
			angle = (int) (45*(index/10f));
			if(index>10){
				stageTimer = 0;
				stage = SWING1;
			}
			break;
		case SWING1:
			distance = (int) (25*(index/30f))+5; // 30
			polarAngle = (int) (-90*(index/30f))+30; // -60
			angle = (int) (-85*(index/30f))+45; // -40
			if(index>30){
				stageTimer = 0;
				stage = SWING2;
			}
			break;
		case SWING2:
			distance = (int) (-25*(index/30f))+30; // 5
			polarAngle = (int) (95*(index/30f))-60; // 35
			angle = (int) (80*(index/30f))-40; // 40
			if(index>30){
				stageTimer = 0;
				stage = SWING3;
			}
			break;
		case SWING3:
			if(index<15){
				distance = (int) (4*((index)/15f))+5; //10
				polarAngle = (int) (-65*index/15f)+35; //-30
				angle = (int) (-30*(index/15f))+40; //10
			}else{
				distance = (int) (25*((index-15f)/15f))+10; //35
				polarAngle = (int) (0*((index-15f)/15f))-30; //-30
				angle = (int) (0*((index-15f)/15f))+10; //10
			}
			if(index>30){
				stageTimer = 0;
				stage = WINDDOWN;
			}
			break;
		case WINDDOWN:
			distance=0;
			polarAngle= 0;
			angle = 0;
			if(index>20){
				stageTimer = 0;
				stage = REST;
			}
			break;
		}
		
		return new int[]{distance,polarAngle,angle};
		
	}

}
