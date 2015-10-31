package com.dungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera {
	OrthographicCamera cam;
	
	private final float TWEEN = 0.2f;
	
	private float x;
	private float y;
	
	public Camera(){
		this.x = 0;
		this.y = 0;
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	}
	
	public void update(float x, float y, float zoom, float mouseX, float mouseY){
		mouseX+=this.x;
		mouseY+=this.y;
		float targetX = (2*x+mouseX)/3;
		float targetY = (2*y+mouseY)/3;
		this.x += (targetX - this.x)*TWEEN;
		this.y += (targetY - this.y)*TWEEN;
		
		cam.position.set(this.x, this.y, 0);
		cam.zoom = zoom;
		cam.update();
	}
	
	public OrthographicCamera getCam(){
		return cam;
	}
}
