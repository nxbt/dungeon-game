package com.dungeon.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.ai.pfa.HierarchicalPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.generator.Generation;
import com.dungeon.game.generator.TutorialGenerator;
import com.dungeon.game.generator.rooms.Castle;
import com.dungeon.game.generator.rooms.Rooms;
import com.dungeon.game.generator.rooms.VillageCastle;
import com.dungeon.game.generator.rooms.VillageRooms;
import com.dungeon.game.pathing.Heuristic;
import com.dungeon.game.pathing.Node;
import com.dungeon.game.pathing.Path;
import com.dungeon.game.pathing.newpathing.Graph;
import com.dungeon.game.pathing.newpathing.Pathfinder;
import com.dungeon.game.textures.tiles.Brick;
import com.dungeon.game.textures.tiles.Dirt;
import com.dungeon.game.textures.tiles.Marble;
import com.dungeon.game.textures.tiles.Stone;
import com.dungeon.game.textures.tiles.WoodPlank;
import com.dungeon.game.utilities.Spritesheet;

public class Floor {
	private static final String DEFAULT = "tilemap.png";
	
	public static final TileMap tileMap1 = new TileMap(DEFAULT);
	
	public Tile[][] tm;
	
	public Texture tmTexture;
	
	public ArrayList<int[]> corners;
	
	public ArrayList<float[]> edges;
	
	public ArrayList<Entity> entities;
	
	protected World world;
	
	public com.badlogic.gdx.physics.box2d.World box2dWorld;
	
	public int seed;
	
	public Pathfinder pathfinder;
	
