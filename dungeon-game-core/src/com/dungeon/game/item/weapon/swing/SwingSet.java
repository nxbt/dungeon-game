package com.dungeon.game.item.weapon.swing;

import com.dungeon.game.item.weapon.Melee;
import com.dungeon.game.world.World;

public class SwingSet {

	private Swing[] swings;
	
	private int curSwing;
	
	private World world;
	private Melee weapon;
	
	public boolean isInUse;
	
	public boolean isInAttack;
	
	
	public SwingSet(World world, Melee weapon, Swing[] swings){
		this.world = world;
		this.weapon = weapon;
		this.swings = swings;
		
		this.swings[0].weapon = this.weapon;
		
		for(int i = 1; i < this.swings.length; i++){
			this.swings[i].weapon = this.weapon;
			this.swings[i].prevSwing = this.swings[i-1];
		}
		swings[0].prevSwing = swings[0];
		curSwing = 0;
		swings[curSwing].beginSwing();
		
		isInUse = false;
		
		isInAttack = false;
		
	}
	
	public void progressWeapon(){
		swings[curSwing].progress();
		if(swings[curSwing].done){
			if(swings[curSwing].nextSwing){
				curSwing++;
				if(curSwing == swings.length){
					swings[0].prevSwing = swings[curSwing-1];
					curSwing = 0;
				}
				swings[curSwing].beginSwing();
			}else {
				swings[0].prevSwing = swings[curSwing];
				curSwing = 0;
				swings[curSwing].beginSwing();
			}
		}

		isInUse = swings[curSwing].isInUse;
		isInAttack = swings[curSwing].isInAttack;
	}

	public void reset() {
		swings[0].prevSwing = swings[0];
		curSwing = 0;
		swings[curSwing].beginSwing();
	}
}
