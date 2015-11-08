package com.dungeon.game.generator;

public abstract class Generation {
	private int width;
	private int height;
	protected int[][] map;
	public Generation(int width, int height){
		this.height = height;
		this.width = width;
		this.map = new int[height][width];
		generateClearDungeon();
	}
	
	public int[][] getMap(){
		return map;
	}
	
	public void generateClearDungeon(){
		for(int i = 0; i<map.length;i++){
			for(int k = 0; k<map[i].length;k++){
				map[i][k]=2;
			}
		}
	}
}
