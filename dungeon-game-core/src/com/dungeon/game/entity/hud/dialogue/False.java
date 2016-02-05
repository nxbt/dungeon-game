package com.dungeon.game.entity.hud.dialogue;

import com.dungeon.game.world.World;

public class False extends Criteria{
  
  
  public Dialogue(World world){
    super(world);
  }
  
  public boolean metCriteria(){
    return false;
  }
}
