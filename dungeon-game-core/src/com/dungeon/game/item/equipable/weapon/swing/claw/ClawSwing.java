package com.dungeon.game.item.equipable.weapon.swing.claw;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public abstract class ClawSwing extends Swing {

	public ClawSwing(World world, String name) {
		super(world, name);
		// TODO Auto-generated constructor stub
	}
	
	public static final Class<?>[] SWINGS = new Class<?>[]{
		RatSwing.class
	};

	public static Swing getSwingByName(World world, String name){
		for(int i = 0; i < SWINGS.length; i++){
			if(SWINGS[i].getSimpleName().equals(name)){
				try {
					return (Swing) SWINGS[i].getConstructors()[0].newInstance(world);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
