package com.dungeon.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dungeon.game.DungeonGame;
import com.dungeon.game.GeneratorTest;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class HierarchicalPathfindingTest extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer; //the shapeRenderer
	
	private OrthographicCamera cam; // the camera
	
	private Viewport view; // the viewport
	
	private int width; //width of the map
	
	private int height; //height of the map
	
	public void create() {
		Gdx.graphics.setWindowedMode(1280, 720); 
		
		shapeRenderer = new ShapeRenderer();
		
		cam = new OrthographicCamera();
		cam.zoom = 1;
		view = new FitViewport(640, 360, cam);
		view.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
		
	}
	
	public static void main (String[] arg) { //MAIN METHOD
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.useGL30 = true;
		new LwjglApplication(new HierarchicalPathfindingTest(), config);
	}
	
	//node class
	
	private class Node{
		
	}
	
	//implementations of Heirarchical Pathfinding classes
	
}
