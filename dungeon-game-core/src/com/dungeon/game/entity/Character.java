package com.dungeon.game.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ShortArray;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Immune;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.item.Inventory;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Character extends Dynamic {
	public float torq;
	
	public float move_angle;
	public float target_angle;
	
	public float life;
	public float stam;
	public float mana;
	
	public float maxLife;
	public float maxStam;
	public float maxMana;
	
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
	
	public float vision;
	
	public ArrayList<Entity> knownEntities;
	
	public ArrayList<Effect> effects;
		
	public Inventory inv;
	
	public Polygon visPolygon;
	
	public ArrayList<Polygon> visTris;
	
	public Character(World world, int x, int y) {
		super(world, x, y);
		
		immune = false;
		
		vision = 0;
		
		knownEntities = new ArrayList<Entity>();
		
		effects = new ArrayList<Effect>();
		
		visPolygon = new Polygon(new float[]{0,0,0,0,0,0});
		visTris = new ArrayList<Polygon>();
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
		sight();
		post();
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
		ArrayList<float[]> rays = new ArrayList<float[]>(); //{startX,startY,endX,endy}
		
		for(int i = -180; i < 180; i+=18){
			rays.add(new float[]{x,y,x+(float) (Math.cos((i)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((i)/180f*Math.PI)*vision*(float)Tile.TS)});
		}
		
		for(int[] corner: world.curFloor.corners){
			if(Math.sqrt((x-corner[0])*(x-corner[0])+(y-corner[1])*(y-corner[1]))<vision*Tile.TS){
				float angleSeg = (float) (Math.atan2(corner[1]-y,corner[0]-x)*180f/Math.PI);
				rays.add(new float[]{x,y,x+(float) (Math.cos((angleSeg+0.01f)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((angleSeg+0.01)/180f*Math.PI)*vision*(float)Tile.TS)});
				rays.add(new float[]{x,y,x+(float) (Math.cos((angleSeg-0.01f)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((angleSeg-0.01)/180f*Math.PI)*vision*(float)Tile.TS)});
				
			}
		}
		
//		if(this instanceof Player){
//			System.out.println("begin");
//			for(float[] num: rays){
//				System.out.println(num[0]);
//				System.out.println(num[1]);
//				System.out.println(num[2]);
//				System.out.println(num[3]);
//			}
//		}
		//TODO: add entity corners!
		//TODO: add entity edges!
		
		//calculate the verticies
		
		float[][] verticies = new float[rays.size()][2];

		ArrayList<float[]> edges = new ArrayList<float[]>();
		
		for(float[] edge: world.curFloor.edges){
			if(Intersector.distanceSegmentPoint(edge[0], edge[1], edge[2], edge[3], x, y)<vision*Tile.TS)edges.add(edge);
		}
		
		Vector2 endVertex = new Vector2(0,0);
		for(int i = 0; i <rays.size();i++){
			endVertex.x = rays.get(i)[2];
			endVertex.y = rays.get(i)[3];
			for(float[] edge: edges){
				boolean checkEdge = false;
				float startAngle = Math.atan2(edge[1]-y,edge[0]-x)*180/Math.PI;
				float endAngle = Math.atan2(edge[3]-y,edge[2]-x)*180/Math.PI;
				float rayAngle = Math.atan2(ray.get(i)[1]-y, edge[0]-x)*180/Math.PI;
				if(Math.signum(startAngle)!=Math.signum(endAngle)&&Math.abs(startAngle)+Math.abs(endAngle)>180){
					//have to transform any negative angles because of the +-180 shift!
					if(startAngle<0)startAngle = 360+startAngle;
					if(endAngle<0)startAngle = 360+endAgnle;
					if(rayAngle<0)startAngle = 360+rayAngle;
				}
				if((startAngle>=rayAngle&&endAngle<=rayAngle)||(endAngle>=rayAngle&&startAngle<=rayAngle))checkEdge = true;
				if(checkEdge)Intersector.intersectSegments(rays.get(i)[0],rays.get(i)[1], endVertex.x,endVertex.y, edge[0],edge[1], edge[2],edge[3],endVertex);
			}
			verticies[i] = (new float[]{endVertex.x, endVertex.y});
		}
		
		
		

		
		//calculate the angles of each vertex
		float[] vertexAngles = new float[verticies.length];
		for(int i = 0; i < verticies.length; i++){
			vertexAngles[i] = (float) Math.atan2(verticies[i][1]-y,verticies[i][0]-x);
		}
		
		
		
		//reorder points to be in counterclockwise fashion.
		
		Arrays.sort(vertexAngles);
		
		
		
		
		float[] finalVerticies = new float[vertexAngles.length*2];
		for(int i = 1; i < finalVerticies.length; i+=2){
			for(float[] vertex: verticies){
				if(vertexAngles[(int)i/2] == (float)Math.atan2(vertex[1]-y,vertex[0]-x)){
					finalVerticies[i-1] = vertex[0];
					finalVerticies[i] = vertex[1];
					break;
				}
			}
		}
		
		//create the visPolygon
		
//		float[] verts = new float[finalVerticies.length*2];
//		for(int i = 1; i < verts.length; i+=2){
//			verts[i-1]=finalVerticies[(int)(i/2)][0];
//			verts[i]=finalVerticies[(int)(i/2)][1];
//		}

		visPolygon = new Polygon(finalVerticies);
		
		visTris = new ArrayList<Polygon>();
		for(int i = 0; i < (visPolygon.getVertices().length/2)-1;i++){
			visTris.add(new Polygon(new float[]{x,y,visPolygon.getVertices()[i*2],visPolygon.getVertices()[i*2+1],visPolygon.getVertices()[i*2+2],visPolygon.getVertices()[i*2+3]}));
		}
		visTris.add(new Polygon(new float[]{x,y,visPolygon.getVertices()[visPolygon.getVertices().length-2],visPolygon.getVertices()[visPolygon.getVertices().length-1],visPolygon.getVertices()[0],visPolygon.getVertices()[1]}));
		
		for(Entity e: world.entities){
			if(!knownEntities.contains(e)&&!e.equals(this)){
				Rectangle bBox = e.getBoundingBox();
				float[][] corners = new float[4][2];
				corners[0] = new float[]{bBox.x,bBox.y};
				corners[0] = new float[]{bBox.x+bBox.width,bBox.y};
				corners[0] = new float[]{bBox.x+bBox.width,bBox.y+bBox.height};
				corners[0] = new float[]{bBox.x,bBox.y+bBox.height};
				boolean check = false;
				for(int i = 0; i < corners.length-1; i++){
					if(Intersector.distanceSegmentPoint(corners[i][0],corners[i][1],corners[i+1][0],corners[i+1][1], x, y)<vision*Tile.TS)check = true;
				}
				if(Intersector.distanceSegmentPoint(corners[corners.length-1][0],corners[corners.length-1][1],corners[0][0],corners[0][1], x, y)<vision*Tile.TS)check = true;
				if(check){
					for(Polygon tri: visTris){
						if(Intersector.overlapConvexPolygons(e.getHitbox(),tri)){
							knownEntities.add(e);
							break;
						}
					}
				}
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
	
	public float gain_life(float value){
		float amount = Math.min(maxLife, life+value)-life;
		life = Math.min(maxLife, life+value);
		
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
