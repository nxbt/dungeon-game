package com.dungeon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dungeon.game.generator.Generation;
import com.dungeon.game.generator.rooms.Castle;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class GeneratorTest extends ApplicationAdapter {

	public ShapeRenderer shapeRenderer;
	
	Generation generation;
	
	Tile[][] tileMap;
	
	OrthographicCamera cam;
	
	Viewport view;
	
	@Override
	public void create() { //shit ton of bugs here!
		Gdx.graphics.setWindowedMode(1280, 720);
		
		shapeRenderer = new ShapeRenderer();
		int width = 50;
		int height = 50;
		
		generation = new Castle(new World(false), width, height, width/2, height/2, width/2, height/2, 0, new Object[]{"test"});
		
		tileMap = generation.map;
		
		cam = new OrthographicCamera();
		cam.zoom = 1;
		view = new FitViewport(width*Tile.TS*3*0.64f, width*Tile.TS*3*0.36f, cam);
		view.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
		cam.position.set(tileMap[0].length*Tile.TS/2, tileMap.length*Tile.TS/2, 0);
		cam.update();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(cam.combined);
		for(int i = 0; i < tileMap.length; i++){
			for(int k = 0; k < tileMap[0].length; k++){
				if(Tile.isSolid(tileMap[i][k]))shapeRenderer.setColor(Color.RED);
				else shapeRenderer.setColor(Color.BLUE);
				shapeRenderer.rect(k*Tile.TS, i*Tile.TS, Tile.TS, Tile.TS);
			}
		}
		shapeRenderer.end();
	}
	
	@Override
	public void resize(int width, int height) {
	      
	}
}
