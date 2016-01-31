package com.dungeon.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.item.Inventory;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

//abstract class for dynamic entities, or entities that move and respond to physics.
public abstract class Dynamic extends Entity {
	public float fric;
	public float mvel;
	
	public ArrayList<int[]> collisions;
	
	public Vector2 moveVec;
	
	public Dynamic(World world, float x, float y) {
		super(world, x, y);
		
		collisions = new ArrayList<int[]>();
		
		moveVec = new Vector2(0,0);
	}

	//entity update function called on every frame before the draw phase.
	@Override
	public void update() {
		norm();
		calc();
		phys();
		post();
	}
	
	//resets some variables at the start of every update cycles
	public void norm() {}
	
	//calculates velocity and collisions for object
	public void phys() {
		float[] originalPos = new float[]{x,y};
		
		float vel = getVel();
		if(moveVec.x != 0 || moveVec.y != 0){
			if(vel < fric) {
				moveVec.x = 0;
				moveVec.y = 0;
			}
			else {
				moveVec.x -= moveVec.x/vel*fric;
				moveVec.y -= moveVec.y/vel*fric;
			}
		}
		
		x += moveVec.x;
		y += moveVec.y;
		
		if(moveVec.x != 0 || moveVec.y != 0)col(true,originalPos);
	}
	
	//fix collision to stop gliching into walls
	
