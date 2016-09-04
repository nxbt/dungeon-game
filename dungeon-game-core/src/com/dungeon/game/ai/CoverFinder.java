package com.dungeon.game.ai;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class CoverFinder {

	//method to find suitable cover from enemy fire
	//world is the world
	//entity is the entity doing the cover-finding
	//danger is the threat that the entity is hiding from
	//pos is the int array to be filled with the coordinates of the cover tile
	//fire back is weather the entity should chose a position from which they can shoot at danger
	//returns weather or not an applicable cover was found
	public static boolean findCover(World world, Entity entity, Entity danger, int[] pos, float[] lineOfCover, boolean fireBack, int range){
		//psuedo code is something like this. any tile that HAS NO LOS to danger that is next to a tile WITH LOS to danger is considered cover when needing to shoot at danger.
		//otherwise any tile without LOS to danger that is far from danger is cover.
		ArrayList<float[]> rays = new ArrayList<float[]>(); //{startX,startY,endX,endy}
		for(int i = -180; i < 180; i+=18){
			rays.add(new float[]{danger.x,danger.y,danger.x+(float) (Math.cos((i)/180f*Math.PI)*range*(float)Tile.TS),danger.y+(float) (Math.sin((i)/180f*Math.PI)*range*(float)Tile.TS)});
		}
		for(int[] corner: world.curFloor.corners){
			if(Math.sqrt((danger.x-corner[0])*(danger.x-corner[0])+(danger.y-corner[1])*(danger.y-corner[1]))<range*Tile.TS){
				float angleSeg = (float) (Math.atan2(corner[1]-danger.y,corner[0]-danger.x)*180f/Math.PI);
				if(corner[2] == 0) {
					rays.add(new float[]{danger.x,danger.y,danger.x+(float) (Math.cos((angleSeg+0.01f)/180f*Math.PI)*range*(float)Tile.TS),danger.y+(float) (Math.sin((angleSeg+0.01)/180f*Math.PI)*range*(float)Tile.TS)});
					rays.add(new float[]{danger.x,danger.y,danger.x+(float) (Math.cos((angleSeg-0.01f)/180f*Math.PI)*range*(float)Tile.TS),danger.y+(float) (Math.sin((angleSeg-0.01)/180f*Math.PI)*range*(float)Tile.TS)});
				}
				else {
					rays.add(new float[]{danger.x,danger.y,danger.x+(float) (Math.cos((angleSeg)/180f*Math.PI)*range*(float)Tile.TS),danger.y+(float) (Math.sin((angleSeg)/180f*Math.PI)*range*(float)Tile.TS)});
				}
			}
		}
		
		//calculate the verticies
		
		float[][] verticies = new float[rays.size()][2];

		ArrayList<float[]> edges = new ArrayList<float[]>();
		
		for(float[] edge: world.curFloor.edges){
			if(Intersector.distanceSegmentPoint(edge[0], edge[1], edge[2], edge[3], danger.x, danger.y)<range*Tile.TS)edges.add(edge);
		}
		
		Vector2 endVertex = new Vector2(0,0);
		for(int i = 0; i <rays.size();i++){
			endVertex.x = rays.get(i)[2];
			endVertex.y = rays.get(i)[3];
			for(float[] edge: edges){
				Intersector.intersectSegments(rays.get(i)[0],rays.get(i)[1], endVertex.x,endVertex.y, edge[0],edge[1], edge[2],edge[3],endVertex);
			}
			verticies[i] = (new float[]{endVertex.x, endVertex.y});
		}
		
		//calculate the angles of each vertex
		float[] vertexAngles = new float[verticies.length];
		for(int i = 0; i < verticies.length; i++){
			vertexAngles[i] = (float) Math.atan2(verticies[i][1]-danger.y,verticies[i][0]-danger.x);
		}
		
		//reorder points to be in counterclockwise fashion.
		Arrays.sort(vertexAngles);
		
		float[] finalVerticies = new float[vertexAngles.length*2];
		for(int i = 1; i < finalVerticies.length; i+=2){
			for(float[] vertex: verticies){
				if(vertexAngles[i/2] == (float)Math.atan2(vertex[1]-danger.y,vertex[0]-danger.x)){
					finalVerticies[i-1] = vertex[0];
					finalVerticies[i] = vertex[1];
					break;
				}
			}
		}
		
		//create the visPolygon

		Polygon dangerPolygon = new Polygon(finalVerticies);

		ArrayList<int[]> coverTiles = new ArrayList<int[]>();
		//fix for off the map
		int startX = Math.max((int) Math.ceil(danger.x/Tile.TS - range), 0);
		int endX = (int) Math.min(Math.floor(danger.x/Tile.TS + range), world.curFloor.tm[0].length-1);
		int startY = Math.max((int) Math.ceil(danger.y/Tile.TS - range), 0);
		int endY = (int) Math.min(Math.floor(danger.y/Tile.TS + range), world.curFloor.tm.length-1);
		for(int i = startX; i < endX; i++){
			for(int k = startY; k < endY; k++){
				if(world.curFloor.tm[k][i].data == 0 && Math.sqrt((i-danger.x/32)*(i-danger.x/32)+(k-danger.y/32)*(k-danger.y/32)) < range){
					boolean cover = false;
					if(Intersector.intersectPolygons(dangerPolygon, new Polygon(new float[]{i*Tile.TS, k*Tile.TS,i*Tile.TS, (k+1)*Tile.TS,(i+1)*Tile.TS, (k+1)*Tile.TS,(i+1)*Tile.TS, k*Tile.TS}), new Polygon())){
						cover = true;
					}
					if(cover){
						cover = false;
						int[][] tilesToCheck = new int[4][2];
						tilesToCheck[0] = new int[]{i-1,k};
						tilesToCheck[1] = new int[]{i+1,k};
						tilesToCheck[2] = new int[]{i,k-1};
						tilesToCheck[3] = new int[]{i,k+1};
						for(int[] t: tilesToCheck){
							boolean tileIsBlocking = true;
							int x = t[0];
							int y = t[1];
							if(world.curFloor.tm[y][x].data == 0){
								if(Intersector.intersectPolygons(dangerPolygon, new Polygon(new float[]{x*Tile.TS, y*Tile.TS,x*Tile.TS, (y+1)*Tile.TS,(x+1)*Tile.TS, (y+1)*Tile.TS,(x+1)*Tile.TS, y*Tile.TS}), new Polygon())){
									tileIsBlocking = false;
								}
							}else tileIsBlocking = false;
							if(tileIsBlocking){
								coverTiles.add(new int[]{i, k, x, y});
								cover = true;
								break;
							}
						}
					}
				}
			}
		}
		
		for(int i = 0; i < coverTiles.size(); i++){
			int[] t = coverTiles.get(i);
			if(Math.sqrt((danger.x/Tile.TS - t[0])*(danger.x/Tile.TS - t[0])+(danger.y/Tile.TS - t[1])*(danger.y/Tile.TS - t[1])) < 2){
				coverTiles.remove(i);
				i--;
			}
		}
		if(coverTiles.size() == 0)return false;
		else{
			//figure out which cover tile is best
			int[] closestCover = coverTiles.get(0);
			for(int[] tile: coverTiles){
				if(Math.sqrt((entity.x/Tile.TS - tile[0])*(entity.x/Tile.TS - tile[0])+(entity.y/Tile.TS - tile[1])*(entity.y/Tile.TS - tile[1])) < Math.sqrt((entity.x/Tile.TS - closestCover[0])*(entity.x/Tile.TS - closestCover[0])+(entity.y/Tile.TS - closestCover[1])*(entity.y/Tile.TS - closestCover[1])))closestCover = tile;
			}
			pos[0] = closestCover[0];
			pos[1] = closestCover[1];
			pos[2] = closestCover[2];
			pos[3] = closestCover[3];
			int xDiff = pos[0] - pos[2];
			int yDiff = pos[1] - pos[3];
			if(xDiff > 0){
				lineOfCover[0] = pos[0]*Tile.TS + Tile.TS * (world.curFloor.tm[pos[1]][pos[0]+1].data == 1?0.5f:1.5f);
				lineOfCover[1] = pos[1]*Tile.TS + Tile.TS / 2 ;
				lineOfCover[2] = pos[2]*Tile.TS - Tile.TS * (world.curFloor.tm[pos[3]][pos[2]-1].data == 1?-0.5f:0.5f);
				lineOfCover[3] = pos[3]*Tile.TS + Tile.TS / 2;
			}else if(xDiff < 0){
				lineOfCover[0] = pos[0]*Tile.TS - Tile.TS * (world.curFloor.tm[pos[1]][pos[0]-1].data == 1?-0.5f:0.5f);
				lineOfCover[1] = pos[1]*Tile.TS + Tile.TS / 2;
				lineOfCover[2] = pos[2]*Tile.TS + Tile.TS * (world.curFloor.tm[pos[3]][pos[2]+1].data == 1?0.5f:1.5f);
				lineOfCover[3] = pos[3]*Tile.TS + Tile.TS / 2;
			}else if(yDiff > 0){
				lineOfCover[0] = pos[0]*Tile.TS + Tile.TS / 2;
				lineOfCover[1] = pos[1]*Tile.TS + Tile.TS * (world.curFloor.tm[pos[1]+1][pos[0]].data == 1?0.5f:1.5f);
				lineOfCover[2] = pos[2]*Tile.TS + Tile.TS / 2;
				lineOfCover[3] = pos[3]*Tile.TS - Tile.TS * (world.curFloor.tm[pos[3]-1][pos[2]].data == 1?-0.5f:0.5f);
			}else if(yDiff < 0){
				lineOfCover[0] = pos[0]*Tile.TS + Tile.TS / 2;
				lineOfCover[1] = pos[1]*Tile.TS - Tile.TS * (world.curFloor.tm[pos[1]-1][pos[0]].data == 1?-0.5f:0.5f);
				lineOfCover[2] = pos[2]*Tile.TS + Tile.TS / 2;
				lineOfCover[3] = pos[3]*Tile.TS + Tile.TS * (world.curFloor.tm[pos[3]+1][pos[2]].data == 1?0.5f:1.5f);
			}
			
			return true;
		}
		
	}
}
