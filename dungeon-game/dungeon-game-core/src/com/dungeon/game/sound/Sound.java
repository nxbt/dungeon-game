package com.dungeon.game.sound;

import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Sound {

	private World world;
	
	private int language;
	private float volume;
	private int duration;
	
	private boolean killMe;

	private float x;
	private float y;
	
	public Sound(World world){
		this.world = world;
	}
	
	public abstract void hear(Character character); // what happens when a character hears this method;
	
	public void calc(){
		for(Entity character: world.entities){
			if(character instanceof Character){
				if(Math.sqrt((x-character.x)*(x-character.x)+(y-character.y)*(y-character.y))<((Character)character).hearing*Tile.TS)hear(((Character)character));
			}
		}
		duration--;
		if(duration == 0)killMe = true;
	}
}
