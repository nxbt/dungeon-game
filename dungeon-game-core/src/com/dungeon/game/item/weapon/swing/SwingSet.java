package com.dungeon.game.item.weapon.swing;

import com.dungeon.game.item.Item;
import com.dungeon.game.item.weapon.Melee;
import com.dungeon.game.world.World;
import com.dungeon.game.entity.character.Character;

public class SwingSet{

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
		
		//set the weapon for all the swings
		for(int i = 1; i < this.swings.length; i++){
			this.swings[i].weapon = this.weapon;
			this.swings[i].prevSwing = this.swings[i-1];
		}
		swings[0].prevSwing = swings[0];
		curSwing = 0;
		swings[curSwing].beginSwing();
		
		isInUse = false;

		isInAttack = false;
		
		weapon.hasHit = false;
		
	}
	
	public void progressWeapon(){
		swings[curSwing].progress(); //progress the current swing
		
		//update some variables
		isInUse = swings[curSwing].isInUse;
		isInAttack = swings[curSwing].isInAttack;
		weapon.hasHit = swings[curSwing].hasHit;
		
		//if the swing is done, we need to figure out what to do!
		if(swings[curSwing].done){
			if(swings[curSwing].nextSwing){ //if nextSwing is true, then we progress to the next swing
				curSwing++; //increase curSwing by 1
				if(curSwing == swings.length){ //if it was the last swing we need to go back to the resting position
					swings[0].prevSwing = swings[curSwing-1]; //set rest's prevSwing to the swing that just finished
					curSwing = 0; //set curSwing to 0, the resting position
				}
				
				if(!swings[curSwing].beginSwing()){ //reset variables for the new swing, and check if the owner has stanima to do so
					swings[0].prevSwing = swings[curSwing-1]; //if the owner does not have enough stanima to swing, we go to the resting position, gotta update prevSwing!
					curSwing = 0; // set the curSwing to rest
					swings[curSwing].beginSwing(); //begin the restin posion
				}
			}else { //if nextSwing is false, we go back to the resting position
				swings[0].prevSwing = swings[curSwing]; //set the prevSwing to the swing we just finished
				curSwing = 0; // set the curSwing to rest, aka 0
				swings[curSwing].beginSwing(); // begin the rest swing.
			}
		}
		
	}

	public void reset() { //reset function is called when weapons are equiped or unequiped
		swings[0].prevSwing = swings[0];
		curSwing = 0;
		swings[curSwing].beginSwing();
	}
	
	public void hit(Character c){ //called when the weapon hits a character
		swings[curSwing].hit(c); //call the swing's hit function
		weapon.hasHit = swings[curSwing].hasHit; //update hasHit
	}
	
	public void setWorld(World w){ //set the world, not sure if this function is neccicary, but using it for now due to use of static variable
		world = w; //update the world
		for(int i = 0; i < swings.length; i++)swings[i].world = w; //update all the swings worlds
	}
}
