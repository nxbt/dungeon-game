package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Web;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;

public class Webbed extends Effect {
	private Web web;
	public Webbed(World world, int duration) {
		super(world, "Webbed", duration);
		texture = new Texture("web.png");
		graphic = new EffectGraphic(world, this);
		web = new Web(world);
	}
	
	public void calc(Character character){
		character.move_angle = 361;
		web.body.setTransform(character.body.getPosition(), 0);
	}
	
	public void begin(Character character) {
		world.entities.add(web);
	}
	
	public void end(Character character) {
		world.entities.remove(web);
	}
	
	public String getHoveredText() {
    		return "You're webbed for "+(int)Math.round(duration/60)+" seconds. You can't move!";
    	}
    	
    	public int getNum() {
    		return (int)duration;
    	}
}
