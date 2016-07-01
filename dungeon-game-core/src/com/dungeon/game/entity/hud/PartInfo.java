package com.dungeon.game.entity.hud;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.Sword;
import com.dungeon.game.world.World;

public class PartInfo extends Hud {

	private static final int SWORD = 0;
	
	protected int type;
	
	private static final int BLADE = 0;
	private static final int GUARD = 1;
	private static final int HILT = 2;
	
	protected int data;
	
	protected int id;
	
	private Texture tex;

	public PartInfo(World world, float x, float y, int type, int data, int id) {
		super(world, x, y, 32, 32, "slot.png");
		this.type = type;
		this.data = data;
		this.id = id;
		if(this.type == 0){
			if(this.data == 0){
				tex = Sword.BLADES[id];
			}else if(this.data == 1){
				tex = Sword.GUARDS[id];
			}else if(this.data == 2){
				tex = Sword.HILTS[id];
			}
		}
	}

	@Override
	public void calc() {
		// TODO Auto-generated method stub

	}
	
	public void draw(SpriteBatch batch){
		batch.draw(tex, x, y, d_width, d_height);
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}

}
