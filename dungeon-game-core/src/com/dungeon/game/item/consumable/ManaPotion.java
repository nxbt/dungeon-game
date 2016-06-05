package com.dungeon.game.item.consumable;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.ManaRegen;
import com.dungeon.game.effect.PotionSick;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class ManaPotion extends Consumable {

	public ManaPotion(World world) {
		super(world, "Mana Restoration Potion", "lifePotion.png");
		
		desc = "Drink to resotre mana over a short period of time.\n\n Mana restored: 40 \n Duration: 3 seconds";
	}

	@Override
	public boolean use(Character user) {
		for(Effect effect: user.effects){
			if(effect instanceof PotionSick){
				if(((PotionSick)effect).potion instanceof ManaPotion)return false;
			}
		}
		user.addEffect(new ManaRegen(world, 180,40));
		user.addEffect(new PotionSick(world, 180, new ManaPotion(world)));
		return true;

	}
	
	public String getDesc() {
		return "Consuming this item will add a mana regeneration effect to the user. While this effect is active, another mana potion item cannot be consumed.\n\n\"It's full of liquid mana.?\" -Wonerik, the archmage\n\n Mana restored: 40\n Duration: 3 seconds";
	}
}