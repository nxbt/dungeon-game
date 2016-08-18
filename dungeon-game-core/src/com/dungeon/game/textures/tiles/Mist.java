package com.dungeon.game.textures.tiles;

import com.dungeon.game.textures.AnimatedTexture;

public class Mist extends ProceduralTile implements AnimatedTexture {
	
	private int timer; //timer for animations
	
	private int seed; //seed unique to this mist
	
	private int fadeEdges; //weather this mist should fade around the edges. (yes if the mist should fade in, no if it should not fade in OR cover the whole map)
	
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
