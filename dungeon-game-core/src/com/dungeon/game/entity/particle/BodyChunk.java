package com.dungeon.game.entity.particle;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.Pool;
import com.dungeon.game.world.World;

public class BodyChunk extends Particle {
	
	private int totalTime;
	
	private com.dungeon.game.textures.entity.particle.BodyChunk texture;
	
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
	
	public static BodyChunk get(World world, float x, float y, Pixmap pix, int texX, int texY, float angle, float vel, float spriteAngle){
		BodyChunk p = pool.get();
		int texXOff = texX - pix.getWidth()/2;
		int texYOff = texY - pix.getHeight()/2;
		float rotationAngle = (float) (spriteAngle/180f*Math.PI);
		p.set(world, (float) (x + texXOff*Math.cos(rotationAngle) - texYOff*Math.sin(rotationAngle)), (float) (y + texXOff*Math.sin(rotationAngle) + texYOff*Math.cos(rotationAngle)), pix, texX, texY, angle, vel, spriteAngle);
		return p;
//		x' = x*cos q - y*sin q
//		y' = x*sin q + y*cos q 
	}
	

	public BodyChunk() {
		super();
		
		dWidth = 4;
		dHeight = 4;
		
		originX = 2;
		originY = 2;
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
		if(texture != null)texture.dispose();
		pool.dispose(this);
	}
	
	public void set(World world, float x, float y, Pixmap pix, int texX, int texY, float angle, float vel, float spriteAngle){
		totalTime = (int) (50+Math.random()*200);
		texture = com.dungeon.game.textures.entity.particle.BodyChunk.get(pix, texX, texY);
		sprite = texture.texture;
		super.set(world, x, y, totalTime, (float) (2-Math.random()*4+Math.cos(angle)*vel), (float) (2-Math.random()*4+Math.sin(angle)*vel));
		this.angle = spriteAngle;
	}

	
	public static Particle[] getChunks(World world, float xPos, float yPos, Texture tex, float angle, float vel, float spriteAngle, boolean bleeds){
		ArrayList<Particle> chunks = new ArrayList<Particle>();
		if(!tex.getTextureData().isPrepared())tex.getTextureData().prepare();
		Pixmap pix = tex.getTextureData().consumePixmap();
		for(int x = 0; x < pix.getWidth(); x+=4){
			for(int y = 0; y < pix.getHeight(); y+=4){
				chunks.add(get(world, xPos, yPos, pix, x, y, angle, vel, spriteAngle));
				if(bleeds){
					chunks.add(Blood.get(world, xPos, yPos, (float) (angle*180f/Math.PI), vel));
				}else {
					chunks.add(Poof.get(world, xPos, yPos, (float) (angle*180f/Math.PI), vel));
				}
			}
		}
		return chunks.toArray(new Particle[chunks.size()]);
	}

}
