package com.dungeon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.*;

public class GeneratorTest extends ApplicationAdapter {
	SpriteBatch batch;
	
	Generation generation;
	
	Tile[][] tileMap;
	
	HierarchicalGraph pathGraph;
	
	Camera cam;
	
	@Override
	public void create() { //shit ton of bugs here!
		Gdx.graphics.setWindowedMode(1280, 720);
		
		batch = new SpriteBatch();
		
		generation = new CastleRooms(50, 50);
		
		tileMap = generation.map;
		
		pathGraph = generation.getPathGraph();
		
		cam = new Camera();
	}

	@Override
	public void render () {
	
	}
	
	@Override
	public void resize(int width, int height) {
	      
	}
}