	public Floor(World world, String type, int width, int height, int centerX, int centerY, int upTrapX, int upTrapY) {
		this.world = world;
		
		seed =(int) (Math.random()*100000);
		
		entities = new ArrayList<Entity>();
		
		tm = new Tile[height][width];
		
		Generation gen;
		if(type.equals("rooms"))gen = new Rooms(world, width, height,centerX,centerY, upTrapX, upTrapY, seed, new Object[0]);
		else if(type.equals("villageRooms"))gen = new VillageRooms(world, width, height,centerX,centerY, upTrapX, upTrapY, seed, new Object[0]);
		else if(type.equals("castle"))gen = new Castle(world, width, height,centerX,centerY, upTrapX, upTrapY, seed, new Object[0]);
		else if(type.equals("villageCastle"))gen = new VillageCastle(world, width, height,centerX,centerY, upTrapX, upTrapY, seed, new Object[0]);
		else if(type.equals("tutorial"))gen = new TutorialGenerator(world, seed);
		else gen = new Rooms(world, width, height,centerX,centerY, upTrapX, upTrapY, seed, new Object[0]);
		
		gen.generateSprites();

		Tile[][] map = gen.map;
		
		entities = gen.getEntities();
		
		//generates textures
		for(int i = 0;i<tm.length;i++){
			for(int k = 0;k<tm[i].length;k++){
				//temp code to test 4 ways
				if(map[i][k].id == 0){
					Texture tex = new WoodPlank(seed, k, i, k != 0 && (map[i][k-1].id != 0), k != map[i].length-1 && (map[i][k+1].id != 0), i != 0 && (map[i-1][k].id != 0), i != map.length-1 && (map[i+1][k].id != 0)).texture;
					for(int j = 0; j < map[i][k].textures.length; j++)map[i][k].textures[j] = tex;
				}else if(map[i][k].id == 2){
					Texture tex = new Dirt(seed, k, i).texture;
					for(int j = 0; j < map[i][k].textures.length; j++)map[i][k].textures[j] = tex;
				}else if(map[i][k].id == 3){
					Texture tex = new Marble(seed, k, i).texture;
					for(int j = 0; j < map[i][k].textures.length; j++)map[i][k].textures[j] = tex;
				}else if(map[i][k].id == 4){
					Texture tex = new Stone(seed, k, i).texture;
					for(int j = 0; j < map[i][k].textures.length; j++)map[i][k].textures[j] = tex;
				}else if(map[i][k].id == 10 || map[i][k].id == 11 || map[i][k].id == 12 || map[i][k].id == 13 || map[i][k].id == 14){
					int sides = 0;
					int corners = 0;
					
					if(k != 0 && !Tile.isSolid(map[i][k-1])) sides += 1; //left
					if(i != 0 && !Tile.isSolid(map[i-1][k])) sides += 2; //bottom
					if(k != map[i].length-1 && !Tile.isSolid(map[i][k+1])) sides += 4; //right
					if(i != map.length-1 && !Tile.isSolid(map[i+1][k])) sides += 8; //upper
					
					if(i != 0 && k != 0 && !Tile.isSolid(map[i-1][k-1])) corners += 1; //bottom left
					if(i != map.length-1 && k != 0 && !Tile.isSolid(map[i+1][k-1])) corners += 2; //upper left
					if(i != 0 && k != map[i].length-1 && !Tile.isSolid(map[i-1][k+1])) corners += 4; //bottom right
					if(i != map.length-1 && k != map[i].length-1 && !Tile.isSolid(map[i+1][k+1])) corners += 8; //upper right
					Texture tex = new Brick(seed, k, i, sides, corners).texture;
					for(int j = 0; j < map[i][k].textures.length; j++)map[i][k].textures[j] = tex;
				}
				tm[i][k] = map[i][k];
			}
		}
		
		Pixmap tmPixmap = new Pixmap(width*Tile.TS, height*Tile.TS, Pixmap.Format.RGBA8888);
		
		for(int i = 0;i<tm.length;i++){
			for(int k = 0;k<tm[i].length;k++) {
				if(!tm[i][k].textures[0].getTextureData().isPrepared()) tm[i][k].textures[0].getTextureData().prepare();
				Pixmap temp = tm[i][k].textures[0].getTextureData().consumePixmap();
				temp = Spritesheet.rotatePixmap(temp, 1);
				temp = Spritesheet.flipPixmap(temp);
				temp = Spritesheet.rotatePixmap(temp, 3);
				tmPixmap.drawPixmap(temp, k*Tile.TS, (i)*Tile.TS);
				temp.dispose();
			}
		}
		
		tmTexture = new Texture(tmPixmap);
		
		corners = new ArrayList<int[]>();
		for(int i = 1; i < tm.length;i++){
			for(int k = 1; k < tm[i].length; k++){
				int val = 0;
				if(tm[i][k].data==1)val+=1;
				if(tm[i-1][k-1].data==1)val+=2;
				if(tm[i][k-1].data==1)val+=4;
				if(tm[i-1][k].data==1)val+=8;
				
				if(val != 15 && val !=0 && val !=5 && val!=6 && val!=9 && val!=10) {
					if(val == 1 || val == 2 || val == 4 || val == 8) corners.add(new int[]{k*Tile.TS,i*Tile.TS,0});
					else corners.add(new int[]{k*Tile.TS,i*Tile.TS,1});
				}
					
			}
		}
		
		edges = new ArrayList<float[]>(); //{startX,startY,endX,endy};
		for(int i = 0; i < tm.length; i++){
			for(int k = 0; k < tm[i].length; k++){
				if(tm[i][k].data == 1){
					if(i > 0 && tm[i-1][k].data != 1) edges.add(new float[]{k*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,i*Tile.TS});
					if(k > 0 && tm[i][k-1].data != 1) edges.add(new float[]{k*Tile.TS,i*Tile.TS,k*Tile.TS,(i+1)*Tile.TS});
					if(k < tm[i].length-1 && tm[i][k+1].data != 1) edges.add(new float[]{(k+1)*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS});
					if(i < tm.length-1 && tm[i+1][k].data != 1)edges.add(new float[]{k*Tile.TS,(i+1)*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS});
				}
			}
		}
		
		for(int i = 0; i < edges.size(); i++) {
			float[] edge1 = edges.get(i);
			for(int k = 0; k < edges.size(); k++) {
				float[] edge2 = edges.get(k);
				if(i != k) {
					if(edge1[0] == edge2[2] && edge1[1] == edge2[3] && ((edge1[0] == edge1[2] && edge2[0] == edge2[2]) || (edge1[1] == edge1[3] && edge2[1] == edge2[3]))) {
						edge2[2] = edge1[2];
						edge2[3] = edge1[3];
						
						if(edges.contains(edge1)) {
							edges.remove(edge1);
							
							i--;
							if(i < k) k--;
						}
					}
					else if(edge2[0] == edge1[2] && edge2[1] == edge1[3] && ((edge2[0] == edge2[2] && edge1[0] == edge1[2]) || (edge2[1] == edge2[3] && edge1[1] == edge1[3]))) {
						edge1[2] = edge2[2];
						edge1[3] = edge2[3];
						
						if(edges.contains(edge2)) {
							edges.remove(edge2);
							
							if(k < i) i--;
							k--;
						}
					}
				}
			}
		}
		
		for(float[] edge: edges) {
			if(edge[0] == edge[2]) {
				edge[1]-= 0.001f;
				edge[3]+= 0.001f;
			}
			else {
				edge[0] -= 0.001f;
				edge[2] += 0.001f;
			}
		}
		Graph graph = gen.getPathGraph();
		pathfinder = new Pathfinder(graph);
		
		box2dWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0,0), true);
		for(int i = 0; i <tm.length; i++){
			for(int k = 0; k <tm.length; k++){
				if(tm[i][k].data == 1){
					// Create our body definition
					BodyDef groundBodyDef =new BodyDef();  
					// Set its world position
					groundBodyDef.position.set(new Vector2(k*Tile.TS+Tile.TS/2, i*Tile.TS+Tile.TS/2));  

					// Create a body from the defintion and add it to the world
					Body groundBody = box2dWorld.createBody(groundBodyDef);  

					// Create a polygon shape
					PolygonShape groundBox = new PolygonShape();  
					// Set the polygon shape as a box which is twice the size of our view port and 20 high
					// (setAsBox takes half-width and half-height as arguments)
					groundBox.setAsBox(Tile.TS/2, Tile.TS/2);
					// Create a fixture from our polygon shape and add it to our ground body  
					groundBody.createFixture(groundBox, 0.0f); 
					// Clean up after ourselves
					groundBox.dispose();
				}
				
			}
		}
		
	}
	
	public void update() {
		
	}
	
	public void draw(SpriteBatch batch, World world) {
		int startHeight = (int) (world.cam.y-world.cam.height/2);
		int endHeight = (int) (world.cam.y+world.cam.height/2);
		int startWidth = (int) (world.cam.x-world.cam.width/2);
		int endWidth = (int) (world.cam.x+world.cam.width/2);
		
		startHeight = Math.max(startHeight,0);
		endHeight = Math.min(endHeight,tm.length*Tile.TS)+1;
		startWidth = Math.max(startWidth,0);
		endWidth = Math.min(endWidth,tm[0].length*Tile.TS)+1;
		
		batch.draw(tmTexture, startWidth, startHeight, endWidth-startWidth, endHeight-startHeight, startWidth, startHeight, endWidth-startWidth, endHeight-startHeight, false, true);
	}

	public static void fixBleeding(TextureRegion[] region) {
	        for (TextureRegion texture : region) {
	            fixBleeding(texture);
	        }
	}

	public static void fixBleeding(TextureRegion region) {
	    float fix = 0.01f;

	    float x = region.getRegionX();
	    float y = region.getRegionY();
	    float width = region.getRegionWidth();
	    float height = region.getRegionHeight();
	    float invTexWidth = 1f / region.getTexture().getWidth();
	    float invTexHeight = 1f / region.getTexture().getHeight();
	    region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight); // Trims
	                                                                                                                                                // region
	}
}
