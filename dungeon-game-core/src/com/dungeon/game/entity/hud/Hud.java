package com.dungeon.game.entity.hud;

import java.util.ArrayList;

import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public abstract class Hud extends Static {
	
	//variables used for subHudEntities
	public String subId;
	public int subOffX;
	public int subOffY;
	
	protected ArrayList<Hud> subEntities;

	public Hud(World world, float x, float y, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
		
		subEntities = new ArrayList<Hud>();
	}
	
	public boolean isHovered(){
		return (world.mouse.x > x && world.mouse.x < x + d_width && world.mouse.y > y && world.mouse.y < y + d_height);
	}
	
	public void setSubOff(String id, int x, int y){
		this.subId = id;
		subOffX = x;
		subOffY = y;
	}
	
	protected void addSubEntitiy(Hud subEnt, String id, int x, int y){
		subEnt.setSubOff(id, x, y);
		subEntities.add(subEnt);
	}
	
	protected void removeSubEntity(String id){
		for(int i = 0; i < subEntities.size(); i++)if(subEntities.get(i).subId.equals(id))subEntities.remove(subEntities.get(i));
	}
}