package com.dungeon.game.generator;

import java.util.ArrayList;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Biospheres extends Generation {
	private ArrayList<Circle> rooms;
	
	public Biospheres(int width, int height){
		super(width, height);
		rooms = new ArrayList<Circle>();
	}
}
