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
		return "Health Potion\n\nThe health potion is the most abundant and widely used potion in the dungeons. Nearly all those who travel into the below bring these red potions with them, "
				+ "and those who don't aren't likely to survive for long. The health potion comes in a standered potion glass designed for a single use; the entirety of the liquid can be "
				+ "swallowed in one or two gulps. Some speculate that the reason this glass is used for the majority of potions is because it was first used only for this ubiquitous "
				+ "elixir, but empty bottles were used by herbalists for storing other potions.\n\nHealth potions work extremely quickly. As soon as the liquid is consumed, wounds and "
				+ "injuries begin closing and the users vitality begins to restore. However, the high is short lived, as after just a few seconds the healing effect wears off. On top of "
				+ "the healing, these potions provide an exilerating feeling, which is as quick to impact and receed as the healing itself. Many users have become addicted to this feeling, "
				+ "and will consume a potion even when in good health, just for the high provided. As such, care must be taken in the use of these potions, or addiction may develop.\n\n"
				+ "Because of their popularity, health potions are one of the few potions that can be bought from merchants other than herbalists. Traveling merchants always have a few, "
				+ "but always keep a few for themselves, and the nature of their job means they will often find themselves with otherwise life threatening injuries. General merchants "
				+ "will usually have a stockpile, because of the nearly guaranteed profit they provide. Herbalists sell them cheaply, and the recipe for one is one of the easiest to "
				+ "learn. Despite how common these red bottles are in villages and towns, they are nearly impossible to find in the below, as anyone holding one would have used it before "
				+ "meeting their demise.\n\n\"Ever wonder what's in a health potion?\" -Tankin, the first herbalist";
	}
}
