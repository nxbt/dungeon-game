package com.dungeon.game.entity;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.item.Slot;
import com.dungeon.game.entity.Character;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public abstract class Projectile extends Dynamic {
	private Weapon weapon;
	
	public float power;
	
	private static int OFFSET = 0;
	
	public float range;
	
	public Character owner;
	
	protected Slot slot;
	
	public Projectile(World world, int x, int y, float angle, float power, Polygon hitbox, float originX, float originY, Weapon weapon) {
		super(world, x, y);
		Vector2 acelVec = new Vector2();
		acelVec.x = (float) Math.cos((angle+135)/180*Math.PI)*power;
		acelVec.y = (float) Math.sin((angle+135)/180*Math.PI)*power;
		acel(acelVec,false);
		range = 35;
		this.angle = angle;
		
		rotate = true;
		
		this.owner = weapon.owner;
		
		this.hitbox = hitbox;
		
		this.origin_x = originX;
		this.origin_y = originY;
		this.weapon = weapon;
		this.power = power;
		
		slot = new Slot(new int[] {0, 0, 0}, null);
	}

	@Override
	public void init() {
		solid = false;

	}

	@Override
	public void calc() { //TODO: make projectile turn into pickupable items
		
		for(Entity e: world.entities){
			if(!e.equals(owner)&& e.solid && e instanceof Dynamic && Intersector.overlapConvexPolygons(getHitbox(), e.getHitbox())){
				weapon.hit((Character) e,this);
				killMe = true;
			}
		}

	}
	
	public void phys(){
		float vel = getVel();
		range--;
		if(range<0||vel<fric){
			killMe = true;
			if(slot.item!=null){
				Drop drop = new Drop(world, (int)x, (int)y, slot);
				drop.angle = angle;
				world.entities.add(drop);
			}
		}else{
			moveVec.x-=moveVec.x/vel*fric;
			moveVec.y-=moveVec.y/vel*fric;
		}
		
		Polygon hitboxTile;
		if((moveVec.x != 0 || moveVec.y != 0) && col(false, new float[]{0,0})==1){
			moveVec.x = 0;
			moveVec.y = 0;
		}
		
		x+=moveVec.x;
		y+=moveVec.y;
	}

}
