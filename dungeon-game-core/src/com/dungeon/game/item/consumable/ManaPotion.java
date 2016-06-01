package com.dungeon.game.item.consumable;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.ManaRegen;
import com.dungeon.game.effect.PotionSick;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class ManaPotion extends Consumable {

	public ManaPotion(World world) {
		super(world, "Mana Restoration Potion", "lifePotion.png");
		
		desc = "It's full of liquid mana. Keep away from eyes.\n\nMana restored: 40 \nDuration: 2 seconds";
	}

	@Override
	public boolean use(Character user) {
		for(Effect effect: user.effects){
			if(effect instanceof PotionSick){
				if(((PotionSick)effect).potion instanceof ManaPotion)return false;
			}
		}
		user.addEffect(new ManaRegen(world, 180,40));
		user.addEffect(new PotionSick(world, 180,new ManaPotion(world)));
		return true;

	}
	
	public String getDesc() {
		return "Drink to restore mana over a short period of time. Good for setting things on fire more. \n\n" + desc;
	}
}
