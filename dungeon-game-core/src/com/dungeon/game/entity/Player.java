package com.dungeon.game.entity;

public class Player extends Dynamic {
	public Player(int x, int y) {
		super("Player", x, y, true);
	
		setAcel(3);
		setMvel(5);
		setFric(2);
	}
	
	@Override
	public void calc() {
		setInp_up(true);
		setInp_lt(true);
	}

}