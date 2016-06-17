package com.dungeon.game.light;

import com.badlogic.gdx.graphics.Color;
import box2dLight.PositionalLight;
import box2dLight.PointLight;
import box2dLight.ConeLight;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.World;

import box2dLight.PointLight;

public class Light {
	
	private final static Color def = new Color(1,1,1,1);
	
	public World world;
	
	private PositionalLight light;
	private Entity ent;
	private int angleOff;
	private int flickerAmount;
	private int strength;
	private int curFlick;
	private int flickDir;
	
	//point light with color
	public Light(World world, float x, float y, int strength, int rays, Color color, int flickerAmount, Entity ent){
		light = new PointLight(world.rayHandler, rays, color, strength, x, y);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		flickDir = 1;
	}
	//point light without color
	public Light(World world, float x, float y, int strength, int rays, int flickerAmount, Entity ent){
		light = new PointLight(world.rayHandler, rays, def, strength, x, y);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		flickDir = 1;
	}
	//cone light with color
	public Light(World world, float x, float y, int strength, int rays, Color color, int dirDeg, int coneDeg, int angleOff, int flickerAmount, Entity ent){
		light = new ConeLight(world.rayHandler, rays, color, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		flickDir = 1;
		this.angleOff = angleOff;
	}
	//cone light without color
	public Light(World world, float x, float y, int strength, int rays, int dirDeg, int coneDeg, int angleOff, int flickerAmount, Entity ent){
		light = new ConeLight(world.rayHandler, rays, def, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		flickDir = 1;
		this.angleOff = angleOff;
	}
	
	public void update(){
		light.setPosition(new Vector2(ent.x,ent.y));
		if(light instanceof ConeLight)((ConeLight) light).setDirection(ent.angle+angleOff);
		if(flickerAmount > 0){
			curFlick += flickDir;
			if(curFlick > flickerAmount)flickDir = (int) (-1*flickerAmount/10*(0.5+Math.random()));
			if(curFlick < -flickerAmount)flickDir = (int) (1*flickerAmount/10*(0.5+Math.random()));
			light.setDistance(strength+curFlick);
		}
	}
	
	
	//point light with color
	public void changeLight(World world, int x, int y, int strength, int rays, Color color, int flickerAmount, Entity ent){
		light = new PointLight(world.rayHandler, rays, color, strength, x, y);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		flickDir = 1;
	}
	//point light with color
	public void changeLight(World world, int x, int y, int strength, int rays, int flickerAmount, Entity ent){
		light = new PointLight(world.rayHandler, rays, def, strength, x, y);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		flickDir = 1;
	}
	
	//cone light with color
	public void changeLight(World world, int x, int y, int strength, int rays, Color color, int dirDeg, int coneDeg, int angleOff, int flickerAmount, Entity ent){
		light = new ConeLight(world.rayHandler, rays, color, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		flickDir = 1;
		this.angleOff = angleOff;
	}
	//cone light with color
	public void changeLight(World world, int x, int y, int strength, int rays, int dirDeg, int coneDeg, int angleOff, int flickerAmount, Entity ent){
		light = new ConeLight(world.rayHandler, rays, def, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		flickDir = 1;
		this.angleOff = angleOff;
	}
	

}
