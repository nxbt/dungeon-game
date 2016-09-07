package com.dungeon.game.entity.character.friend;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.character.PrintPools;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.light.Light;
import com.dungeon.game.textures.entity.Person;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Villager extends Friend {
	
	private int[] wanderTile;

	public Villager(World world, float x, float y) {
		super(world, x, y, 32, 32, "mentor.png");
		sprite = new Person().texture;
		
		speechColor = new Color(0.3f,0.1f,0.4f,1);
		speechBubble.setColor();	
		
		name = "Villager";
		
		light = new Light(world, x, y, 1, 100, 0, this);
		
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
		
		acel = 4f;
		fric = 0.4f;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		genVisBox();
		
		originX = 16;
		originY = 16;
		
		inv = new Inventory(world, new int[][] {}, 10, 100);
		
		equipSlots = new Slot[]{
				new Slot(world, new int[]{0, 0, 0}, inv),
				new Slot(world, new int[]{0, 0, 0}, inv)
		};
		
		equipItems = new Equipable[2];
		
		wanderTile = new int[2];
		
		wanderTile[0] = (int) (x/Tile.TS);
		wanderTile[1] = (int)(y/Tile.TS);
		

		moveTo = new int[2];
		
		moveTo[0] = wanderTile[0];
		moveTo[1] = wanderTile[1];
		
		path =  new ArrayList<int[]>();
		
		
		dialogue = new Dialogue(world, this);

		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this,"U halfin a gigle m8?", "end"));
		
		printPool = PrintPools.humanPool;

	}

	@Override
	public void calc() {
		showPopupBubble("");
		
		if(world.hudEntities.contains(dialogue)) target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y, world.player.x-x));
		else wander();
		
		if(flipX) target_angle+=target_angle>0?-180:180;
	}
	
	private void wander(){
		if(Math.random()<0.001||(wanderTile[0] == (int)(x/Tile.TS) && wanderTile[1] == (int)(y/Tile.TS))){
			//new target location
			do{
				wanderTile = new int[]{1+(int)(Math.random()*world.curFloor.tm[0].length-1),1+(int)(Math.random()*world.curFloor.tm.length-1)};
			}while(Tile.isSolid(world.curFloor.tm[wanderTile[1]][wanderTile[0]]));
//			Path p = world.curFloor.pathfinder.findPath(x, y, wanderTile[0]*Tile.TS + Tile.TS/2, wanderTile[1]*Tile.TS + Tile.TS/2);
//			path = p.getPath();
//			targetTile = Path.getTargTile(world, path);
		}
		
		if(staggerTimer == 0){
//			long s = System.nanoTime();
			findPath(world.entities, new float[]{wanderTile[0]*Tile.TS + Tile.TS/2, wanderTile[1]*Tile.TS + Tile.TS/2});
		}
		moveToTarg();
		moveTo = targetTile;
		target_angle = move_angle;
		if(target_angle > 360)target_angle-=360;
	}

	@Override
	public void post() {
		if(speechBubble.endText.equals("")){
			world.hudEntities.remove(speechBubble);
		}else{
			if(!world.hudEntities.contains(speechBubble))world.hudEntities.add(speechBubble);	
			speechBubble.x = x-world.cam.x+world.cam.width/2;
			speechBubble.y = y-world.cam.y+world.cam.height/2;
		}

	}

}
