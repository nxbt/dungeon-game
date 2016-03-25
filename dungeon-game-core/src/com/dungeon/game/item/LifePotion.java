package com.dungeon.game.item;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.LifeRegen;
import com.dungeon.game.effect.PotionSick;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class LifePotion extends Consumable {

	public LifePotion(World world) {
		super(world, "Health Potion", "lifePotion.png");
		
		desc = "Ever wonder why health potions are red? \n\nYou haven't? \n\nGood. \n\nLife restored: 40 \nDuration: 2 seconds";
	}

	@Override
	public boolean use(Character user) {
		for(Effect effect: user.effects){
			if(effect instanceof PotionSick){
				if(((PotionSick)effect).potion instanceof LifePotion)return false;
			}
		}
		user.addEffect(new LifeRegen(world, 180,40));
		user.addEffect(new PotionSick(world, 180,new LifePotion(world)));
		return true;

	}
	
	public String getDesc() {
		return "Drink to restore health over time. Helps with the whole not-dieing thing. \n\n" + desc;
	}
}
