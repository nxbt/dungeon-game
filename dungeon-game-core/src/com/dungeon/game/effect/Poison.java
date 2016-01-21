package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;

public class Poison extends Effect {
      private float dmg;
      private float rate;
	
	    public Poison(float rate, float dmg) {
		        super("Poison", duration);
		        this.rate = rate;
		        this.dmg = dmg;
	    }
	    
	    public void update(Character character){
		        calc(character);
		        if(dmg==0)killMe = true;
	}
	
    	public void calc(Character character){
	        	character.damage(Math.round(Math.max(damage*rate,1)));
	        	damage -= Math.round(Math.max(damage*rate,1));
	    }
}
