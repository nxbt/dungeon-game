package com.dungeon.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.dungeon.game.Camera;
import com.dungeon.game.entity.*;
import com.dungeon.game.entity.hud.DescBox;
import com.dungeon.game.entity.hud.HealthBar;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.entity.hud.ManaBar;
import com.dungeon.game.entity.hud.Mouse;
import com.dungeon.game.entity.hud.StaminaBar;

public class World {
	public SpriteBatch hudBatch;
	
	public Camera cam;
	public Camera hudCam;
	
	public ArrayList<Dungeon> dungeons;
	
	public Dungeon curDungeon;
	public Floor curFloor;
	
	public ArrayList<Entity> entities;
	public ArrayList<Hud> hudEntities;
	
	public Player player;
	public Mouse mouse;
	
	public DescBox descBox;
	
	public World() {
		hudBatch = new SpriteBatch();
		
		dungeons = new ArrayList<Dungeon>();
		
		dungeons.add(new Dungeon());
		
		curDungeon = dungeons.get(0);
		curFloor = curDungeon.floors.get(0);
		
		cam = new Camera();
		hudCam = new Camera();
		
		entities = new ArrayList<Entity>();
		hudEntities = new ArrayList<Hud>();
		
		player = new Player(curFloor.tm[0].length/2*Tile.TS, curFloor.tm.length/2*Tile.TS);
		
		mouse = new Mouse(0, 0);
		descBox = new DescBox();
		
		entities = curFloor.entities;
		entities.add(0,player);
		entities.add(new Chest((curFloor.tm[0].length/2+1)*Tile.TS, curFloor.tm.length/2*Tile.TS));
		hudEntities.add(new HealthBar(100,20));
		hudEntities.add(new StaminaBar(220,20));
		hudEntities.add(new ManaBar(340,20));
	}
	
	public void update() {
		mouse.update(this);
		descBox.update(this);
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update(this);
			if(entities.get(i).killMe) {
				entities.remove(i);
				i--;
			}
		}
		
		for(int i = hudEntities.size()-1;i>=0;i--) {
			hudEntities.get(i).update(this);
		}
		
		cam.update(player.x+player.d_width/2, player.y+player.d_height/2, mouse.x, mouse.y, 1f);
	}
	
	public void draw(SpriteBatch batch) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		batch.setProjectionMatrix(cam.cam.combined);
		
		curFloor.draw(batch, this);
		
		for(int i = entities.size()-1; i >= 0; i--) {
			entities.get(i).draw(batch);
		}
		//temp code to generate blackness
		Texture darkness = new Texture("darkness.png");
		if (!darkness.getTextureData().isPrepared()) {
			darkness.getTextureData().prepare();
		}
		Pixmap tempMap = darkness.getTextureData().consumePixmap();
		Pixmap lightMap = new Pixmap(cam.WIDTH,cam.HEIGHT,Pixmap.Format.RGBA8888);
		lightMap.drawPixmap(tempMap, 0, 0, tempMap.getWidth(), tempMap.getHeight(), 0, 0, lightMap.getWidth(), lightMap.getHeight());
		Texture light = new Texture("light.png");
		if (!light.getTextureData().isPrepared()) {
			light.getTextureData().prepare();
		}
		Pixmap lightBall = light.getTextureData().consumePixmap();
		lightMap.drawPixmap(lightBall,(int) (player.x+player.width/2-cam.x+cam.WIDTH/2-lightBall.getWidth()/2),(int) (curFloor.tm.length-(player.y+player.height/2-cam.y+cam.HEIGHT/2-lightBall.getHeight()/2)));
		darkness = new Texture(lightMap);
		batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_COLOR);
//		temp.setPosition(player.x+player.width/2-temp.getWidth()/2, player.y+player.height/2-temp.getHeight()/2);
//		temp.draw(batch, 100);
		batch.draw(darkness, cam.x-cam.WIDTH/2, cam.y-cam.HEIGHT/2);
		
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		batch.setProjectionMatrix(hudCam.cam.combined);
		
		for(int i = hudEntities.size()-1;i>=0;i--) {
			hudEntities.get(i).draw(batch);
		}
		
		mouse.draw(batch);
		descBox.draw(batch);
		
		batch.end();
	}
}
