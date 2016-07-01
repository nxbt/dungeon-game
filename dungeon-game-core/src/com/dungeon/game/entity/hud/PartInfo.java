package com.dungeon.game.entity.hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.Sword;
import com.dungeon.game.world.World;

public class PartInfo extends Hud {

	public static final int SWORD = 0;
	
	protected int type;
	
	public static final int BLADE = 0;
	public static final int GUARD = 1;
	public static final int HILT = 2;
	
	protected int data;
	
	protected int id;
	
	private Texture tex;
	
	private BitmapFont font;

	public PartInfo(World world, float x, float y, int type, int data, int id) {
		super(world, x, y, 32, 32, "slot.png");
		this.type = type;
		this.data = data;
		this.id = id;
		if(this.type == 0){
			if(this.data == 0){
				tex = Sword.BLADES[id];
				origin_x = 14;
				origin_y = 18;
			}else if(this.data == 1){
				tex = Sword.GUARDS[id];
				origin_x = 26;
				origin_y = 6;
			}else if(this.data == 2){
				tex = Sword.HILTS[id];
				origin_x = 30;
				origin_y = 2;
			}
		}
		
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
	}

	@Override
	public void calc() {
		// TODO Auto-generated method stub

	}
	
	public void draw(SpriteBatch batch){
		if(type == SWORD){
			if(data == BLADE){
				font.draw(batch, "Blade:", x, y);
				batch.draw(tex, x + 70 - origin_x, y - origin_y - 6, d_width, d_height);
			}else if(data == GUARD){
				font.draw(batch, "Guard:", x, y);
				batch.draw(tex, x + 70 - origin_x, y - origin_y - 6, d_width, d_height);
			}else if(data == HILT){
				font.draw(batch, "Hilt:", x, y);
				batch.draw(tex, x + 70 - origin_x, y - origin_y - 6, d_width, d_height);
			}
		}
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}

}
