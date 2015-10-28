package com.dungeon.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Dynamic {
	public Player(int x, int y) {
		super("Player", x, y);
	}
	
	public void init() {
		acel = 3;
		mvel = 10;
		fric = 1;
		
		sprite = new Texture("badlogic.jpg");
		
		solid = true;
	}
	
	public void calc() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) inp_lt = true;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) inp_rt = true;
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) inp_up = true;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) inp_dn = true;
	}
}