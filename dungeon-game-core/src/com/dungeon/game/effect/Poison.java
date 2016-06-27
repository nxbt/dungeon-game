package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;

public class Poison extends Effect {
	private static float TICKLENGTH = 30;
    private float dmg;
    private float rate;
    private float tickTimer;
      
      //make tick rate slower
	
	    public Poison(World world, float rate, float dmg) {
		        super(world, "Poison",0);
		        this.rate = rate;
		        this.dmg = dmg;
		        tickTimer = TICKLENGTH;
				texture = new Texture("poison.png");
				graphic = new EffectGraphic(world, this);
	    }
	    
	    public void update(Character character){
		        calc(character);
		        if(dmg==0)killMe = true;
	}
	
    	public void calc(Character character){
    			if(tickTimer == 0){
    				character.addEffect(new PoisonDamage(world, Math.round(Math.max(dmg*rate,1))));
    	        	dmg -= Math.round(Math.max(dmg*rate,1));
    	        	tickTimer = TICKLENGTH;
    			}else tickTimer--;
	    }
    	
    	public String getHoveredText() {
    		return "Remaining Damage: "+Math.round(dmg);
    	}
    	
    	public int getNum() {
    		return (int)dmg;
    	}
}
