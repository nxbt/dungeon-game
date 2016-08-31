package com.dungeon.game.textures.entity.particle;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;
import com.dungeon.game.utilities.Pool;

public class BodyChunk extends ProceduralTexture {
	
	public static final Pool<BodyChunk> pool = new Pool<BodyChunk>(200){

		@Override
		public BodyChunk getNew() {
			return new BodyChunk();
		}
		
	};
	
	public static void init(){
		BodyChunk p = pool.get();
		p.dispose();
	}

	public static BodyChunk get(Pixmap pix, int x, int y){
		BodyChunk p = pool.get();
		p.set(pix, x, y);
		return p;
	}

	public BodyChunk() {
		super(0, 0, new Object[0]);
		
	}

	@Override
	public void generateTexture(Object[] args) {
		if(args.length > 0){
			Pixmap pix = (Pixmap) args[0];
			int x = (Integer) args[1];
			int y = (Integer) args[2];
			Pixmap texMap = new Pixmap(4,4,Pixmap.Format.RGBA8888);
			
			texMap.drawPixmap(pix, x, pix.getHeight() - 4 - y, 4, 4, 0, 0, 4, 4);
			
			texture = new Texture(texMap);
		}

	}
	
	private void set(Pixmap pix, int x, int y){
		generateTexture(new Object[]{pix, x, y});
	}
	
	public void dispose() {
		pool.dispose(this);
	}

}
