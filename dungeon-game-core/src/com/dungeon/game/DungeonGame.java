package com.dungeon.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class DungeonGame extends ApplicationAdapter {
	SpriteBatch batch;
	
	World world;
	
	public ArrayList<Float> frameTimes;
	
	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(1280, 720);
		
		batch = new SpriteBatch();
		
		world = new World(true);
		
		frameTimes = new ArrayList<Float>();
	}

	@Override
	public void render () {
		long s = System.nanoTime();
		world.update();
		System.out.println("Update in in: " + (float)(System.nanoTime() - s)/16000000f + " frames");
//		s = System.nanoTime();
		world.draw(batch);
//		frameTimes.add((float)(System.nanoTime() - s)/16000000f);
//		System.out.println("Render in: " + (float)(System.nanoTime() - s)/16000000f + " frames");
//		float totalTime = 0;
//		for(float f: frameTimes){
//			totalTime+=f;
//		}
//		System.out.println("AVERAGE: " + totalTime/(float)(frameTimes.size()));
	}
	
	@Override
	public void resize(int width, int height) {
	    world.cam.view.update(width, height);
	}
}
