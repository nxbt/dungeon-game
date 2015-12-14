package com.dungeon.game.light;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.World;

public class Light {
	private int x;
	private int y;
	
	private Entity ent;
	
	private int radius;
	
	private Pixmap light;
	
	public Light(Entity ent, int radius) {
		light = new Pixmap(radius*2, radius*2, Pixmap.Format.RGBA8888);
		
		light.setColor(1,1,1,0);
		Pixmap.setBlending(Pixmap.Blending.None);
		light.fillRectangle(0, 0, light.getWidth(), light.getHeight());
		
		for(int i = radius; i >= 0; i--) {
			light.setColor(1,1,1, 1 - (float) Math.pow(i, 0.75)/(float) Math.pow(radius, 0.75));
			light.fillCircle(light.getWidth()/2, light.getHeight()/2,i);
		}
		
		Pixmap.setBlending(Pixmap.Blending.SourceOver);
		
		this.ent = ent;
		
		this.radius = radius;
	}
	
	public void update(World world) {
		x = (int) (ent.x+ent.width/2-world.cam.x+world.cam.WIDTH/2-(light.getWidth())/2);
		y = (int) (world.cam.HEIGHT-(ent.y+ent.height/2-world.cam.y+world.cam.HEIGHT/2)-(light.getHeight())/2);
	}
	
	public void draw(Pixmap lightMap) {
		lightMap.drawPixmap(light, 0, 0, light.getWidth(), light.getHeight(), x, y, light.getWidth(), light.getHeight());
	}
}
