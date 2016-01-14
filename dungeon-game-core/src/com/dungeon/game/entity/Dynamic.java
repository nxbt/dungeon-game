package com.dungeon.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.world.*;

//abstract class for dynamic entities, or entities that move and respond to physics.
public abstract class Dynamic extends Entity {
	public float dx;
	public float dy;
	
	float acel;
	float fric;
	float mvel;
	float torq;
	
	public float move_angle;
	public float target_angle;
	
	public float maxLife;
	public float maxStam;
	public float maxMana;
	
	public float life;
	public float stam;
	public float mana;
	
	public int immunityTimer;
	public int immunityTime;
	public boolean immune;
	public boolean immortal;

	public int stunTimer;
	public boolean stun;
	
	public float physc_resist;
	public float arcan_resist;
	public float flame_resist;
	public float ligtn_resist;
	public float poisn_resist;
	
	public float base_physc_resist;
	public float base_arcan_resist;
	public float base_flame_resist;
	public float base_ligtn_resist;
	public float base_poisn_resist;
	
	//temp variable
	
	public ArrayList<int[]> collisions;
	
	public Dynamic(int x, int y) {
		super(x, y);
		
		solid = true;
		
		immune = true;
		immortal = false;
		
		immunityTime = 10;
		

		collisions = new ArrayList<int[]>();
	}

	//entity update function called on every frame before the draw phase.
	@Override
	public void update(World world) {
		norm();
		calc(world);
		phys(world);
		
	}
	
	//resets some variables at the start of every update cycles
	public void norm() {
		move_angle = 361;
	}
	
	//calculates velocity and collisions for object
	public void phys(World world) {
		if(stunTimer > 0) stunTimer--;
		else if(stun) stun = false;
		
		if(immunityTimer > 0) immunityTimer--;
		else if(!immortal && immune && immunityTimer == 0) immune = false;

		float[] originalPos = new float[]{x,y};
		
		float vel = (float) Math.sqrt(dx * dx + dy * dy);
		
		if(!stun && move_angle != 361) {
			if(vel < mvel) {
				dx += Math.cos(move_angle*Math.PI/180)*acel;
				dy += Math.sin(move_angle*Math.PI/180)*acel;
				
				vel = (float) Math.sqrt(dx * dx + dy * dy);
				
				if(vel + acel > mvel) {
					dx = dx/vel*mvel;
					dy = dy/vel*mvel;
				}
			}
			
			vel = (float) Math.sqrt(dx * dx + dy * dy);
		}
		
		if(dx != 0 || dy != 0){
			if(vel < fric) {
				dx = 0;
				dy = 0;
			}
			else {
				dx -= dx/vel*fric;
				dy -= dy/vel*fric;
			}
		}
		
		x += dx;
		y += dy;
		boolean turnRight = true;
		float originalAngle = angle;
		if(angle != target_angle) {
			float tempAngle = angle+180;
			float tempTargetAngle = target_angle+180;
			
			if(tempAngle>180&&tempTargetAngle>180){
				if(tempTargetAngle<tempAngle)turnRight = false;
				else turnRight = true;
			}else if(tempAngle<180&&tempTargetAngle<180){
				if(tempAngle>tempTargetAngle)turnRight = false;
				else turnRight = true;
			}else if(tempAngle>180&&tempTargetAngle<180){
				if(tempTargetAngle<tempAngle-180)turnRight = true;
				else turnRight = false;
			}else if(tempAngle<180&&tempTargetAngle>180){
				if(tempTargetAngle-180>tempAngle)turnRight = false;
				else turnRight = true;
			}
			
			if(turnRight)angle+=torq;
			else angle-=torq;
			
			float difference = 0;
			float angleModifier1 = 0;
			float angleModifier2 = 0;
			
			if(tempAngle > tempTargetAngle){
				angleModifier1 = tempAngle;
				angleModifier2 = tempTargetAngle;
			}
			else {
				if(tempAngle == tempTargetAngle)difference = 0;
				else {
					angleModifier1 = tempTargetAngle;
					angleModifier2 = tempAngle;
				}
			}
			
			if(angleModifier1-180<angleModifier2){
				difference = angleModifier1-angleModifier2;
			}
			else {
				difference = angleModifier2+Math.abs(angleModifier1-360);
			}
			
			if(difference < torq) angle = target_angle;
				
			if(angle > 180) angle -= 360;
			if(angle < -180) angle += 360;
		}
		
		col(world,true,originalPos);
		
	}
	
	public int col(World world, boolean move, float[] originalPos){ //TODO add entity collision
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
		
		for(int i = tile_lt; i <= tile_rt; i++) {
			for(int k = tile_dn; k <= tile_up; k++) {
				if(world.curFloor.tm[k][i].data == 1) {
					Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
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
							//add something here for small entities?
						}
					}
				}
			}
		}
		if(move){
			if(collide_lt){
				x+=xChange;
//				x=center_x*Tile.TS+bBox.width/2;
				dx = 0;
			}
			if(collide_rt){
				x-=xChange+0.0001f;
//				x=(center_x+1)*Tile.TS-bBox.width/2-0.001f;
				dx = 0;
			}
			if(collide_dn){
				y+=yChange;
//				y=center_y*Tile.TS+bBox.height/2;
				dy = 0;
			}
			if(collide_up){
				y-=yChange+0.0001f;
//				y=(center_y+1)*Tile.TS-bBox.height/2-0.001f;
				dy = 0;
			}
		}
		if(collide_lt||collide_rt||collide_dn||collide_up){
			collisionType+=TILE_COL;
		}
		return collisionType;
	}
	
	//===HELPER METHODS===//
	
	public float damage(float value /*Add an array of Effects*/){
		if(immune) return 0;
		
		float amount = life - Math.max(life-value,0);
		life-=value;
		
		if(life <= 0) killMe = true;
		
		immunityTimer = immunityTime;
		immune = true;
		
		stunTimer = 20;
		stun = true;
		
		System.out.println(name + " took " + amount + " damage.");
		
		return amount;
	}
	
	public float heal(float value /*Add an array of Effects*/){
		float amount = Math.max(maxLife, life+value)-life;
		life = Math.max(maxLife, life+value);
		
		System.out.println(name + " gained " + amount + " life.");
		
		return amount;
	}
}