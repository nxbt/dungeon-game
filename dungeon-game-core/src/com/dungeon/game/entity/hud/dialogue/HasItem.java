package com.dungeon.game.entity.hud.dialogue;

import com.dungeon.game.world.World;

public class HasItem extends Criteria{

  private Item item;
  private Character character;
  
  
  public Dialogue(World world, Item item, Character character){
    super(world);
    this.item = item;
    this.character = character;
  }
  
  public boolean metCriteria(){
    return character.inv.contains(item);
  }
}
