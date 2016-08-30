package com.dungeon.game.entity.character.enemy;

import java.util.ArrayList;

import com.dungeon.game.entity.Drop;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Gold;
import com.dungeon.game.item.Item;
import com.dungeon.game.pathing.Heuristic;
import com.dungeon.game.pathing.Path;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Enemy extends Character {
	
	protected int[] targetTile;
	
	
	public Enemy(World world, float x, float y, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
	}
	
	protected void findPath(ArrayList<Entity> entities, float[] target){
		float targetX = x;
		float targetY = y;
		if(stagerTimer == 0){
			Path p = new Path(world);
			world.curFloor.heiGraph.setLevel(0);
			world.curFloor.findPath(world.curFloor.heiGraph.getClosestNode(x, y), world.curFloor.heiGraph.getClosestNode(target[0] + 0.5f, target[1] + 0.5f), new Heuristic(), p);//new int[]{(int) (x/Tile.TS),(int) (y/Tile.TS)},new int[]{(int) (target[0]/Tile.TS),(int) (target[1]/Tile.TS)});
			path = p.getPath();
			if(path.size() > 0) targetTile = p.getTargTile();
		}
		if(targetTile!=null){
				moveTo = targetTile;
				targetX = targetTile[0]*Tile.TS+Tile.TS/2;
				targetY = targetTile[1]*Tile.TS+Tile.TS/2;
		}
		
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
	}
	
	public void dead(){
		super.dead();
		if(equipItems[0]!=null)equipItems[0].unequip();
		if(equipItems[1]!=null)equipItems[1].unequip();
		
		ArrayList<Item> drops = inv.getDrops();
		for(Item item: drops){
			Slot slot = new Slot(world, new int[]{0, 0, 0}, null);
			slot.item = item;
			Drop drop = new Drop(world, x, y, slot);
			world.entities.add(drop);
		}
		
		if(gold > 0) {
			Slot goldSlot = new Slot(world, new int[]{0,0,0}, null);
			
			Item goldItem = new Gold(world);
			goldItem.stack = gold;
			
			goldSlot.item = goldItem;
			
			Drop goldDrop = new Drop(world, x, y, goldSlot);
			
			world.entities.add(goldDrop);
		}
	}
}
