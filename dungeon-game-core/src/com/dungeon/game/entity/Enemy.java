package com.dungeon.game.entity;

import java.util.ArrayList;

import com.dungeon.game.item.Item;
import com.dungeon.game.item.Slot;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Enemy extends Dynamic {
	public int[] moveTo;
	public ArrayList<int[]> path;
	

	protected Weapon leftEquiped;
	protected Weapon rightEquiped;
	
	protected float[] leftPos;
	protected float[] rightPos;
	
	protected boolean attacking;
	
	
	public Enemy(int x, int y) {
		super(x, y);
	}
	
	protected void findPath(World world, ArrayList<Entity> entities, float[] target){
//		int[][] mapData = new int[map.length][map[0].length];
//		for(int i = 0;i<map.length;i++){
//			for(int k = 0;k<map[0].length;k++){
//				mapData[i][k] = map[i][k].data;
//				Rectangle tile = new Rectangle(k*Tile.TS,i*Tile.TS,Tile.TS,Tile.TS);
//				Rectangle entity;
//				for(Entity e: entities){
//					if(e.solid){
//						entity = e.getBoundingBox();
//						if(tile.overlaps(entity))mapData[i][k]=1;
//					}
//				}
//			}
//		}
//		pathfinder.setData(mapData);
		int[] targetTile = world.areaMap.findPath(new int[]{(int) (x/Tile.TS),(int) (y/Tile.TS)},new int[]{(int) (world.player.x/Tile.TS),(int) (world.player.y/Tile.TS)});
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
		}
	}
	
	public void dead(World world){
		if(leftEquiped!=null)unequip(world, leftEquiped);
		if(rightEquiped!=null)unequip(world, rightEquiped);
		
		ArrayList<Item> drops = inv.getDrops();
		for(Item item: drops){
			Slot slot = new Slot(new int[]{0, 0, 0}, null);
			slot.item = item;
			world.entities.add(new Drop((int)x, (int)y, slot));
		}
	}
}
