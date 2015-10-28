package com.dungeon.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player extends Dynamic {
	public Player(int x, int y) {
		super("Player", x, y, true);
	
		setAcel(3);
		setMvel(10);
		setFric(1);
	}
	
	@Override
	public void calc() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) setInp_lt(true);
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) setInp_rt(true);
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) setInp_up(true);
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) setInp_dn(true);
	}

}