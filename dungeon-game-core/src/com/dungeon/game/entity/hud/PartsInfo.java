package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.Sword;
import com.dungeon.game.item.weapon.Weapon;
import com.dungeon.game.utilities.TextHelper;
import com.dungeon.game.world.World;

public class PartsInfo extends Hud {
	private final NinePatch BACKGROUND = new NinePatch(new Texture("desc_box.png"), 4, 4, 4, 4);
	
	private Weapon weapon;
	
	private PartInfo[] parts;
	
	private BitmapFont font;

	public PartsInfo(World world, float x, float y, Weapon weapon) {
		super(world, x, y, 32, 32, "slot.png");
		d_width = 144;
		d_height = 96;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
		
		this.weapon = weapon;
		
		parts = new PartInfo[3];
		
		if(weapon instanceof Sword){
			parts[0] = new PartInfo(world, 0, 0, 0, 0, ((Sword) weapon).blade);
			parts[1] = new PartInfo(world, 0, 0, 0, 0, ((Sword) weapon).guard);
			parts[2] = new PartInfo(world, 0, 0, 0, 0, ((Sword) weapon).hilt);
		}
		
	}

	@Override
	public void calc() {
		// TODO Auto-generated method stub

	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}
	
	public void draw(SpriteBatch batch) {
			BACKGROUND.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
			font.draw(batch, "Parts: ", TextHelper.alignCenter("Parts: ", x + d_width/2), y + d_height - 4);
	}

}
