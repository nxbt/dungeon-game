package com.dungeon.game.entity.character;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.particle.Blood;
import com.dungeon.game.entity.particle.BodyChunk;
import com.dungeon.game.entity.particle.Footprint;
import com.dungeon.game.entity.particle.Particle;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.item.equipable.weapon.Weapon;
import com.dungeon.game.utilities.Pool;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Character extends Dynamic {
	

	public int[] moveTo;
	public ArrayList<int[]> path;
	
	private static final int STAGGER_TIME = 3;
	
	private static int stagger = 0;
	
	protected int staggerTimer;
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
	
	public boolean attacking;
	
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
	
	public Texture face;

	public Color speechColor;
	public String desc;
	
	public boolean bleeds;
	
	protected Pool<Footprint> printPool;
	
	private int printTimer;
	
	protected int printTime;
	
	public Character(World world, float x, float y, int width, int height, String filename) {
		super(world, x, y, width, height, filename);

		staggerTimer = stagger;
		stagger++;
		if(stagger == STAGGER_TIME)stagger = 0;
		immune = false;
		
		vision = 0;
		
		hearing = 0;
		
		knownEntities = new ArrayList<Entity>();

		seenEntities = new ArrayList<Entity>();
		
		effects = new ArrayList<Effect>();
		
		visPolygon = new Polygon(new float[]{0,0,0,0,0,0});

		solid = true;
		
		face = new Texture("face.png");
		
		speechColor = Color.BLACK;
		
		leftActivated = false;
		rightActivated = false;
		
		//default arrays
		equipSlots = new Slot[]{};
		equipItems = new Equipable[equipSlots.length];
		
		armor = 0;
		arcan_resist = 0;
		flame_resist = 0;
		ligtn_resist = 0;
		poisn_resist = 0;
		
		desc = "On this stage, we are all players. Even this guy.";
		
		bleeds = true;
		
		printTime = 7;
		
		layer = PERSON;
	}

	public void norm() {
		super.norm();
		move_angle = 361;
	}
	
	public void update() {
		norm();
		handleEquips();
		activations();
		calcResistances();
		calc();
		effect();
		move();
		phys();
		footPrints();
		sight();
		post();
		calcLight();
		
		staggerTimer++;
		if(staggerTimer == STAGGER_TIME)staggerTimer = 0;
	}

	public void move() {
		Vector2 acelVec = new Vector2();
		acelVec.x = (float) (Math.cos(move_angle*Math.PI/180)*acel/Tile.PPM);
		acelVec.y = (float) (Math.sin(move_angle*Math.PI/180)*acel/Tile.PPM);
		if(!stun && move_angle != 361)acel(acelVec, true);
		
		boolean turnRight = true;
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
			body.setAngularVelocity(0);
			
			if(difference < torq) {
				body.setTransform(body.getPosition(), (float) (target_angle*Math.PI/180f));
				body.setAwake(true);
			}
			else if(turnRight)body.setAngularVelocity((float) (torq*Math.PI/180));
			else body.setAngularVelocity((float) (-torq*Math.PI/180));
			if(body.getAngle() > Math.PI)body.setTransform(body.getPosition(), (float) (body.getAngle() - Math.PI*2f));			
			else if(body.getAngle() < -Math.PI)body.setTransform(body.getPosition(), (float) (body.getAngle() + Math.PI*2f));
		}
	}
	
	public void sight(){
		if(staggerTimer==0){
			
			ArrayList<float[]> rays = new ArrayList<float[]>(); //{startX,startY,endX,endy}
			
			for(int i = -180; i <= 180; i+=5){
				rays.add(new float[]{x,y,x+(float) (Math.cos((i)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((i)/180f*Math.PI)*vision*(float)Tile.TS),x+(float) (Math.cos((i)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((i)/180f*Math.PI)*vision*(float)Tile.TS)});
			}
			
			for(int[] corner: world.curFloor.corners){
				if(Math.sqrt((x-corner[0])*(x-corner[0])+(y-corner[1])*(y-corner[1]))<vision*Tile.TS){
					float angleSeg = (float) (Math.atan2(corner[1]-y,corner[0]-x)*180f/Math.PI);
					if(corner[2] == 0) {
						rays.add(new float[]{x,y,x+(float) (Math.cos((angleSeg+0.01f)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((angleSeg+0.01)/180f*Math.PI)*vision*(float)Tile.TS), corner[0], corner[1]});
						rays.add(new float[]{x,y,x+(float) (Math.cos((angleSeg-0.01f)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((angleSeg-0.01)/180f*Math.PI)*vision*(float)Tile.TS), corner[0], corner[1]});
					}
					else {
						rays.add(new float[]{x,y,x+(float) (Math.cos((angleSeg)/180f*Math.PI)*vision*(float)Tile.TS),y+(float) (Math.sin((angleSeg)/180f*Math.PI)*vision*(float)Tile.TS), corner[0], corner[1]});
					}
				}
			}
			
			//calculate the verticies
			
			ArrayList<float[]> verticies = new ArrayList<float[]>();
	
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
				if(Math.sqrt((rays.get(i)[0] - endVertex.x)*(rays.get(i)[0] - endVertex.x) + (rays.get(i)[1] - endVertex.y)*(rays.get(i)[1] - endVertex.y)) + Tile.TS/2 > Math.sqrt((rays.get(i)[0] - rays.get(i)[4])*(rays.get(i)[0] - rays.get(i)[4]) + (rays.get(i)[1] - rays.get(i)[5])*(rays.get(i)[1] - rays.get(i)[5])))verticies.add(new float[]{endVertex.x, endVertex.y});
			}
			while(verticies.size() < 3){
				verticies.add(new float[]{x, y});
			}
			
			//calculate the angles of each vertex
			float[] vertexAngles = new float[verticies.size()];
			for(int i = 0; i < verticies.size(); i++){
				vertexAngles[i] = (float) Math.atan2(verticies.get(i)[1]-y,verticies.get(i)[0]-x);
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
			ArrayList<Entity> preSeenEnts = new ArrayList<Entity>(seenEntities);
			seenEntities = new ArrayList<Entity>();
			for(Entity e: world.entities){
				if(Math.sqrt((Math.abs(e.x - x) - e.dWidth)*(Math.abs(e.x - x) - e.dWidth)+(Math.abs(e.y - y) - e.dHeight)*(Math.abs(e.y - y) - e.dHeight)) < vision*Tile.TS && !e.equals(this)){
					try {
						if(!(e instanceof Particle) && Intersector.intersectPolygons(visPolygon, e.getVisbox(), new Polygon())){
							if(!knownEntities.contains(e))knownEntities.add(e); //some bug here...
							seenEntities.add(e);
						}
					} catch(Exception error) {
						System.out.println(error);
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
	
	protected void footPrints(){
		if(printPool != null && moveVec.len() > 0.1){
			if(printTimer == printTime){
				float yOffSet = 5;
				float angle = moveVec.angleRad();
				float x = this.x - (float) (yOffSet*Math.sin(angle));
				float y = this.y + (float) (yOffSet*Math.cos(angle));
				world.entities.add(Footprint.get(world, printPool, x, y, moveVec.angle()));
				printTimer++;
			}else if(printTimer == printTime * 2){
				float yOffSet = -5;
				float angle = moveVec.angleRad();
				float x = this.x - (float) (yOffSet*Math.sin(angle));
				float y = this.y + (float) (yOffSet*Math.cos(angle));
				world.entities.add(Footprint.get(world, printPool, x, y, moveVec.angle()));
				printTimer = 0;
			}
			else printTimer++;
		}
		
		if(bleeds && Math.random() > 0.9f + 0.4f*(life/maxLife)){
			world.entities.add(Blood.get(world, x, y, (float) (Math.random()*360f), (float) (Math.random()*3f)));
		}
	}

	protected void activations(){
		
	};
	

	private void calcResistances() {
		physc_resist = 1f - (float) (Math.pow(0.9945f,armor)*75+25)/100f;
		
	}
	
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
				Effect e = effects.get(i);
				if(this instanceof Player&&effects.get(i).graphic!=null)((Player)this).effectGraphics.remove(e.graphic);
				effects.remove(i);
				e.end(this);
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
		
		value *= Math.max(1-physc_resist,0); //reduce damage for armor, should we replace this with phys_resist and add in a calcResistances method somewhere?
		
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
	
	public void poisonDamage(float value){
		value*=Math.max(1-poisn_resist,0);
		
		life-=value;
		
		if(life <= 0) killMe = true;
	}
	
	public void fireDamage(float value){
		value*=Math.max(1-flame_resist, 0);
		
		life-=value;
		
		if(life <= 0) killMe = true;
	}
	
	public void electricDamage(float value){
		value*=Math.max(1-ligtn_resist,0);
		
		life-=value;
		
		if(life <= 0) killMe = true;
	}
	
	public void magicDamage(float value){
		value*=Math.max(1-arcan_resist,0);
		
		life-=value;
		
		if(life <= 0) killMe = true;
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
	
	public void dead(){
		super.dead();
		Particle[] chunks = BodyChunk.getChunks(world, x, y, sprite, body.getLinearVelocity().angleRad(), body.getLinearVelocity().len()*Tile.PPM, angle, bleeds);
		for(Particle c: chunks){
			world.entities.add(c);
		}
	}
	
	public void getBody(com.badlogic.gdx.physics.box2d.World world) {
		super.getBody(world);
		
		CircleShape shape = new CircleShape();
		
		shape.setRadius(0.9f);
		
		body.destroyFixture(body.getFixtureList().get(0));
		
		Fixture f = body.createFixture(shape, 0.0f);
		Filter filter = new Filter();
		filter.categoryBits = 0x0002;
		if(solid){
			filter.maskBits = -1;
		}
		else{
			filter.maskBits = 0;
		}
		f.setFriction(0);
		f.setFilterData(filter);
	}
}
