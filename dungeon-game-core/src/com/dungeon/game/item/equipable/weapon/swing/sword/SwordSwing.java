package com.dungeon.game.item.equipable.weapon.swing.sword;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public abstract class SwordSwing extends Swing {

	public SwordSwing(World world, String name) {
		super(world, name);
		// TODO Auto-generated constructor stub
	}
	
	public static final Class<?>[] SWINGS = new Class<?>[]{
		Slash.class,
		Stab.class,
		GuardJab.class,
		HiltJab.class,
		Whirlwind.class
	};

	public static Swing getSwingByName(World world, String name){
		for(int i = 0; i < SWINGS.length; i++){
			if(SWINGS[i].getName().equals("com.dungeon.game.item.equipable.weapon.swing.sword."+name)){
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
