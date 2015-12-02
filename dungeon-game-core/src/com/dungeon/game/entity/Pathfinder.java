package com.dungeon.game.entity;

import java.util.ArrayList;

public class Pathfinder {
	public int[][] map;
	
	public Pathfinder(){
		
	}
	
	public void setMap(int[][] map){
		this.map = map;
	}
	
	public int[] findPath(int[] start, int[] end){
		if(start[0]==end[0]&&start[1]==end[1])return end;
		ArrayList<int[]> queue = new ArrayList<int[]>();
		queue.add(new int[]{end[0],end[1],0});
		int count = 0;
		boolean endQueue = false;
		for(int i = 0; i < queue.size(); i++){
			count++;
			ArrayList<int[]> toAdd = new ArrayList<int[]>();
			if(!(queue.get(i)[0]+1>map[0].length||map[queue.get(i)[1]][queue.get(i)[0]+1]==1))toAdd.add(new int[]{queue.get(i)[0]+1,queue.get(i)[1],count});
			if(!(queue.get(i)[1]+1>map.length||map[queue.get(i)[1]+1][queue.get(i)[0]]==1))toAdd.add(new int[]{queue.get(i)[0],queue.get(i)[1]+1,count});
			if(!(queue.get(i)[0]-1<0||map[queue.get(i)[1]][queue.get(i)[0]-1]==1))toAdd.add(new int[]{queue.get(i)[0]-1,queue.get(i)[1],count});
			if(!(queue.get(i)[1]+1>map.length||map[queue.get(i)[1]-1][queue.get(i)[0]]==1))toAdd.add(new int[]{queue.get(i)[0],queue.get(i)[1]-1,count});
			for(int k = toAdd.size()-1; k>=0;k--){
				for(int[] e: queue){
					toAdd.get(k);
					if(toAdd.get(k)[0]==e[0]&&toAdd.get(k)[1]==e[1]&&toAdd.get(k)[2]>=e[2]){
						toAdd.remove(toAdd.get(k));
						break;
					}
				}
			}
			for(int[] k: toAdd){
				if(k[0] == start[0]&&k[1] == start[1])endQueue = true;
				queue.add(k);
			}
			if(endQueue)break;
		}
		ArrayList<int[]> path = new ArrayList<int[]>();
		boolean gotToTarget = false;
		int[] curTile = new int[]{start[0],start[1]};
		System.out.println("Start X: "+curTile[0]+" Y:"+curTile[1]);
		int curCount=count+1;
		int temp = 0;
		while(!gotToTarget&&temp<10){
			temp++;
			path.add(curTile);
			if(curTile[0]==end[0]&&curTile[1]==end[1])gotToTarget=true;
			else{
				for(int[] i: queue){
					if(curTile[0]+1==i[0]&&curTile[1]==i[1]){
						curCount = i[2];
						curTile = new int[]{i[0],i[1]};
						break;
					}
					if(curTile[0]-1==i[0]&&curTile[1]==i[1]&&i[2]<=curCount){
						curCount = i[2];
						curTile = new int[]{i[0],i[1]};
						break;
					}
					if(curTile[0]==i[0]&&curTile[1]+1==i[1]&&i[2]<=curCount){
						curCount = i[2];
						curTile = new int[]{i[0],i[1]};
						break;
					}
					if(curTile[0]==i[0]&&curTile[1]-1==i[1]&&i[2]<=curCount){
						curCount = i[2];
						curTile = new int[]{i[0],i[1]};
						break;
					}
				}
			}
		}
		System.out.println("Target X: "+path.get(1)[0]+" Y: "+path.get(1)[1]);
		for(int[] i:path){
			System.out.println("PathX: "+i[0]+" Y: "+i[1]);
		}
		return path.get(1);
	}
}
