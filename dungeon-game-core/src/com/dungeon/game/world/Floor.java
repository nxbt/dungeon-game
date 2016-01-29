package com.dungeon.game.world;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.Goon;
import com.dungeon.game.generator.Biospheres;
import com.dungeon.game.generator.Castle;
import com.dungeon.game.generator.Generation;
import com.dungeon.game.generator.Rooms;
import com.dungeon.game.pathing.Area;
import com.dungeon.game.pathing.AreaMap;

public class Floor {
	private static final String DEFAULT = "Tilemap.png";
	
	private TextureRegion[] spritesheet;
	
	public Tile[][] tm;
	
	public ArrayList<int[]> corners;
	
	public ArrayList<float[]> edges;
	
	public ArrayList<Entity> entities;
	
	private int width;
	private int height;

	public AreaMap areaMap;
	
	protected World world;
	
	public Floor(World world, int width, int height) {
		this.world = world;
		this.width = width;
		this.height = height;
		
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
		Generation gen = new Rooms(world, width, height);
		int[][] map = gen.getMap();
		entities = gen.getEntities();
		
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
				
				if(val != 15 && val !=0 && val !=5 && val!=6 && val!=9 && val!=10) corners.add(new int[]{k*Tile.TS,i*Tile.TS});
					
			}
		}
		
		edges = new ArrayList<float[]>(); //{startX,startY,endX,endy};
		for(int i = 0; i < tm.length; i++){
			for(int k = 0; k < tm[i].length; k++){
				if(tm[i][k].data == 1){
					edges.add(new float[]{k*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,i*Tile.TS});
					edges.add(new float[]{k*Tile.TS,i*Tile.TS,k*Tile.TS,(i+1)*Tile.TS});
					edges.add(new float[]{(k+1)*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS});
					edges.add(new float[]{k*Tile.TS,(i+1)*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS});
				}
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
