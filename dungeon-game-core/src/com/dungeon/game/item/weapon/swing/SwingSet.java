package com.dungeon.game.item.weapon.swing;

import com.dungeon.game.item.weapon.Melee;
import com.dungeon.game.item.weapon.Weapon;
import com.dungeon.game.world.World;

public class SwingSet {

	private Swing[] swings;
	private World world;
	private Melee weapon;
	
	public SwingSet(World world, Melee weapon, Swing[] swings){
		this.world = world;
		this.weapon = weapon;
		this.swings = swings;
		
		this.swings[0].weapon = this.weapon;
		
		for(int i = 1; i < this.swings.length; i++){
			this.swings[i].weapon = this.weapon;
			this.swings[i].PrevSwing = this.swings[i-1];
		}
		
	}
}
