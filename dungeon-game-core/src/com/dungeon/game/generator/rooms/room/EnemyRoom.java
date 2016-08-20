package com.dungeon.game.generator.rooms.room;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.character.enemy.Goon;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class EnemyRoom extends Room {

	public EnemyRoom(World world, Rectangle roomBase, int[][] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() {
		int numEnemies = (room.length * room[0].length)/10;
		numEnemies*= Math.random()*2f;
		
		while(numEnemies > 0){
			numEnemies--;
			entities.add(new Goon(world, (float)(room[0].length*Tile.TS*Math.random()), (float) (room.length*Tile.TS*Math.random())));
		}
	}

}