	public int col(boolean move, float[] originalPos){
		collisions = new ArrayList<int[]>();
		
		final int TILE_COL = 1; 
		final int ENTITY_COL = 2;
		
		int collisionType = 0;
		Rectangle bBox = getBoundingBox();
		Polygon hBox = getHitbox();
		
		int center_x = ((int) (x))/Tile.TS;
		int center_y = ((int) (y))/Tile.TS;
		
		int tile_lt = ((int) (bBox.x))/Tile.TS;
		int tile_rt = ((int) (bBox.x+bBox.width))/Tile.TS;
		int tile_dn = ((int) (bBox.y))/Tile.TS;
		int tile_up = ((int) (bBox.y+bBox.height))/Tile.TS;
		
		float xChange = 0;
		float yChange = 0;
		
		boolean collide_lt = false;
		boolean collide_rt = false;
		boolean collide_dn = false;
		boolean collide_up = false;
		
		Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
		
		for(int i = tile_lt; i <= tile_rt; i++) {
			for(int k = tile_dn; k <= tile_up; k++) {
				if(world.curFloor.tm[k][i].data == 1) {
					Polygon tile_hBox = new Polygon(new float[] {i*Tile.TS,k*Tile.TS,(i+1)*Tile.TS,k*Tile.TS,(i+1)*Tile.TS,(k+1)*Tile.TS,i*Tile.TS,(k+1)*Tile.TS});
					
					if(Intersector.overlapConvexPolygons(hBox, tile_hBox, mtv)) {
						collisions.add(new int[]{i,k});
						if(center_x>i){
							if(center_y>k){ //Tile is to the bottom left
								if(world.curFloor.tm[k+1][i].data==1){
									collide_lt = true;
									xChange = Math.max(xChange, mtv.depth);
									if(world.curFloor.tm[k][i+1].data==1){
										collide_dn = true;
										yChange = Math.max(yChange, mtv.depth);
									}
								}else if(world.curFloor.tm[k][i+1].data==1){
									collide_dn = true;
									yChange = Math.max(yChange, mtv.depth);
								}else{
									if(Math.abs(k*Tile.TS-originalPos[1])>Math.abs(i*Tile.TS-originalPos[0])){
										collide_dn = true;
										yChange = Math.max(yChange, mtv.depth);
									}
									else {
										collide_lt = true;
										xChange = Math.max(xChange, mtv.depth);
									}
								}
							}else if(center_y<k){ //Tile is to the top left
								if(world.curFloor.tm[k-1][i].data==1){
									collide_lt = true;
									xChange = Math.max(xChange, mtv.depth);
									if(world.curFloor.tm[k][i+1].data==1){
										collide_up = true;
										yChange = Math.max(yChange, mtv.depth);
									}
								}else if(world.curFloor.tm[k][i+1].data==1){
									collide_up = true;
									yChange = Math.max(yChange, mtv.depth);
								}else{
									if(Math.abs((k+1)*Tile.TS-originalPos[1])>Math.abs(i*Tile.TS-originalPos[0])){
										collide_up = true;
										yChange = Math.max(yChange, mtv.depth);
									}
									else{
										collide_lt = true;
										xChange = Math.max(xChange, mtv.depth);
									}
								}
							}else{ //Tile is to the left
								collide_lt = true;
								xChange = Math.max(xChange, mtv.depth);
							}
						}else if(center_x<i){
							if(center_y>k){ //Tile is the the bottom right
								if(world.curFloor.tm[k+1][i].data==1){
									collide_rt = true;
									xChange = Math.max(xChange, mtv.depth);
									if(world.curFloor.tm[k][i-1].data==1){
										collide_dn = true;
										yChange = Math.max(yChange, mtv.depth);
									}
								}else if(world.curFloor.tm[k][i-1].data==1){
									collide_dn = true;
									yChange = Math.max(yChange, mtv.depth);
								}else{
									if(Math.abs(k*Tile.TS-originalPos[1])>Math.abs((i+1)*Tile.TS-originalPos[0])){
										collide_dn = true;
										yChange = Math.max(yChange, mtv.depth);
									}
									else{
										collide_rt = true;
										xChange = Math.max(xChange, mtv.depth);
									}
								}
							}else if(center_y<k){ //Tile is to the top right
								if(world.curFloor.tm[k-1][i].data==1){
									collide_rt = true;
									xChange = Math.max(xChange, mtv.depth);
									if(world.curFloor.tm[k][i-1].data==1){
										collide_up = true;
										yChange = Math.max(yChange, mtv.depth);
									}
								}else if(world.curFloor.tm[k][i-1].data==1){
									collide_up = true;
									yChange = Math.max(yChange, mtv.depth);
								}else{
									if(Math.abs((k+1)*Tile.TS-originalPos[1])>Math.abs((i+1)*Tile.TS-originalPos[0])){
										collide_up = true;
										yChange = Math.max(yChange, mtv.depth);
									}
									else{
										collide_rt = true;
										xChange = Math.max(xChange, mtv.depth);
									}
								}
							}else{ //Tile is to the right
								collide_rt = true;
								xChange = Math.max(xChange, mtv.depth);
							}
						}else if(center_y>k){ //Tile is the bottom
							collide_dn = true;
							yChange = Math.max(yChange, mtv.depth);
						}else if(center_y<k){ //Tile is to the top
							collide_up = true;
							yChange = Math.max(yChange, mtv.depth);
						}else{ //Tile is the same as the one the entity is in
							collide_lt = true;
							collide_rt = true;
							collide_dn = true;
							collide_up = true;
						}
					}
				}
			}
		}
		
		for(Entity e: world.entities) {
			if(e.solid && !e.equals(this) && Intersector.overlaps(bBox, e.getBoundingBox()) && Intersector.overlapConvexPolygons(hBox, e.getHitbox(),mtv)) {
				xChange = Math.max(xChange, Math.abs(mtv.normal.x*mtv.depth));
				yChange = Math.max(yChange, Math.abs(mtv.normal.y*mtv.depth));
				
//				move = true;
				
				if(mtv.normal.x > 0) collide_lt = true;
				if(mtv.normal.x < 0) collide_rt = true;
				if(mtv.normal.y > 0) collide_dn = true;
				if(mtv.normal.y < 0) collide_up = true;
				if(mtv.normal.y !=0||mtv.normal.y !=1)collisionType = ENTITY_COL;
			}
		}
		
		if(move){
			if(collide_lt){
				x+=xChange;
				moveVec.x = 0;
			}
			if(collide_rt){
				x-=xChange+0.0001f;
				moveVec.x = 0;
			}
			if(collide_dn){
				y+=yChange;
				moveVec.y = 0;
			}
			if(collide_up){
				y-=yChange+0.0001f;
				moveVec.y = 0;
			}
		}
		if(collide_lt||collide_rt||collide_dn||collide_up){
			collisionType+=TILE_COL;
		}
		return collisionType;
	}
	
	public void acel(Vector2 vector, boolean trim){
		double velPre = getVel();
		moveVec.x+=vector.x;
		moveVec.y+=vector.y;
		double velAft = getVel();
		if(trim&&velAft>mvel){
			if(velPre<mvel){

				moveVec.x = (float) (moveVec.x/velAft*mvel);
				moveVec.y = (float) (moveVec.y/velAft*mvel);
			}else{
				moveVec.x = (float) (moveVec.x/velAft*velPre);
				moveVec.y = (float) (moveVec.y/velAft*velPre);
			}
		}
	}
	
	public float getVel(){
		return (float) Math.sqrt(moveVec.x*moveVec.x+moveVec.y*moveVec.y);
	}
}
