package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.entity.hud.window.TrainingWindow;
import com.dungeon.game.world.World;

public class TrainingTable extends Static {
	
	private TrainingWindow window;

	public TrainingTable(World world, float x, float y) {
		super(world, x, y, 32, 32, "trainingTable.png");
		
		window = new TrainingWindow(world, 0, 0);
		
		hitbox = new Polygon(new float[]{1,1,31,1,31,31,1,31});
		
		solid = true;
		
	}

	@Override
	public void calc() {
	}
	
	public void hovered() {
		if(world.mouse.lb_pressed&&!world.player.fightMode){
			if(world.hudEntities.contains(window)){
				window.close();
			}
			else window.open();
		}
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub
		
	}

}
