package com.dungeon.game.entity.particle;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.Pool;
import com.dungeon.game.world.World;

public class BodyChunk extends Particle {
	
	private int totalTime;
	
	public static final Pool<BodyChunk> pool = new Pool<BodyChunk>(100){

		@Override
		public BodyChunk getNew() {
			return new BodyChunk();
		}
		
	};
	
	public static void init(){
		BodyChunk p = pool.get();
		p.dispose();
	}
	
	public static BodyChunk get(World world, float x, float y, Pixmap pix, int texX, int texY){
		BodyChunk p = pool.get();
		p.set(world, x + texX, y + texY, pix, texX, texY);
		return p;
	}
	

	public BodyChunk() {
		super();
		
		dWidth = 4;
		dHeight = 4;
		
		originX = 2;
		originY = 2;
		
		sprite = new com.dungeon.game.textures.entity.particle.Blood().texture;
	}
	
	public void calc() {
		dx*=0.8f;
		dy*=0.8f;
		alpha = duration/(float)(totalTime);
		super.calc();
	}

	@Override
	public void post() {
	}
	
	public static float getAngle(float angle){
		return (float) ((angle+20f-Math.random()*40)*Math.PI/180f);
	}
	
	public static float getVel(float vel){
		return (float) (vel *(0.7f+Math.random())*1.3f);
	}

	@Override
	public void dispose() {
		pool.dispose(this);
	}
	
	public void set(World world, float x, float y, Pixmap pix, int texX, int texY){
		totalTime = (int) (50+Math.random()*200);
		sprite = new com.dungeon.game.textures.entity.particle.BodyChunk(pix, texX, texY).texture;
		super.set(world, x, y, totalTime, 0, 0);
	}

	
	public static BodyChunk[] getChunks(World world, float xPos, float yPos, Texture tex){
		xPos-=tex.getWidth()/2;
		yPos-=tex.getHeight()/2;
		ArrayList<BodyChunk> chunks = new ArrayList<BodyChunk>();
		if(!tex.getTextureData().isPrepared())tex.getTextureData().prepare();
		Pixmap pix = tex.getTextureData().consumePixmap();
		for(int x = 0; x < pix.getWidth(); x+=4){
			for(int y = 0; y < pix.getHeight(); y+=4){
				chunks.add(get(world, xPos, yPos, pix, x, y));
			}
		}
		return chunks.toArray(new BodyChunk[chunks.size()]);
	}

}
