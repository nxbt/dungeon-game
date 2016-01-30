package com.dungeon.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.Camera;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.Enemy;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.Player;
import com.dungeon.game.entity.hud.DescBox;
import com.dungeon.game.entity.hud.HealthBar;
import com.dungeon.game.entity.hud.HelpButton;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.entity.hud.HudBackground;
import com.dungeon.game.entity.hud.HudSlot;
import com.dungeon.game.entity.hud.InvButton;
import com.dungeon.game.entity.hud.ManaBar;
import com.dungeon.game.entity.hud.MenuButton;
import com.dungeon.game.entity.hud.Mouse;
import com.dungeon.game.entity.hud.PortraitBackground;
import com.dungeon.game.entity.hud.StamBar;
import com.dungeon.game.pathing.AreaMap;

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
	
	public AreaMap areaMap;
	
	private BitmapFont fpsFont;
	
	boolean debug_hitbox;
	boolean debug_pathing;
	boolean debug_frames;
	boolean debug_sight;
	
//	public LightMap lightMap;
	
	public World() {
		hudBatch = new SpriteBatch();
		
		shapeRenderer = new ShapeRenderer();
		
		dungeons = new ArrayList<Dungeon>();
		
		dungeons.add(new Dungeon(this));
		
		curDungeon = dungeons.get(0);
		curFloor = curDungeon.floors.get(0);

		
		areaMap = curFloor.areaMap;
		
		cam = new Camera();
		hudCam = new Camera();
		
		entities = new ArrayList<Entity>();
		hudEntities = new ArrayList<Hud>();
		
		player = new Player(this, curFloor.tm[0].length/2*Tile.TS, curFloor.tm.length/2*Tile.TS);
		
		mouse = new Mouse(this, 0, 0);
		descBox = new DescBox(this);
		
		entities = curFloor.entities;
		entities.add(0,player);
		hudEntities.add(new MenuButton(this, 4, cam.HEIGHT-20));
		hudEntities.add(new HelpButton(this, 24, cam.HEIGHT-20));
		hudEntities.add(new InvButton(this, cam.WIDTH-56, 76));
		hudEntities.add(new PortraitBackground(this, cam.WIDTH-72, 4));
		hudEntities.add(new HealthBar(this,cam.WIDTH-36,76));
		hudEntities.add(new StamBar(this,cam.WIDTH-24,76));
		hudEntities.add(new ManaBar(this,cam.WIDTH-12,76));
		hudEntities.add(new HudSlot(this, cam.WIDTH-144, 40, player.inv.slot[30]));
		hudEntities.add(new HudSlot(this, cam.WIDTH-108, 40, player.inv.slot[31]));
		hudEntities.add(new HudSlot(this, cam.WIDTH-252, 4, player.inv.slot[0]));
		hudEntities.add(new HudSlot(this, cam.WIDTH-216, 4, player.inv.slot[1]));
		hudEntities.add(new HudSlot(this, cam.WIDTH-180, 4, player.inv.slot[2]));
		hudEntities.add(new HudSlot(this, cam.WIDTH-144, 4, player.inv.slot[3]));
		hudEntities.add(new HudSlot(this, cam.WIDTH-108, 4, player.inv.slot[4]));
		hudEntities.add(new HudBackground(this));
		
		fpsFont = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		fpsFont.setColor(Color.RED);
		
		debug_hitbox = false;
		debug_pathing = false;
		debug_frames = false; 
		debug_sight = false;
		
//		lightMap = new LightMap(cam.WIDTH,cam.HEIGHT);
	}
	
	public void update() {
		mouse.update();
		descBox.update();
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).killMe) {
				entities.get(i).dead();
				entities.remove(i);
				i--;
			}
		}
		
		for(int i = hudEntities.size()-1;i>=0;i--) {
			hudEntities.get(i).update();
		}
		
		cam.update(player.x, player.y, mouse.x, mouse.y, 1f);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.F9)) debug_frames = !debug_frames;
		if(Gdx.input.isKeyJustPressed(Input.Keys.F8)) debug_pathing = !debug_pathing;
		if(Gdx.input.isKeyJustPressed(Input.Keys.F7)) debug_hitbox = !debug_hitbox;
		if(Gdx.input.isKeyJustPressed(Input.Keys.F10)) debug_sight = !debug_sight;
		
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
		batch.end();
		
		if(debug_pathing||debug_sight||debug_hitbox) {
			shapeRenderer.begin(ShapeType.Line);
			
			shapeRenderer.setProjectionMatrix(cam.cam.combined);
			
			for(Entity e: entities){
				if(debug_hitbox) {
					if(e.solid) shapeRenderer.setColor(Color.RED);
					else shapeRenderer.setColor(Color.GREEN);
					
					shapeRenderer.polygon(e.getHitbox().getVertices());	
				}
				
				if(debug_pathing && e instanceof Enemy && ((Enemy)e).moveTo!=null&&((Enemy)e).path!=null){
					shapeRenderer.setColor(Color.BLUE);
					shapeRenderer.rect(((Enemy)e).moveTo[0]*Tile.TS, ((Enemy)e).moveTo[1]*Tile.TS, Tile.TS, Tile.TS);
					shapeRenderer.line(e.x, e.y, ((Enemy)e).moveTo[0]*Tile.TS+Tile.TS/2, ((Enemy)e).moveTo[1]*Tile.TS+Tile.TS/2);
					int[] prePoint = new int[]{0,0};
					shapeRenderer.setColor(Color.YELLOW);
					for(int[] point:((Enemy)e).path){
						if(((Enemy)e).path.indexOf(point)>0){
							shapeRenderer.line(prePoint[0]*Tile.TS+Tile.TS/2, prePoint[1]*Tile.TS+Tile.TS/2,point[0]*Tile.TS+Tile.TS/2,point[1]*Tile.TS+Tile.TS/2);
						}
						prePoint = point;
					}
				}
				
				if(debug_sight) {
					shapeRenderer.setColor(Color.PURPLE);
					if(e instanceof Player) shapeRenderer.polygon(((Character)e).visPolygon.getVertices());
				}
			}
			
			if(debug_sight) {
				shapeRenderer.setColor(Color.PURPLE);
				for(int[] corner: curFloor.corners){
					shapeRenderer.rect(corner[0]-2,corner[1]-2,4,4);
				}	
			}
			
			shapeRenderer.end();
		}
		
		batch.begin();
		
		batch.setProjectionMatrix(hudCam.cam.combined);
		
//		lightMap.draw(batch);
		
		for(int i = hudEntities.size()-1;i>=0;i--) {
			hudEntities.get(i).draw(batch);
		}
		
		mouse.draw(batch);
		descBox.draw(batch);
		
		if(debug_frames) fpsFont.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 60f, cam.HEIGHT-8f);
		
		batch.end();
	}
}
