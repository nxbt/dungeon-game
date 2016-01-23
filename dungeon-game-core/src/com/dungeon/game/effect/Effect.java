package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;
public abstract class Effect {
	
	public static Texture texture;
	
	public EffectGraphic graphic;

	private String name;
	
	public int duration;
	
	public boolean killMe;
	
	private World world;
	
	private Character character;
	
	public Effect(World world, Character character, String name, int duration){
		this.name = name;
		this.duration = duration;
		this.world = world;
		this.character = character;
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

	public int getNum() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
