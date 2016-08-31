package com.dungeon.game.generator.objects;

import java.util.ArrayList;

import com.dungeon.game.world.World;

public class GenHall extends GenObject {
	
	private ArrayList<int[]> tiles;

	public GenHall(World world, int x, int y) {
		super(world, x, y, 1, 1);
		
		tiles = new ArrayList<int[]>();
		tiles.add(new int[]{0, 0});
		reservedTiles[0][0] = true;
	}
	
	public void addTile(int dir){
		int x = 0, y = 0;
		if(dir == 0){
			x = tiles.get(tiles.size() - 1)[0] - 1;
			y = tiles.get(tiles.size() - 1)[1];
		}else if(dir == 1){
			x = tiles.get(tiles.size() - 1)[0];
			y = tiles.get(tiles.size() - 1)[1] - 1;
		}else if(dir == 2){
			x = tiles.get(tiles.size() - 1)[0] + 1;
			y = tiles.get(tiles.size() - 1)[1];
		}else if(dir == 3){
			x = tiles.get(tiles.size() - 1)[0];
			y = tiles.get(tiles.size() - 1)[1] - 1;
		}
		tiles.add(new int[]{x, y});
		if(x >= width){
			width++;
			boolean[][] old = reservedTiles;
			reservedTiles = new boolean[width][height];
			for(int x1 = 0; x1 < width - 1; x1++){
				for(int y1 = 0; y1 < height; y1++){
					reservedTiles[x1][y1] = old[x1][y1];
				}
			}
		}else if(y >= height){
			height++;
			boolean[][] old = reservedTiles;
			reservedTiles = new boolean[width][height];
			for(int x1 = 0; x1 < width; x1++){
				for(int y1 = 0; y1 < height - 1; y1++){
					reservedTiles[x1][y1] = old[x1][y1];
				}
			}
		}else if(x < 0){
			width++;
			boolean[][] old = reservedTiles;
			reservedTiles = new boolean[width][height];
			for(int x1 = 1; x1 < width; x1++){
				for(int y1 = 0; y1 < height; y1++){
					reservedTiles[x1][y1] = old[x1 - 1][y1];
				}
			}
			x++;
		}else if(y < 0){
			height++;
			boolean[][] old = reservedTiles;
			reservedTiles = new boolean[width][height];
			for(int x1 = 0; x1 < width; x1++){
				for(int y1 = 1; y1 < height; y1++){
					reservedTiles[x1][y1] = old[x1][y1 - 1];
				}
			}
			y++;
		}
		reservedTiles[x][y] = true;
	}

}
