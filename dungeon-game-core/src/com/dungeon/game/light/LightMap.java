package com.dungeon.game.light;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.World;

public class LightMap {

	Pixmap lightMap;
	
	Texture lightMapTex;
	
	ArrayList<Light> lights;
	
	public LightMap(int width, int height) {
		lightMap = new Pixmap(width,height,Pixmap.Format.RGBA8888);
		
		lightMap.setColor(0,0,0,1);
		
		lights = new ArrayList<Light>();
	}

	public void update(World world) {
		lightMap.fillRectangle(0, 0, lightMap.getWidth(), lightMap.getHeight());
		
		for(Entity ent: world.entities) {
			if(ent.light != null) {
				ent.light.update(world);
				ent.light.draw(lightMap);
			}
		}
		
		lightMapTex = new Texture(lightMap);
	}
	
	public void draw(SpriteBatch batch) {
		batch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);
		
		batch.draw(lightMapTex, 0, 0);
		
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
}
