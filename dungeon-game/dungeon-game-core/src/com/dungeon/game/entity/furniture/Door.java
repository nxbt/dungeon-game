package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Static;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

//TODO: rework cuz shit
public class Door extends Static {
	public boolean open;
	public int dir; 
	public Door(World world, float x, float y, int dir) {
		super(world, x, y, 32, 4, "door.png");
		
		name = "door";
		solid = false;
		
		hitbox = new Polygon(new float[]{0,0,0,0,0,0,0,0});
		
		origin_x = 0;
		origin_y = 0;
		
		open = false;
		this.dir = dir;
		if(dir == 0){
			d_offx = 0;
			d_offy = 14;
		}else {
			d_offx = 18;
			d_offy = 0;
		}
	}
	
	private void open(){
		open = !open;
		if(dir==0){
			if(open){

				d_offx = 4;
				d_offy = 14;
			}else{
				d_offx = 0;
				d_offy = 14;
			}
		}else {
			if(open){

				d_offx = 14;
				d_offy = 0;
			}else{
				d_offx = 18;
				d_offy = 0;
			}
		}
	}

	@Override
	public void calc() {
		boolean canOpen = true;
		if(world.mouse.rb_pressed&&world.mouse.x > x-world.cam.x+world.cam.width/2 && world.mouse.x < x+Item.SIZE-world.cam.x+world.cam.width/2 && world.mouse.y > y-world.cam.y+world.cam.height/2 && world.mouse.y < y+Item.SIZE-world.cam.y+world.cam.height/2){
			if(Math.sqrt(Math.pow((x+d_width/2) - (world.player.x + world.player.d_width/2), 2) + Math.pow((y+d_height/2) - (world.player.y + world.player.d_height/2), 2)) <= world.player.REACH){
				for(int i = 0; i< world.curFloor.tm.length;i++){
					for(int k = 0; k <world.curFloor.tm[i].length;k++){
						if(world.curFloor.tm[k][i].data==1){
							float[] verticies = new float[]{i*Tile.TS,k*Tile.TS,(i+1)*Tile.TS,k*Tile.TS,(i+1)*Tile.TS,(k+1)*Tile.TS,(i)*Tile.TS,(k+1)*Tile.TS};
							if(Intersector.intersectSegmentPolygon(new Vector2(x+d_width/2,y+d_height/2), new Vector2(world.player.x + world.player.d_width/2,world.player.y + world.player.d_height/2), new Polygon(verticies))){
								canOpen = false;
							}
						}
					}
				}
				if(canOpen){
					open();
				}
			}
		}
//		solid = !open;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if(dir ==0){
			if(!open)batch.draw(sprite, x+d_offx, y+d_offy, d_width, d_height);
			else batch.draw(sprite, x+d_offx, y+d_offy, 0, 0, d_width, d_height, 1, 1, 90, 0, 0, sprite.getWidth(), sprite.getHeight(), flipX, flipY);
		}else {
			if(!open)batch.draw(sprite, x+d_offx, y+d_offy, 0, 0, d_width, d_height, 1, 1, 90, 0, 0, sprite.getWidth(), sprite.getHeight(), flipX, flipY);
			else batch.draw(sprite, x+d_offx, y+d_offy, d_width, d_height);
		}
	}

	public void post() {}
}
