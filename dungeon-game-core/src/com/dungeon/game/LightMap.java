package com.dungeon.game;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class LightMap {

	Pixmap lightMap;
	Pixmap light;
	
	Texture lightMapTex;
	
	public LightMap(int width, int height) {
		lightMap = new Pixmap(width,height,Pixmap.Format.RGBA8888);
		
		float alpha = 0.05f;
		
		lightMap.setColor(alpha,alpha,alpha,1);
		
		Texture tempLight = new Texture("light.png");
		tempLight.getTextureData().prepare();
		light = tempLight.getTextureData().consumePixmap();
	}

	public void update(World world) {
		lightMap.fillRectangle(0, 0, lightMap.getWidth(), lightMap.getHeight());
		
		lightMap.drawPixmap(light,(int) (world.player.x+world.player.width/2-world.cam.x+world.cam.WIDTH/2-light.getWidth()/2),(int) (world.curFloor.tm.length-(world.player.y+world.player.height/2-world.cam.y+world.cam.HEIGHT/2-light.getHeight()/2)));
		lightMap.drawPixmap(light,(int) (world.curFloor.tm[0].length/2*Tile.TS-world.cam.x+world.cam.WIDTH/2-light.getWidth()/2),(int) (world.curFloor.tm.length-(world.curFloor.tm.length/2*Tile.TS-world.cam.y+world.cam.HEIGHT/2-light.getHeight()/2)));

		lightMapTex = new Texture(lightMap);
	}
	
	public void draw(SpriteBatch batch) {
		batch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);
		
		batch.draw(lightMapTex, 0, 0);
		
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
}
