package com.dungeon.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.dungeon.game.Camera;
import com.dungeon.game.entity.*;
import com.dungeon.game.entity.hud.DescBox;
import com.dungeon.game.entity.hud.HealthBar;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.entity.hud.ManaBar;
import com.dungeon.game.entity.hud.Mouse;
import com.dungeon.game.entity.hud.StaminaBar;
import com.dungeon.game.light.LightMap;

public class World {
	public SpriteBatch hudBatch;
	public ShapeRenderer shapeRenderer;
	
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
	
//	public LightMap lightMap;
	
	public World() {
		hudBatch = new SpriteBatch();
		
		shapeRenderer = new ShapeRenderer();
		
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
		
//		lightMap = new LightMap(cam.WIDTH,cam.HEIGHT);
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
		
		cam.update(player.x, player.y, mouse.x, mouse.y, 1f);
		
//		lightMap.update(this);
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
		
		batch.setProjectionMatrix(hudCam.cam.combined);
		
//		lightMap.draw(batch);
		
		for(int i = hudEntities.size()-1;i>=0;i--) {
			hudEntities.get(i).draw(batch);
		}
		
		mouse.draw(batch);
		descBox.draw(batch);
		
		batch.end();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.setProjectionMatrix(cam.cam.combined);
		for(Entity e: entities){
				shapeRenderer.polygon(e.getHitbox().getVertices());	
		}
		shapeRenderer.end();
	}
}
