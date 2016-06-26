package com.dungeon.game.entity.character;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.item.weapon.Weapon;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Character extends Dynamic {
	

	public int[] moveTo;
	public ArrayList<int[]> path;
	
	private static final int STAGER_TIME = 3;
	
	private static int stager = 0;
	
	protected int stagerTimer;
	public float torq;
	
	public float move_angle;
	public float target_angle;
	
	public float acel;
	public float life;
	public float stam;
	public float mana;
	
	public float maxLife;
	public float maxStam;
	public float maxMana;
	
	public int gold;
	
	public boolean immune;
	
	public boolean stun;
	
	public Slot[] equipSlots;
	
	public Equipable[] equipItems;
	
	public Hand leftEquiped;
	public Hand rightEquiped;
	
	public boolean leftActivated;
	public boolean rightActivated;
	
	public boolean fightMode;
	
	protected boolean attacking;
	
	public float armor;
	
	public float physc_resist;
	public float arcan_resist;
	public float flame_resist;
	public float ligtn_resist;
	public float poisn_resist;
	
	public float baseArmor;
	
	public float base_physc_resist;
	public float base_arcan_resist;
	public float base_flame_resist;
	public float base_ligtn_resist;
	public float base_poisn_resist;
	
	public float vision;
	
	public float hearing;
	
	public ArrayList<Entity> knownEntities;
	public ArrayList<Entity> seenEntities;
	
	public ArrayList<Effect> effects;
		
	public Inventory inv;
	
	public Polygon visPolygon;
	
	public ArrayList<Polygon> visTris;
	
	public Texture face;

	public Color speechColor;
	
	public Character(World world, float x, float y, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
		
		stagerTimer = stager;
		stager++;
		if(stager == STAGER_TIME)stager = 0;
		immune = false;
		
		vision = 0;
		
		hearing = 0;
		
		knownEntities = new ArrayList<Entity>();

		seenEntities = new ArrayList<Entity>();
		
		effects = new ArrayList<Effect>();
		
		visPolygon = new Polygon(new float[]{0,0,0,0,0,0});
		visTris = new ArrayList<Polygon>();

		solid = true;
		
		face = new Texture("face.png");
		
		speechColor = Color.BLACK;
		
		leftActivated = false;
		rightActivated = false;
		
		//default arrays
		equipSlots = new Slot[]{};
		equipItems = new Equipable[equipSlots.length];
	}

	public void norm() {
		move_angle = 361;
	}
	
	public void update() {
		norm();
		handleEquips();
		activations();
		calc();
		effect();
		move();
		phys();
		sight();
		post();
		calcLight();
		stagerTimer++;
		if(stagerTimer == STAGER_TIME)stagerTimer = 0;
	}

	public void move() {
		
		Vector2 acelVec = new Vector2();
		acelVec.x = (float) (Math.cos(move_angle*Math.PI/180)*acel);
		acelVec.y = (float) (Math.sin(move_angle*Math.PI/180)*acel);
		if(!stun && move_angle != 361)acel( acelVec, true );
		
		boolean turnRight = true;
		@SuppressWarnings("unused")
		float originalAngle = angle;
		if(angle != target_angle) {
			float tempAngle = angle+180;
			float tempTargetAngle = target_angle+180;
			
			if(tempAngle>=180&&tempTargetAngle>=180){
				if(tempTargetAngle<tempAngle)turnRight = false;
				else turnRight = true;
			}else if(tempAngle<=180&&tempTargetAngle<=180){
				if(tempAngle>tempTargetAngle)turnRight = false;
				else turnRight = true;
			}else if(tempAngle>=180&&tempTargetAngle<=180){
				if(tempTargetAngle<tempAngle-180)turnRight = true;
				else turnRight = false;
			}else if(tempAngle<=180&&tempTargetAngle>=180){
				if(tempTargetAngle-180>tempAngle)turnRight = false;
				else turnRight = true;
			}
			
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
			else if(turnRight)angle+=torq;
			else angle-=torq;
				
			if(angle > 180) angle -= 360;
			if(angle < -180) angle += 360;
		}
	}
	
	public void sight(){
		if(stagerTimer==0){
			
			ArrayList<float[]> rays = new ArrayList<float[]>(); //{startX,startY,endX,endy}
			
			for(int i = -180; i < 180; i+=18){
				rays.add(new float[]{x,y,x+(float) (Math.cos((i)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((i)/180f*Math.PI)*vision*(float)Tile.TS)});
			}
			
			for(int[] corner: world.curFloor.corners){
				if(Math.sqrt((x-corner[0])*(x-corner[0])+(y-corner[1])*(y-corner[1]))<vision*Tile.TS){
					float angleSeg = (float) (Math.atan2(corner[1]-y,corner[0]-x)*180f/Math.PI);
					if(corner[2] == 0) {
						rays.add(new float[]{x,y,x+(float) (Math.cos((angleSeg+0.01f)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((angleSeg+0.01)/180f*Math.PI)*vision*(float)Tile.TS)});
						rays.add(new float[]{x,y,x+(float) (Math.cos((angleSeg-0.01f)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((angleSeg-0.01)/180f*Math.PI)*vision*(float)Tile.TS)});
					}
					else {
						rays.add(new float[]{x,y,x+(float) (Math.cos((angleSeg)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((angleSeg)/180f*Math.PI)*vision*(float)Tile.TS)});
					}
				}
			}
			
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
					Intersector.intersectSegments(rays.get(i)[0],rays.get(i)[1], endVertex.x,endVertex.y, edge[0],edge[1], edge[2],edge[3],endVertex);
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
					if(vertexAngles[i/2] == (float)Math.atan2(vertex[1]-y,vertex[0]-x)){
						finalVerticies[i-1] = vertex[0];
						finalVerticies[i] = vertex[1];
						break;
					}
				}
			}
			
			//create the visPolygon
	
			visPolygon = new Polygon(finalVerticies);
			
			visTris = new ArrayList<Polygon>();
			for(int i = 0; i < (visPolygon.getVertices().length/2)-1;i++){
				visTris.add(new Polygon(new float[]{x,y,visPolygon.getVertices()[i*2],visPolygon.getVertices()[i*2+1],visPolygon.getVertices()[i*2+2],visPolygon.getVertices()[i*2+3]}));
			}
			visTris.add(new Polygon(new float[]{x,y,visPolygon.getVertices()[visPolygon.getVertices().length-2],visPolygon.getVertices()[visPolygon.getVertices().length-1],visPolygon.getVertices()[0],visPolygon.getVertices()[1]}));
		
			ArrayList<Entity> preSeenEnts = new ArrayList<Entity>(seenEntities);
			seenEntities = new ArrayList<Entity>();
			for(Entity e: world.entities){
				if(!e.equals(this)){
					for(Polygon tri: visTris){
						if(Intersector.overlapConvexPolygons(e.getHitbox(),tri)){
							if(!knownEntities.contains(e))knownEntities.add(e);
							seenEntities.add(e);
							break;
						}
					}
				}
			}
			
			for(Entity e: preSeenEnts){
				if(!seenEntities.contains(e))unsee(e);
			}
			
			for(Entity e: seenEntities){
				if(!preSeenEnts.contains(e))see(e);
			}
		}
	}
	
	public void handleEquips() {
		for(int i = 0; i < equipSlots.length; i++){
			if(equipSlots[i].item == null){
				if(equipItems[i] != null){
					equipItems[i].unequip();
					equipItems[i] = null;
				}
			}else{
				if(equipItems[i] == null){
					if(equipSlots[i].item instanceof Weapon){
						equipItems[i] = null;
					}else{
						equipItems[i] = (Equipable) equipSlots[i].item;
						equipItems[i].equip(this, false);
					}
				}else{
					if(!equipSlots[i].item.equals(equipItems[i])){
						equipItems[i].unequip();
						if(equipSlots[i].item instanceof Weapon){
							equipItems[i] = null;
						}else{
							equipItems[i] = (Equipable) equipSlots[i].item;
							equipItems[i].equip(this, false);
						}
					}
				}
			}
			
		}
		
	}

	protected void activations(){
		
	};
	
	public void see(Entity e) {}

	public void unsee(Entity e) {}

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
	
	public void endEffects(){
		for(Effect effect: effects){
			effect.end(this);
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
	
	public void equip(Hand weapon, boolean leftSide) {
		weapon.equip(this, leftSide);
	}
	
	public void unequip(Hand weapon) {
		weapon.unequip();
	}
	
	public boolean hasEffect(Effect effect) {
		for(Effect e: effects) {
			if(e.getClass().equals(effect.getClass())) return true;
		}
		
		return false;
	}
	
	public boolean spendGold(int value){
		if(gold >= value){
			gold -=value;
			return true;
		}
		return false;
		
	}
	
	public void toggleFightMode() {
		fightMode = !fightMode;
		
		if(fightMode) {
			if(equipSlots[0].item != null && equipSlots[0].item instanceof Weapon){
				equipItems[0] = (Equipable) equipSlots[0].item;
				equip((Hand) equipItems[0], true);
			}
			if(equipSlots[1].item != null && equipSlots[1].item instanceof Weapon){
				equipItems[1] = (Equipable) equipSlots[1].item;
				equip((Hand) equipItems[1], false);
			}
		}
		
		else {
			if(equipItems[0] != null && equipItems[0] instanceof Weapon){
				equipItems[0].unequip();
				equipItems[0] = null;
			}
			if(equipItems[1] != null && equipItems[1] instanceof Weapon){
				equipItems[1].unequip();
				equipItems[1] = null;
			}
		}
	}
}
