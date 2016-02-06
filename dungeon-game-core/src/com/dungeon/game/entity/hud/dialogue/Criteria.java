package com.dungeon.game.entity.hud.dialogue;

import com.dungeon.game.world.World;

public abstract class Criteria{

  private World world;
  
  
  public Criteria(World world){
    this.world = world;
  }
  
  public abstract boolean metCriteria();
}
