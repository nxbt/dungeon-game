package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;
public abstract class Effect {
	
	public static Texture texture;
	
	public EffectGraphic graphic;

	public String name;
	
	public int duration;
	
	public boolean killMe;
	
	protected World world;
	
	public Effect(World world, String name, int duration){
		this.world = world;
		
		this.name = name;
		
		this.duration = duration;
	}
	
	public void update(Character character){
		calc(character);
		if(duration>0)duration--;
		else if(duration==0){
			killMe = true;
		}
	}
	
	protected void calc(Character character){}
	
	public void begin(Character character){}
	
	public void end(Character character){}

	public String getHoveredText() {
		return "";
	}

	public String getDesc() {
		return "Yo estoy muy pantelones";
	}
	
	public int getNum() {
		return 0;
	}
	
	
}
