package com.dungeon.game.world;

import java.util.ArrayList;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.dungeon.game.Camera;
import com.dungeon.game.DungeonGame;
import com.dungeon.game.KeyListener;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.character.Player;
import com.dungeon.game.entity.character.enemy.Enemy;
import com.dungeon.game.entity.character.enemy.Goon;
import com.dungeon.game.entity.character.enemy.Spider;
import com.dungeon.game.entity.character.friend.Friend;
import com.dungeon.game.entity.hud.DescBox;
import com.dungeon.game.entity.hud.EffectHudBackground;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.entity.hud.HudBackground;
import com.dungeon.game.entity.hud.Mouse;
import com.dungeon.game.entity.particle.Blood;
import com.dungeon.game.entity.particle.BodyChunk;
import com.dungeon.game.entity.particle.Ember;
import com.dungeon.game.entity.particle.Poof;
import com.dungeon.game.pathing.Node;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

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
	
	private BitmapFont fpsFont;
	
	public boolean debug_hitbox;
	public boolean debug_pathing;
	public boolean debug_frames;
	public boolean debug_sight;
	public boolean debug_light;
	public boolean debug_pause;
	public boolean debug_freeCam;
	
	private com.badlogic.gdx.physics.box2d.World emptyWorld;
	private PointLight emptyWorldLight;
	
	public RayHandler rayHandler;
	
	private ArrayList<Entity> drawEnts;
	
	private Box2DDebugRenderer debugRenderer;
	
	public World(boolean generate) {
		Poof.init();
		Blood.init();
		Ember.init();
		BodyChunk.init();
		com.dungeon.game.textures.entity.particle.BodyChunk.init();
		RayHandler.setGammaCorrection(false);
		rayHandler = new RayHandler(null);
		rayHandler.setBlurNum(10);
		rayHandler.setAmbientLight(new Color(0,0,0,0));
		RayHandler.useDiffuseLight(true);
		Filter f = new Filter();
		f.maskBits = 0x0001;
		Light.setGlobalContactFilter(f);
		hudBatch = new SpriteBatch();
		cam = new Camera(this);
		hudCam = new Camera(this);
		player = new Player(this, 0, 0);
		player.x = 50*Tile.TS-Tile.TS/2;
		player.y = 50*Tile.TS-Tile.TS/2;
		if(generate){
			shapeRenderer = new ShapeRenderer();
			
			dungeons = new ArrayList<Dungeon>();
			
			dungeons.add(new Dungeon(this));
			curDungeon = dungeons.get(0);
			curFloor = curDungeon.floors.get(0);
			
			entities = new ArrayList<Entity>();
			hudEntities = new ArrayList<Hud>();
			
			mouse = new Mouse(this, 0, 0);
			descBox = new DescBox(this);
			
			entities = curFloor.entities;
			
			entities.add(0,player);
			
			hudEntities.add(new HudBackground(this));
			
			hudEntities.add(new EffectHudBackground(this, cam.width-44, cam.height-44));
			
			fpsFont = new BitmapFont(Gdx.files.internal("main_text.fnt"));
			fpsFont.setColor(Color.RED);
			
			emptyWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0,0), true);
			emptyWorldLight = new PointLight(rayHandler, 10, Color.WHITE, Integer.MAX_VALUE, 0, 0);
			emptyWorldLight.remove(false);
			
			debug_hitbox = false;
			debug_pathing = false;
			debug_frames = false; 
			debug_sight = false;
			debug_light = false;
			debug_pause = false;
			debug_freeCam = false;
			rayHandler.setWorld(curFloor.box2dWorld);
			
			for(Entity e: entities){
				if(e.light != null)e.light.load();
			}
			
			drawEnts = new ArrayList<Entity>();
		}
		debugRenderer = new Box2DDebugRenderer();
	}
	
	public void update() {
		KeyListener.update();
		
		descBox.text = "";
		
		cam.update();
		mouse.update();
		descBox.update();
		if(debug_pause){
			player.update();
		}else{
			for(int i = 0; i < entities.size(); i++) {
				if(!entities.get(i).bodyMade)entities.get(i).getBody(curFloor.box2dWorld);
				entities.get(i).update();
			}
		}

		curFloor.box2dWorld.step(1, 5, 5);
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).goToBodyPostion();
			if(entities.get(i).killMe) {
				if(entities.get(i) instanceof Character)((Character)entities.get(i)).endEffects();
				entities.get(i).dead();
				if(entities.get(i).body != null)curFloor.box2dWorld.destroyBody(entities.get(i).body);
				entities.remove(i);
				i--;
			}
		}

		for(int i = hudEntities.size()-1;i>=0;i--) {
			hudEntities.get(i).update();
		}
		
		if(KeyListener.keysJustPressed[Input.Keys.F10]) debug_sight = !debug_sight;
		if(KeyListener.keysJustPressed[Input.Keys.F9]) debug_frames = !debug_frames;
		if(KeyListener.keysJustPressed[Input.Keys.F8]) debug_pathing = !debug_pathing;
		if(KeyListener.keysJustPressed[Input.Keys.F7]) debug_hitbox = !debug_hitbox;
		if(KeyListener.keysJustPressed[Input.Keys.F6]) debug_pause = !debug_pause;
		if(KeyListener.keysJustPressed[Input.Keys.F5]) debug_freeCam = !debug_freeCam;
		
		if(KeyListener.keysJustPressed[Input.Keys.F11]) {
			debug_light = !debug_light;
			if(debug_light) {
				rayHandler.setWorld(emptyWorld);
				emptyWorldLight.add(rayHandler);
			}
			else {
				rayHandler.setWorld(curFloor.box2dWorld);
				emptyWorldLight.remove(false);
			}
		}
	}
	
	public void draw(SpriteBatch batch) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		batch.setProjectionMatrix(cam.cam.combined);
		
		curFloor.draw(batch, this);
		
		drawEnts.clear();
		
		for(int i = 0; i < entities.size(); i++) drawEnts.add(entities.get(i));

		drawEnts.sort(new Comparator<Entity>(){

			@Override
			public int compare(Entity o1, Entity o2) {
				return (""+o2.layer).compareTo(""+o1.layer);
			}
			
		});
		
		
		for(int i = drawEnts.size()-1; i >= 0; i--) {
			drawEnts.get(i).draw(batch);
		}
		if(drawEnts.size() > 0)drawEnts.get(drawEnts.size()-1).draw(batch);//have to draw player twice when using lights
		rayHandler.setCombinedMatrix(cam.lightCam);
		rayHandler.updateAndRender();
		if(debug_hitbox)debugRenderer.render(curFloor.box2dWorld, cam.lightCam.combined);
		
		batch.end();
		
		if(debug_pathing||debug_sight||debug_hitbox) {
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setAutoShapeType(true);
			
			shapeRenderer.setProjectionMatrix(cam.cam.combined);
			if(debug_pathing){
				shapeRenderer.set(ShapeType.Line);
				for(Node n :curFloor.pathfinder.graph.graphLevels[0].nodes){
					shapeRenderer.setColor(Color.PURPLE);
					shapeRenderer.circle(n.x*Tile.TS, n.y*Tile.TS, 1);
//					shapeRenderer.polygon(n.getHitBox().getVertices());
					shapeRenderer.setColor(Color.BLACK);
					for(Node n2: n.outNodes){
						if(n2.outNodes.contains(n)){
							shapeRenderer.line(n.x*Tile.TS, n.y*Tile.TS, n2.x*Tile.TS, n2.y*Tile.TS);
						}
					}
				}
				
			}
			
			for(Entity e: entities){
				if(debug_hitbox && e.hitbox != null) {
					if(e.solid) shapeRenderer.setColor(Color.RED);
					else shapeRenderer.setColor(Color.GREEN);
					
//					shapeRenderer.polygon(e.getVisbox().getVertices());

					shapeRenderer.set(ShapeType.Line);
				}
				
				if(debug_pathing && e instanceof Character && ((Character)e).moveTo!=null&&((Character)e).path!=null){
					shapeRenderer.setColor(Color.BLUE);
					shapeRenderer.rect(((Character)e).moveTo[0]*Tile.TS, ((Character)e).moveTo[1]*Tile.TS, Tile.TS, Tile.TS);
					shapeRenderer.line(e.x, e.y, ((Character)e).moveTo[0]*Tile.TS+Tile.TS/2, ((Character)e).moveTo[1]*Tile.TS+Tile.TS/2);
					int[] prePoint = new int[]{0,0};
					shapeRenderer.setColor(Color.YELLOW);
					for(int[] point:((Character)e).path){
						if(((Character)e).path.indexOf(point)>0){
							shapeRenderer.line(prePoint[0]*Tile.TS+Tile.TS/2, prePoint[1]*Tile.TS+Tile.TS/2,point[0]*Tile.TS+Tile.TS/2,point[1]*Tile.TS+Tile.TS/2);
						}
						prePoint = point;
					}
					if(e instanceof Goon && ((Goon)e).lineOfCover != null){
						shapeRenderer.setColor(Color.CORAL);
						shapeRenderer.line(((Goon)e).lineOfCover[0], ((Goon)e).lineOfCover[1], ((Goon)e).lineOfCover[2], ((Goon)e).lineOfCover[3]);
					}
					
					if(e instanceof Spider && ((Spider)e).lineOfCover != null){
						shapeRenderer.setColor(Color.CORAL);
						shapeRenderer.line(((Spider)e).lineOfCover[0], ((Spider)e).lineOfCover[1], ((Spider)e).lineOfCover[2], ((Spider)e).lineOfCover[3]);
					}
				}
				
				if(debug_sight) {
					Gdx.gl.glEnable(GL20.GL_BLEND);
					Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					if(e instanceof Character){
						if(e instanceof Player)shapeRenderer.setColor(1,0,1,1);
						if(e instanceof Friend)shapeRenderer.setColor(0,1,1,1);
						if(e instanceof Enemy)shapeRenderer.setColor(1,1,0,1);
						shapeRenderer.polygon(((Character) e).visPolygon.getVertices());
						float[] verts = ((Character) e).visPolygon.getVertices();
						shapeRenderer.set(ShapeType.Filled);
						shapeRenderer.setColor(Color.RED);
						for(int i = 1; i < verts.length; i+=2){
							shapeRenderer.circle(verts[i-1], verts[i], 1);
						}
						shapeRenderer.set(ShapeType.Line);
					}
					Gdx.gl.glBlendFunc(GL20.GL_ZERO, GL20.GL_ONE);
					Gdx.gl.glDisable(GL20.GL_BLEND);
				}
			}
			
			shapeRenderer.end();
		}
		
		batch.begin();
		
		batch.setProjectionMatrix(hudCam.cam.combined);
		
		for(int i = hudEntities.size()-1;i>=0;i--) {
			hudEntities.get(i).draw(batch);
		}
		
		mouse.draw(batch);
		descBox.draw(batch);
		
		if(debug_frames){
			fpsFont.setColor(Color.RED);
			fpsFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 60f, cam.height-8f);
			fpsFont.setColor(Color.GREEN);
			fpsFont.draw(batch, "X: " + (int)(mouse.x+cam.x-cam.width/2)/32, 60f, cam.height-20f);
			fpsFont.draw(batch, "Y: " + (int)(mouse.y+cam.y-cam.height/2)/32, 60f, cam.height-32f);
		}
		
		batch.end();
	}
	
	public void changeFloor(int floor, int destX, int destY, float x, float y){
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			if(e.light != null)e.light.unload();
			if(e instanceof Character){
				for(int k = 0; k < ((Character)e).equipItems.length; k++){
					if(((Character)e).equipItems[k] != null)((Character)e).equipItems[k].unequip();
					((Character)e).equipItems[k] = null;
				}
			}
		}
		curFloor.box2dWorld.destroyBody(player.body);
		player.bodyMade = false;
		entities.remove(player);
		if(curDungeon.floors.size() <= floor) {
			//generate new floor
			if(floor == 0 || (dungeons.indexOf(curDungeon) == 0 && floor == 1))curDungeon.floors.add(new Floor(this, "villageCastle", 50,50,destX,destY,(int)(x/Tile.TS),(int)(y/Tile.TS)));
			else curDungeon.floors.add(new Floor(this, "rooms", 50,50,destX,destY,(int)(x/Tile.TS),(int)(y/Tile.TS)));
		}
		curFloor = curDungeon.floors.get(floor);
		entities = curFloor.entities;
		entities.add(0,player);
		player.x = destX*Tile.TS-Tile.TS/2;
		player.y = destY*Tile.TS-Tile.TS/2;
		for(Entity e: entities){
			if(e.light != null)e.light.load();
		}
		rayHandler.setWorld(curFloor.box2dWorld);
		if(debug_light)rayHandler.setWorld(emptyWorld);
		
		DungeonGame.lag = 0;
		
		DungeonGame.t = System.nanoTime();
	}
}
