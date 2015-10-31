package com.dungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera {
	OrthographicCamera cam;
	
	private final float TWEEN = 0.3f;
	
	private float x;
	private float y;
	
	public Camera(){
		this.x = 0;
		this.y = 0;
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	}
	
	public void update(float x, float y, float mouseX, float mouseY, float zoom){
		this.x += ((3*x+(mouseX-Gdx.graphics.getWidth()/2)+this.x)/4 - this.x)*TWEEN;
		this.y += ((3*y+(mouseY-Gdx.graphics.getHeight()/2)+this.y)/4 - this.y)*TWEEN;
		
		cam.position.set(this.x, this.y, 0);
		cam.zoom = zoom;
		cam.update();
	}
	
	public OrthographicCamera getCam(){
		return cam;
	}
}
