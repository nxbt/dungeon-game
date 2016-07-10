package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.effect.damage.FireDamage;
import com.dungeon.game.effect.damage.PoisonDamage;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.character.Player;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;

public class Fire extends Effect {
	private static float TICKLENGTH = 30;
    private float tickTimer;
	
	private int str;

	public Fire(World world, int str) {
		super(world, "Fire", 0);
		this.str = str;
		tickTimer = TICKLENGTH;
		
		texture = new Texture("dizzy.png");
		graphic = new EffectGraphic(world, this);
	}
	

    
	public void update(Character character){
		calc(character);
		if(str==0)killMe = true;
	}
	
    public void calc(Character character){
    	if(tickTimer == 0){
        	character.addEffect(new FireDamage(world, str));
        	float chance = (float) Math.random();
        	while(chance < (1+character.flame_resist)/2 && str > 0){
            	str--;
            	chance = (float) Math.random();
        	}
    		tickTimer = TICKLENGTH;
    	}else tickTimer--;
	}
    
    public void begin(Character character){
		for(int i = 0; i < character.effects.size(); i++){
			Effect e = character.effects.get(i);
    		if(e instanceof Fire && !e.killMe && e != this){
    			e.killMe = true;
    			this.str+=((Fire) e).str;
    			if(character instanceof Player&&e.graphic!=null)((Player)character).effectGraphics.remove(e.graphic);
    			character.effects.remove(i);
    			i--;
    		}
    	}
    }
	
	public String getHoveredText() {
		return "You are on fire, it is "+str+" hot!";
	}
	

	
	public int getNum() {
		return (int) str;
	}

}
