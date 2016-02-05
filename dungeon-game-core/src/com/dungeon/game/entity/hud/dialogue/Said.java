package com.dungeon.game.entity.hud.dialogue;

import com.dungeon.game.world.World;

public class Said extends Criteria{
  
  private Dialogue dialogue;
  private int index;
  
  public Dialogue(World world, Dialogue dialogue, int index){
    super(world);
    this.dialogue = dialogue;
    this.index = index;
  }
  
  public boolean metCriteria(){
    for(SpeechBubble bubble: dialogue.speechBubbles){
      if(dialogue.potentialBubbles.indexOf(bubble) == index) return
    }
    return false;
  }
}
