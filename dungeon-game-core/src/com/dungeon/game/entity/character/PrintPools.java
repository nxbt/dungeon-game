package com.dungeon.game.entity.character;

import com.dungeon.game.entity.particle.Footprint;
import com.dungeon.game.utilities.Pool;

public class PrintPools {
	
	public static final Pool<Footprint> humanPool = new Pool<Footprint>(500){

		@Override
		public Footprint getNew() {
			return new Footprint(new com.dungeon.game.textures.entity.particle.Footprint().texture);
		}
	};
	
	public static void init(){
		Footprint p = humanPool.get();
		p.dispose();
	}

}
