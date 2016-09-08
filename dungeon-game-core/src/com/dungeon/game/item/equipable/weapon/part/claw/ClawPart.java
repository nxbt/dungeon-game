package com.dungeon.game.item.equipable.weapon.part.claw;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.item.equipable.weapon.part.Part;
import com.dungeon.game.item.equipable.weapon.swing.axe.AxeSwing;
import com.dungeon.game.world.World;

public abstract class ClawPart extends Part {
	
	//parts
	public static final int MAIN = 0;

	public ClawPart(World world, String name, Texture sprite, int level) {
		super(world, name, sprite, level);
		type = CLAW;
	}
	
	public String getDesc(){
		String string = name + "\nDamage: " + damage + "\nSpeed: " + speed + "\nKnockback: " + knockback + "\nWeight: " + weight;
		if(allowedSwings.length > 0){
			string += "\nAllowed Swings: ";
			for(int i = 0; i < allowedSwings.length; i++){
				string+=AxeSwing.getSwingByName(world, allowedSwings[i]).name;
				if(i != allowedSwings.length - 1)string +=", ";
			}
		}
		if(bannedSwings.length > 0){
			string += "\nBanned Swings: ";
			for(int i = 0; i < bannedSwings.length; i++){
				string+=AxeSwing.getSwingByName(world, bannedSwings[i]).name;
				if(i != bannedSwings.length - 1)string +=", ";
			}
		}
		string += repeatable?"\nRepeatable":"";
		return  string;
	}

}
