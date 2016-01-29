package com.dungeon.game.entity;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Immune;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.item.Inventory;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Character extends Dynamic {
	public float move_angle;
	public float target_angle;
	
	public float maxLife;
	public float maxStam;
	public float maxMana;
	
	public float life;
	public float stam;
	public float mana;
	
	public boolean immune;
	public boolean stun;
	
	public boolean fight_mode;
	
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
	
	protected float vision;
	
	public ArrayList<Entity> knownEntities;
	
	public ArrayList<Effect> effects;
		
	public Inventory inv;
	
	public Character(World world, int x, int y) {
		super(world, x, y);
		
		immune = false;
		
		vision = 0;
		
		knownEntities = new ArrayList<Entity>();
		
		effects = new ArrayList<Effect>();
	}

	public void norm() {
		move_angle = 361;
	}
	
	public void update() {
		norm();
		calc();
		effect();
		move();
		phys();
		post();
		sight();
	}

	public void move() {
		
		Vector2 acelVec = new Vector2();
		acelVec.x = (float) (Math.cos(move_angle*Math.PI/180)*acel);
		acelVec.y = (float) (Math.sin(move_angle*Math.PI/180)*acel);
		if(!stun && move_angle != 361)acel( acelVec, true );
		
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
	}
	
	public void sight(){
//		ArrayList<float[]> rays = new ArrayList<float[]>(); //{startX,startY,endX,endy}
//		ArrayList<float[]> verticies = new ArrayList<float[]>();
//		for(int[] corner: world.curFloor.corners){
//			float angleSeg = (float) (Math.atan2(corner[1]-y,corner[0]-x)*180/Math.PI);
//			rays.add(new float[]{x,y,(float) (Math.cos((angleSeg+1)/180*Math.PI)*vision),(float) (Math.sin((angleSeg+1)/180*Math.PI)*vision)});
//			rays.add(new float[]{x,y,(float) (Math.cos((angleSeg-1)/180*Math.PI)*vision),(float) (Math.sin((angleSeg-1)/180*Math.PI)*vision)});
//		}
//		//TODO: add entity corners!
//		ArrayList<float[]> edges = new ArrayList<float[]>(); //{startX,startY,endX,endy};
//		for(int i = 0; i < world.curFloor.tm.length; i++){
//			for(int k = 0; k < world.curFloor.tm[i].length; k++){
//				if(world.curFloor.tm[i][k].data == 1){
//					edges.add(new float[]{k*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,i*Tile.TS});
//					edges.add(new float[]{k*Tile.TS,i*Tile.TS,k*Tile.TS,(i+1)*Tile.TS});
//					edges.add(new float[]{(k+1)*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS});
//					edges.add(new float[]{k*Tile.TS,(i+1)*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS});
//				}
//			}
//		}
//		//TODO: add entity edges!
//		
//		//calculate the verticies
//		for(float[] ray: rays){
//			Vector2 vertex = new Vector2(ray[2],ray[3]);
//			for(float[] edge: edges){
//				Intersector.intersectSegments(new Vector2(ray[0],ray[1]), vertex, new Vector2(edge[0],edge[1]), new Vector2(edge[2],edge[3]),vertex);
//			}
//			verticies.add(new float[]{vertex.x, vertex.y});
//		}
//		
//		//calculate the angles of each vertex
//		ArrayList<Float> vertexAngles = new ArrayList<Float>();
//		for(float[] vertex: verticies){
//			vertexAngles.add((float) Math.atan2(vertex[0]-x,vertex[1]-y));
//		}
//		//reorder points to be in counterclockwise fashion.
//		Collections.sort(vertexAngles);
//		ArrayList<float[]> finalVerticies = new ArrayList<float[]>();
//		for(float angle: vertexAngles){
//			for(float[] vertex: verticies){
//				if(angle == Math.atan2(vertex[0]-x,vertex[1]-y))finalVerticies.add(vertexAngles.indexOf(angle),vertex);
//			}
//		}
//		//create the visPolygon
//		float[] verts = new float[finalVerticies.size()];
//		for(float[] vertex: finalVerticies){
//			verts[finalVerticies.indexOf(vertex)*2]=vertex[0];
//			verts[finalVerticies.indexOf(vertex)*2+1]=vertex[1];
//		}
//		Polygon visPoly = new Polygon(verts);
		for(Entity e: world.entities){
			if(!knownEntities.contains(e)){
				float dist = (float) Math.sqrt(Math.abs(x-e.x)*Math.abs(x-e.x)+Math.abs(y-e.y)*Math.abs(y-e.y));
				if(dist < vision*Tile.TS)knownEntities.add(e);
			}
		}
	}
	
	public void addEffect(Effect effect){
		effects.add(effect);
		effect.begin(this);
		if(this instanceof Player&&effect.graphic!=null)((Player)this).effectGraphics.add(effect.graphic);
	}
	

	
	private void effect() {
		for(int i = effects.size()-1;i>=0;i--){
			effects.get(i).update(this);
		}
		for(int i = effects.size()-1;i>=0;i--){
			if(effects.get(i).killMe){
				effects.get(i).end(this);				
				if(this instanceof Player&&effects.get(i).graphic!=null)((Player)this).effectGraphics.remove(effects.get(i).graphic);
				effects.remove(i);
			}
		}
	}
	
	public float damage(float value /*Add an array of Effects*/, ArrayList<Effect> hitEffects){
		if(immune) return 0;
		
		float amount = life - Math.max(life-value,0);
		life-=value;
		
		if(life <= 0) killMe = true;
		
//		System.out.println(name + " took " + amount + " damage" + (life<=0? " and was killed.":"."));
		if(amount>0&&hitEffects!=null){
			for(Effect effect: hitEffects){
				addEffect(effect);
			}
		}
		return amount;
	}
	
	public boolean use_stam(float value) {
		if(stam >= value) {
			stam -= value;
			return true;
		}
		
		return false;
	}
	
	public boolean use_mana(float value) {
		if(mana >= value) {
			mana -= value;
			return true;
		}
		
		return false;
	}
	
	public float gain_life(float value /*Add an array of Effects*/){
		float amount = Math.min(maxLife, life+value)-life;
		life = Math.min(maxLife, life+value);
		
//		System.out.println(name + " gained " + amount + " life.");
		
		return amount;
	}
	
	public void gain_stam(float value) {
		if(stam<maxStam) stam = (float) Math.min(stam+value,maxStam);
	}
	
	public void gain_mana(float value) {
		if(mana<maxMana)mana = (float) Math.min(mana+value,maxMana);
	}
	
	public void equip(Weapon weapon) {
		weapon.equip(world, this);
	}
	
	public void unequip(Weapon weapon) {
		weapon.unequip(world, this);
	}
	
	public boolean hasEffect(Effect effect) {
		for(Effect e: effects) {
			if(e.getClass().equals(effect.getClass())) return true;
		}
		
		return false;
	}
}
