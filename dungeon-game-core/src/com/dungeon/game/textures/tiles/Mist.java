package com.dungeon.game.textures.tiles;

import com.dungeon.game.textures.AnimatedTexture;

public class Mist extends ProceduralTile implements AnimatedTexture {
	
	private int timer;
	
	public Mist(int seed, int x, int y) {
		super(new Object[]{seed, x, y});
	}
	
	
	public void generateTexture(Object[] args) {
		timer = 0;
		
	}
	
	
	public void update(){
		timer+=1;
	}
}
