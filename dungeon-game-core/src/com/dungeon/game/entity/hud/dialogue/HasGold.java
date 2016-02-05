package com.dungeon.game.entity.hud.dialogue;

import com.dungeon.game.world.World;

public class HasItem extends Criteria{

  private Int amount;
  private Character character;
  
  
  public Dialogue(World world, int amount, Character character){
    super(world);
    this.amount = amount;
    this.character = character;
  }
  
  public boolean metCriteria(){
    return character.gold>=amount;
  }
}
