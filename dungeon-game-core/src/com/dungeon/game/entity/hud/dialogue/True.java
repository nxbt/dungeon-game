package com.dungeon.game.entity.hud.dialogue;

import com.dungeon.game.world.World;

public class True extends Criteria{
  
  
  public True(World world){
    super(world);
  }
  
  public boolean metCriteria(){
    return true;
  }
}
