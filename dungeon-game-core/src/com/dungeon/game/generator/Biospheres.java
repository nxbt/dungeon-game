package com.dungeon.game.generator;

import java.util.ArrayList;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Biospheres extends Generation {
	private ArrayList<Circle> rooms;
	private ArrayList<ArrayList<int[]>> halls;
	
	public Biospheres(int width, int height){
		super(width, height);
		rooms = new ArrayList<Circle>();
		halls = new ArrayList<ArrayList<int[]>>();
		int x = width/2;
		int y = height/2;
	}
	
	public boolean generateStartSphere(int x, int y){
		int radius = (int) (Math.random()*10);
		Circle room = new Circle(x,y,radius);
		int nextX;
		int nextY;
		return false;
		
	}
}
