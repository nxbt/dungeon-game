package com.dungeon.game.item.equipable.weapon.part.axe;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.item.equipable.weapon.part.Part;
import com.dungeon.game.item.equipable.weapon.swing.axe.AxeSwing;
import com.dungeon.game.item.equipable.weapon.swing.sword.SwordSwing;
import com.dungeon.game.world.World;

public abstract class AxePart extends Part {
	
	//parts
	public static final int BLADE = 0;
	public static final int TIP = 1;
	public static final int HANDLE = 2;

	public AxePart(World world, String name, Texture sprite, int level) {
		super(world, name, sprite, level);
		type = AXE;
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
		string += repeatable?"\nRepeatable":"\nNot Repeatable";
		return  string;
	}

}
