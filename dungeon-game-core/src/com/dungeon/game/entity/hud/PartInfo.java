package com.dungeon.game.entity.hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.Sword;
import com.dungeon.game.utilities.TextHelper;
import com.dungeon.game.world.World;

public class PartInfo extends Hud {
	
	//there is definately a better way do do this shit! more classes i think. A part class?
	
	private static final Texture slot = new Texture("slot.png");

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
			}else if(this.data == 1){
				tex = Sword.GUARDS[id];
			}else if(this.data == 2){
				tex = Sword.HILTS[id];
			}
		}
		
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
	}

	@Override
	public void calc() {
		// TODO Auto-generated method stub

	}
	
	public void hovered(){
		String text = "";
		if(type == SWORD){
			if(data == BLADE){
				text = Sword.BLADE_NAMES[id];
			}else if(data == GUARD){
				text = Sword.GUARD_NAMES[id];
			}else if(data == HILT){
				text = Sword.HILT_NAMES[id];
			}
		}
		world.descBox.updateText(text);
	}
	
	public void draw(SpriteBatch batch){
		if(type == SWORD){
			if(data == BLADE){
				font.draw(batch, "Blade:", x - 60, y + 22);
				batch.draw(slot, x, y, d_width, d_width);
				batch.draw(tex, x, y, d_width, d_height);
				font.draw(batch, Sword.BLADE_NAMES[id], TextHelper.alignRight(Sword.BLADE_NAMES[id], x + 183), y + 22);
			}else if(data == GUARD){
				font.draw(batch, "Guard:", x - 60, y + 22);
				batch.draw(slot, x, y, d_width, d_width);
				batch.draw(tex, x, y, d_width, d_height);
				font.draw(batch, Sword.GUARD_NAMES[id], TextHelper.alignRight(Sword.GUARD_NAMES[id], x + 183), y + 22);
			}else if(data == HILT){
				font.draw(batch, "Hilt:", x - 60, y + 22);
				batch.draw(slot, x, y, d_width, d_width);
				batch.draw(tex, x, y, d_width, d_height);
				font.draw(batch, Sword.HILT_NAMES[id], TextHelper.alignRight(Sword.HILT_NAMES[id], x + 183), y + 22);
			}
		}
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}

}
