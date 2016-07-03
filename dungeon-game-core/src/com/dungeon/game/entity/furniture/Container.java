package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Static;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Container extends Static{
	public Inventory inv;
	
	public Container(World world, float x, float y, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
		
		int[][] invLayout = new int[][]{
			new int[] {0, 8, 8},
			new int[] {0, 48, 8},
			new int[] {0, 88, 8},
			new int[] {0, 128, 8},
			new int[] {0, 168, 8},
			new int[] {0, 8, 48},
			new int[] {0, 48, 48},
			new int[] {0, 88, 48},
			new int[] {0, 128, 48},
			new int[] {0, 168, 48},
			new int[] {0, 8, 88},
			new int[] {0, 48, 88},
			new int[] {0, 88, 88},
			new int[] {0, 128, 88},
			new int[] {0, 168, 88},
			new int[] {0, 8, 128},
			new int[] {0, 48, 128},
			new int[] {0, 88, 128},
			new int[] {0, 128, 128},
			new int[] {0, 168, 128},
			new int[] {0, 8, 168},
			new int[] {0, 48, 168},
			new int[] {0, 88, 168},
			new int[] {0, 128, 168},
			new int[] {0, 168, 168}
		};
		
		inv = new Inventory(world, invLayout, 310, 140);
	}
	
	@Override
	public void calc() {
		if(world.hudEntities.contains(inv.graphic)) {
			if((Math.sqrt(Math.pow((x+d_width/2) - (world.player.x + world.player.d_width/2), 2) + Math.pow((y+d_height/2) - (world.player.y + world.player.d_height/2), 2))) >= world.player.REACH){
				inv.graphic.close();
			}
			for(int i = 0; i< world.curFloor.tm.length;i++){
				for(int k = 0; k <world.curFloor.tm[i].length;k++){
					if(world.curFloor.tm[k][i].data==1){
						float[] verticies = new float[]{i*Tile.TS,k*Tile.TS,(i+1)*Tile.TS,k*Tile.TS,(i+1)*Tile.TS,(k+1)*Tile.TS,(i)*Tile.TS,(k+1)*Tile.TS};
						if(Intersector.intersectSegmentPolygon(new Vector2(x,y), new Vector2(world.player.x,world.player.y), new Polygon(verticies))) inv.graphic.close();
					}
				}
			}
		}
	}
		
	public void hovered() {
		if(world.mouse.lb_pressed&&!world.player.fightMode){
			if(world.hudEntities.contains(inv.graphic)){
				inv.graphic.close();
			}
			else inv.graphic.open();
		}
	}

	@Override
	public void post() {
	}

}
