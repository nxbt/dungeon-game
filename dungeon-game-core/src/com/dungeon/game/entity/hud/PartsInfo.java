package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.Sword;
import com.dungeon.game.item.weapon.Weapon;
import com.dungeon.game.item.weapon.parts.Part;
import com.dungeon.game.utilities.TextHelper;
import com.dungeon.game.world.World;

public class PartsInfo extends Hud {
	private final NinePatch BACKGROUND = new NinePatch(new Texture("desc_box.png"), 4, 4, 4, 4);
	
	private Weapon weapon;
	
	private Part[] parts;
	
	private BitmapFont font;

	public PartsInfo(World world, float x, float y, Weapon weapon) {
		super(world, x, y, 32, 32, "slot.png");
		d_width = 140;
		d_height = 116;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
		
		this.weapon = weapon;
		
		parts = new Part[3];
		
		if(weapon instanceof Sword){
			parts[0] = ((Sword) weapon).blade;
			parts[1] = ((Sword) weapon).guard;
			parts[2] = ((Sword) weapon).hilt;
		}
		
	}

	@Override
	public void calc() {
		super.calc();
	}
	
	public void hovered(){
		super.hovered();
		for(int i = 0; i < parts.length; i++){
			if(world.mouse.x > x + 76 && world.mouse.x < x + 76 + 32 && world.mouse.y > y + d_height - 50 - i * 32 && world.mouse.y < y + d_height - 50 - i * 32 + 32){
				parts[i].hovered();

				break;
			}
		}
	}

	@Override
	public void post() {
	}
	
	public void draw(SpriteBatch batch) {
			BACKGROUND.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
			font.draw(batch, "Parts: ", TextHelper.alignCenter("Parts: ", x + d_width/2), y + d_height - 4);
			for(int i = 0; i < parts.length; i++){
				parts[i].draw(batch, x + 76, y + d_height - 50 - i * 32);
			}
	}

}
