package com.dungeon.game.light;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.World;

public class OldLight {
	private int x;
	private int y;
	
	private Entity ent;
	
	private float radius;
	
	private Pixmap light;
	
	public OldLight(Entity ent, float radius) {
		Texture tempLight = new Texture("light.png");
		tempLight.getTextureData().prepare();
		tempLight.dispose();
		light = tempLight.getTextureData().consumePixmap();
		
		this.ent = ent;
		
		this.radius = radius;
	}
	
	public void update(World world) {
		x = (int) (ent.x-world.cam.x+world.cam.width/2-(light.getWidth() * radius)/2);
		y = (int) (world.cam.height-(ent.y-world.cam.y+world.cam.height/2)-(light.getHeight() * radius)/2);
	}
	
	public void draw(Pixmap lightMap) {
		lightMap.drawPixmap(light, 0, 0, light.getWidth(), light.getHeight(), x, y, (int) (light.getWidth()*radius), (int) (light.getHeight()*radius));
	}
}
