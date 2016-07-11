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
		//psuedo code is something like this. any tile that HAS NO LOS to danger that is next to a tile WITH LOS to danger is considered cover when needing to shoot at danger.
		//otherwise any tile without LOS to danger that is far from danger is cover.
		return false;
		
	}
}
