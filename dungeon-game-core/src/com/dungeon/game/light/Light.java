package com.dungeon.game.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.World;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.PositionalLight;

public class Light {

	public static final Color WHITE = new Color(1,1,1,1);
	public static final Color RED = new Color(1,0.7f,0.7f,1);
	public static final Color GREEN = new Color(0.7f,1,0.7f,1);
	public static final Color BLUE = new Color(0.7f,0.7f,1,1);
	public static final Color YELLOW = new Color(1,1,0.7f,1);
	public static final Color PURPLE = new Color(1,0.7f,1,1);
	public static final Color ORANGE = new Color(1,0.8f,0.6f,1);
	
	private final static Color DEF = new Color(1,1,1,1);
	
	public World world;
	
	public PositionalLight light;
	private Entity ent;
	private int angleOff;
	private int flickerAmount;
	private int strength;
	private int curFlick;
	private int offX;
	private int offY;
	public boolean isOn;
	
	//point light with color
	public Light(World world, float x, float y, int strength, int rays, Color color, int flickerAmount, Entity ent){
		this.world = world;
		light = new PointLight(world.rayHandler, rays, color, strength, x, y);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		light.remove(false);
		offX = 0;
		offY = 0;
		isOn = false;
	}
	//point light without color
	public Light(World world, float x, float y, int strength, int rays, int flickerAmount, Entity ent){
		this.world = world;
		light = new PointLight(world.rayHandler, rays, DEF, strength, x, y);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		light.remove(false);
		offX = 0;
		offY = 0;
		isOn = false;
	}
	//cone light with color
	public Light(World world, float x, float y, int strength, int rays, Color color, int dirDeg, int coneDeg, int angleOff, int flickerAmount, Entity ent){
		this.world = world;
		light = new ConeLight(world.rayHandler, rays, color, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		this.angleOff = angleOff;
		light.remove(false);
		offX = 0;
		offY = 0;
		isOn = false;
	}
	//cone light without color
	public Light(World world, float x, float y, int strength, int rays, int dirDeg, int coneDeg, int angleOff, int flickerAmount, Entity ent){
		this.world = world;
		light = new ConeLight(world.rayHandler, rays, DEF, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		this.angleOff = angleOff;
		light.remove(false);
		offX = 0;
		offY = 0;
		isOn = false;
	}
	
	public void update(){
		int actingOffX = offX;
		int actingOffY = offY;
		if(ent.flipX)actingOffX*=-1;
		if(ent.flipY)actingOffY*=-1;
		int f_x = (int) (Math.cos(ent.angle/180*Math.PI)*actingOffX - Math.sin(ent.angle/180*Math.PI)*actingOffY);
		int f_y = (int) (Math.sin(ent.angle/180*Math.PI)*actingOffX + Math.cos(ent.angle/180*Math.PI)*actingOffY);
		light.setPosition(new Vector2(ent.x + f_x,ent.y + f_y));
		if(light instanceof ConeLight)((ConeLight) light).setDirection(ent.angle+angleOff);
		if(flickerAmount > 0){
			curFlick = (int) ((Math.random()*flickerAmount + 5*curFlick)/6); //wont this keep increasing? meh, idk, I think it needs work anyway. I'll agree the old one was bad though
			light.setDistance(strength+curFlick);
		}
	}
	
	public void setOffset(int x ,int y){
		offX = x;
		offY = y;
	}
	
	public void load(){
		light.add(world.rayHandler);
		isOn = true;
	}
	
	public void unload(){
		if(isOn){
			light.remove(false);
			isOn = false;
		}
	}
	
	
	//point light with color
	public void changeLight(int x, int y, int strength, int rays, Color color, int flickerAmount, Entity ent){
		light.remove();
		light = new PointLight(world.rayHandler, rays, color, strength, x, y);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		light.remove(false);
		offX = 0;
		offY = 0;
		isOn = false;
	}
	//point light with color
	public void changeLight(int x, int y, int strength, int rays, int flickerAmount, Entity ent){
		light.remove();
		light = new PointLight(world.rayHandler, rays, DEF, strength, x, y);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		light.remove(false);
		offX = 0;
		offY = 0;
		isOn = false;
	}
	
	//cone light with color
	public void changeLight(int x, int y, int strength, int rays, Color color, int dirDeg, int coneDeg, int angleOff, int flickerAmount, Entity ent){
		light.remove();
		light = new ConeLight(world.rayHandler, rays, color, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		this.angleOff = angleOff;
		light.remove(false);
		offX = 0;
		offY = 0;
		isOn = false;
	}
	//cone light with color
	public void changeLight(int x, int y, int strength, int rays, int dirDeg, int coneDeg, int angleOff, int flickerAmount, Entity ent){
		light.remove();
		light = new ConeLight(world.rayHandler, rays, DEF, strength, x, y, dirDeg, coneDeg);
		this.ent = ent;
		this.flickerAmount = flickerAmount;
		this.strength = strength;
		curFlick = 0;
		this.angleOff = angleOff;
		light.remove(false);
		offX = 0;
		offY = 0;
		isOn = false;
	}
	

}
