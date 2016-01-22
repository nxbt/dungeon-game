package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;

public class Poison extends Effect {
      private float dmg;
      private float rate;
      
      //make tick rate slower
	
	    public Poison(float rate, float dmg) {
		        super("Poison",0);
		        this.rate = rate;
		        this.dmg = dmg;
	    }
	    
	    public void update(Character character){
		        calc(character);
		        if(dmg==0)killMe = true;
	}
	
    	public void calc(Character character){
	        	character.damage(Math.round(Math.max(dmg*rate,1)));
	        	System.out.println(dmg + " still to take.");
	        	dmg -= Math.round(Math.max(dmg*rate,1));
	    }
}
