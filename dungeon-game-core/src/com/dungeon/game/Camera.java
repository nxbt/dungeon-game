package com.dungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera {
	OrthographicCamera cam;
	public Camera(){
		cam = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	}
	
	public void update(float x, float y, float zoom){
		cam.position.set(x, y, 0);
		cam.zoom = zoom;
		cam.update();
	}
	
	public OrthographicCamera getCam(){
		return cam;
	}
}
