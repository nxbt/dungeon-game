package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.effect.Dizzy;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.LifeRegen;
import com.dungeon.game.effect.Poison;
import com.dungeon.game.effect.PotionSick;
import com.dungeon.game.entity.Character;
import com.dungeon.game.world.World;

public class LifePotion extends Consumable {

	public LifePotion(World world) {
		super(world, "Health Potion");
		// TODO Auto-generated constructor stub
		sprite = new Texture("lifePotion.png");
		desc = "Ever wonder why health potions are red? \n\nYou haven't? \n\nGood.";
	}
	

	@Override
	public void init() {}


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
}
