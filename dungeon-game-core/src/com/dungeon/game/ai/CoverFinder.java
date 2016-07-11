package com.dungeon.game.ai;

import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.World;

public class CoverFinder {

	//method to find suitable cover from enemy fire
	//world is the world
	//entity is the entity doing the cover-finding
	//danger is the threat that the entity is hiding from
	//pos is the int array to be filled with the coordinates of the cover tile
	//fire back is weather the entity should chose a position from which they can shoot at danger
	//returns weather or not an applicable cover was found
	public static boolean findCover(World world, Entity entity, Entity danger, int[] pos, boolean fireBack){
		return false;
		
	}
}
