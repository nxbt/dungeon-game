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
	
	//point light with color
	public Light(World world, float x, float y, int strength, int rays, Color color, Entity ent){
		light = new PointLight(world.rayHandler, rays, color, strength, x, y);
		this.ent = ent;
	}
	//point light without color
	public Light(World world, float x, float y, int strength, int rays, Entity ent){
		light = new PointLight(world.rayHandler, rays, def, strength, x, y);
		this.ent = ent;
	}
	//cone light with color
	public Light(World world, float x, float y, int strength, int rays, Color color, int dirDeg, int coneDeg, Entity ent){
		light = new ConeLight(world.rayHandler, rays, color, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
	}
	//cone light without color
	public Light(World world, float x, float y, int strength, int rays, int dirDeg, int coneDeg, Entity ent){
		light = new ConeLight(world.rayHandler, rays, def, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
	}
	
	public void update(){
		light.setPosition(new Vector2(ent.x,ent.y));
		if(light instanceof ConeLight)((ConeLight) light).setDirection(ent.angle);
	}
	
	
	//point light with color
	public void changeLight(World world, int x, int y, int strength, int rays, Color color, Entity ent){
		light = new PointLight(world.rayHandler, rays, color, strength, x, y);
		this.ent = ent;
	}
	//point light with color
	public void changeLight(World world, int x, int y, int strength, int rays, Entity ent){
		light = new PointLight(world.rayHandler, rays, def, strength, x, y);
		this.ent = ent;
	}
	
	//cone light with color
	public void changeLight(World world, int x, int y, int strength, int rays, Color color, int dirDeg, int coneDeg, Entity ent){
		light = new ConeLight(world.rayHandler, rays, color, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
	}
	//cone light with color
	public void changeLight(World world, int x, int y, int strength, int rays, int dirDeg, int coneDeg, Entity ent){
		light = new ConeLight(world.rayHandler, rays, def, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
	}
	

}
