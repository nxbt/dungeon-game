package com.dungeon.game.entity;

import java.util.ArrayList;

public class Pathfinder {
	public int[][] map;
	
	public Pathfinder(){
		
	}
	
	public void setMap(int[][] map){
		this.map = map;
	}
	
	public ArrayList<int[]> findPath(int[] start, int[] end){
		ArrayList<int[]> queue = new ArrayList<int[]>();
		queue.add(new int[]{end[0],end[1],0});
		int count = 0;
		boolean endQueue = false;
		for(int[] i: queue){
			count++;
			ArrayList<int[]> toAdd = new ArrayList<int[]>();
			toAdd.add(new int[]{i[0]+1,i[1],count});
			toAdd.add(new int[]{i[0],i[1]+1,count});
			toAdd.add(new int[]{i[0]-1,i[1],count});
			toAdd.add(new int[]{i[0],i[1]-1,count});
			if(i[0]+1>map[0].length||map[i[1]][i[0]+1]==0)toAdd.remove(new int[]{i[0],i[1],count});
			if(i[1]+1>map.length||map[i[1]+1][i[0]]==0)toAdd.remove(new int[]{i[0],i[1],count});
			if(i[0]-1<0||map[i[1]][i[0]-1]==0)toAdd.remove(new int[]{i[0],i[1],count});
			if(i[1]+1>map.length||map[i[1]-1][i[0]]==0)toAdd.remove(new int[]{i[0],i[1],count});
			for(int[] k: toAdd){
				for(int[] e: queue){
					if(k[0]==e[0]&&k[1]==e[1]&&k[0]>=e[0])toAdd.remove(k);
				}
			}
			for(int[] k: toAdd){
				if(k[0] == start[0]&&k[1] == start[1])endQueue = true;
				queue.add(k);
			}
			if(endQueue)break;
		}
		return queue;
	}
}
