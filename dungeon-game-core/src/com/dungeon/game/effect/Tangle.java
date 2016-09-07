package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Static;
import com.dungeon.game.entity.Vines;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;

public class Tangle extends Effect {
	private Static vines;
	public Tangle(World world, int duration) {
		super(world, "Tangled", duration);
		texture = new Texture("tangled.png");
		graphic = new EffectGraphic(world, this);
		vines = new Vines(world);
	}
	
	public void calc(Character character){
		character.move_angle = 361;
		vines.body.setTransform(character.body.getPosition(), 0);
	}
	
	public void begin(Character character) {
		world.entities.add(vines);
	}
	
	public void end(Character character) {
		world.entities.remove(vines);
	}
	
	public String getHoveredText() {
    		return "You're tangled for "+(int)Math.round(duration/60)+" seconds. You can't move!";
    	}
    	
    	public int getNum() {
    		return (int)duration;
    	}
}
