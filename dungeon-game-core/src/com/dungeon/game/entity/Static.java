package com.dungeon.game.entity;

public abstract class Static extends Entity {
	public Static() {
		super();
	}
	
	public Static(int x, int y) {
		super(x, y);
	}
	
	public Static(String name, int x, int y) {
		super(name, x, y);
	}
	
	public Static(String name, int x, int y, boolean solid) {
		super(name, x, y, solid);
	}
}