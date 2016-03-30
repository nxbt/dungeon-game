package com.dungeon.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.generator.Generation;
import com.dungeon.game.generator.Rooms;
import com.dungeon.game.generator.VillageRooms;
import com.dungeon.game.pathing.Area;
import com.dungeon.game.pathing.AreaMap;

public class Floor {
	private static final String DEFAULT = "tilemap.png";
	
	private TextureRegion[] spritesheet;
	
	public Tile[][] tm;
	
	public ArrayList<int[]> corners;
	
	public ArrayList<float[]> edges;
	
	public ArrayList<Entity> entities;

	public AreaMap areaMap;
	
	protected World world;
	
	public Floor(World world, String type, int width, int height, int centerX, int centerY, int upTrapX, int upTrapY) {
		this.world = world;
		
		entities = new ArrayList<Entity>();
		
		tm = new Tile[height][width];
		
		Texture tempsheet = new Texture(DEFAULT);
		
		int sheetWidth = tempsheet.getWidth()/Tile.TS;
		int sheetHeight = tempsheet.getHeight()/Tile.TS;
		
		spritesheet = new TextureRegion[sheetWidth * sheetHeight];
		
		for(int i = 0; i < sheetHeight; i++) {
			for(int k = 0; k < sheetWidth; k++) {
				spritesheet[i*sheetWidth+k] = new TextureRegion(new Texture(DEFAULT),k*Tile.TS,i*Tile.TS,Tile.TS,Tile.TS);
			}
		}
		Generation gen;
		if(type.equals("rooms"))gen = new Rooms(world, width, height,centerX,centerY, upTrapX, upTrapY);
		else if(type.equals("village_rooms"))gen = new VillageRooms(world, width, height,centerX,centerY, upTrapX, upTrapY);
		else gen = new Rooms(world, width, height,centerX,centerY, upTrapX, upTrapY);

		int[][] map = gen.getMap();
		entities = gen.getEntities();
//		entities.add(new Stair(world, (centerX+1)*Tile.TS, centerY*Tile.TS, true, 15+(int) (Math.random()*10), 15+((int) Math.random()*10)));
		
		fixBleeding(spritesheet);

		for(int i = 0;i<tm.length;i++){
			for(int k = 0;k<tm[i].length;k++){
				tm[i][k] = new Tile(spritesheet,map[i][k]);
			}
		}
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
		
		gen.generateAreas();
		areaMap = new AreaMap(tm);
		for(Area area: gen.areas){
			areaMap.addArea(area);
		}
		areaMap.prepAreas();
	}
	
	public void update() {
		
	}
	
	public void draw(SpriteBatch batch, World world) {
		int startHeight = (int) (world.cam.y-world.cam.HEIGHT/2)/Tile.TS;
		int endHeight = (int)(world.cam.y+world.cam.HEIGHT/2)/Tile.TS+1;
		int startWidth = (int) (world.cam.x-world.cam.WIDTH/2)/Tile.TS;
		int endWidth = (int)(world.cam.x+world.cam.WIDTH/2)/Tile.TS+1;
		
		startHeight = Math.max(startHeight,0);
		endHeight = Math.min(endHeight,tm.length);
		startWidth = Math.max(startWidth,0);
		endWidth = Math.min(endWidth,tm[0].length);
		
		for(int i = startHeight; i < endHeight; i++){
			for(int k = startWidth; k < endWidth; k++){
				batch.draw(tm[i][k].texture, k*Tile.TS, i*Tile.TS);
			}
		}
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
