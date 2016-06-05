package com.dungeon.game.item.consumable;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.LifeRegen;
import com.dungeon.game.effect.PotionSick;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class LifePotion extends Consumable {
	
	public LifePotion(World world) {
		super(world, "Health Potion", "lifePotion.png");
		
		desc = "Drink to restore health over a short period of time.\n\n Health restored: 40\n Duration: 3 seconds";
	}

	@Override
	public boolean use(Character user) {
		for(Effect effect: user.effects){
			if(effect instanceof PotionSick){
				if(((PotionSick)effect).potion instanceof LifePotion)return false;
			}
		}
		user.addEffect(new LifeRegen(world, 180,40));
		user.addEffect(new PotionSick(world, 180, new LifePotion(world)));
		return true;

	}
	
	public String getDesc() {
		return "Consuming this item will add a health regeneration effect to the user. While this effect is active, another health potion item cannot be consumed.\n\n\"Ever wonder what's in a health potion?\" -Tankin, the lost adventurer\n\n Health restored: 40\n Duration: 3 seconds";
	}
}
