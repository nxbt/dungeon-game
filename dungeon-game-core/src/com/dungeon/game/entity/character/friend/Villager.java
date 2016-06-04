package com.dungeon.game.entity.character.friend;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.criteria.Criteria;
import com.dungeon.game.criteria.Invert;
import com.dungeon.game.criteria.Said;
import com.dungeon.game.criteria.True;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Villager extends Friend {
	
	protected int[] targetTile;
	
	private int[] wanderTile;

	public Villager(World world, float x, float y) {
		super(world, x, y, 32, 32, "mentor.png");
		speechColor = new Color(0.3f,0.1f,0.4f,1);
		speechBubble.setColor();	
		
		name = "Villager";
		
		torq = 5;
		
		vision = 10;
		hearing = 3;
		
		speechRadius = 3;
		
		maxLife = 1;
		maxStam = 1;
		maxMana = 1;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		acel = 1.5f;
		mvel = 3;
		fric = 0.5f;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		
		origin_x = 16;
		origin_y = 16;
		
		wanderTile = new int[2];
		
		wanderTile[0] = (int) x/Tile.TS;
		wanderTile[1] = (int) y/Tile.TS;
		

		moveTo = new int[2];
		
		moveTo[0] = wanderTile[0];
		moveTo[1] = wanderTile[1];
		
		
		dialogue = new Dialogue(world, this);

		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this,"U halfin a gigle m8?", "end"));

	}

	@Override
	public void calc() {
		if(seenEntities.contains(world.player)){
			if(flipX)target_angle+=target_angle>0?-180:180;
			if(!world.player.fightMode&&speechBubble.endText.equals("")){
				if(!world.hudEntities.contains(dialogue))speechBubble.updateText("A Bunch of Bullshit");
				speechBubble.dismissed = false;
			}
			
		}else{
			if(world.hudEntities.contains(dialogue)) {
				speechBubble.updateText("");
				dialogue.close();
			}
		}
		if(world.player.fightMode||Math.sqrt((x-world.player.x)*(x-world.player.x)+(y-world.player.y)*(y-world.player.y))>speechRadius*Tile.TS)speechBubble.updateText("");
		//random wandering engdge!
		wander();
		
	}
	
	private void wander(){
		if(Math.random()<0.01||(wanderTile[0] == (int)(x/Tile.TS) && wanderTile[1] == (int)(y/Tile.TS))){
			//new target location
			boolean foundTile = false;
			while(!foundTile){
				wanderTile = new int[]{(int)(Math.random()*world.curFloor.tm[0].length),(int)(Math.random()*world.curFloor.tm.length)};
				targetTile = world.areaMap.findPath(new int[]{(int) (x/Tile.TS),(int) (y/Tile.TS)},new int[]{(int) (wanderTile[0]),(int) (wanderTile[1])});
				if(targetTile[0]!=(int)(x/Tile.TS)||targetTile[1]!=(int)(y/Tile.TS))foundTile = true;
			}
		}
		if(stagerTimer == 0)targetTile = world.areaMap.findPath(new int[]{(int) (x/Tile.TS),(int) (y/Tile.TS)},new int[]{(int) (wanderTile[0]),(int) (wanderTile[1])});
		path = world.areaMap.lastPath;
		
		if(targetTile!=null){
			moveTo = targetTile;
			float targetX = targetTile[0]*Tile.TS+Tile.TS/2;
			float targetY = targetTile[1]*Tile.TS+Tile.TS/2;
			
			boolean inp_rt = false;
			boolean inp_lt = false;
			boolean inp_up = false;
			boolean inp_dn = false;
			
			if(x+2<targetX)inp_rt=true;
			if(x-2>targetX)inp_lt=true;
			if(y+2<targetY)inp_up=true;
			if(y-2>targetY)inp_dn=true;
			if(inp_up && inp_rt) move_angle = 45;
			else if(inp_up && inp_lt) move_angle = 135;
			else if(inp_dn && inp_rt) move_angle = -45;
			else if(inp_dn && inp_lt) move_angle = -135;
			else if(inp_up) move_angle = 90;
			else if(inp_dn) move_angle = -90;
			else if(inp_rt) move_angle = 0;
			else if(inp_lt) move_angle = 180;
			target_angle = move_angle;
			if(flipX)target_angle+=180;
			if(target_angle > 360)target_angle-=360;
		}
	}

	@Override
	public void post() {
		if(speechBubble.endText.equals("")){
			world.hudEntities.remove(speechBubble);
		}else{
			if(!world.hudEntities.contains(speechBubble))world.hudEntities.add(speechBubble);	
			speechBubble.x = x-world.cam.x+world.cam.WIDTH/2;
			speechBubble.y = y-world.cam.y+world.cam.HEIGHT/2;
		}

	}

}
