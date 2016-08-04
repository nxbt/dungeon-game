package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class SwingGraphic extends Hud {
	private final NinePatch BACKGROUND = new NinePatch(new Texture("desc_box.png"), 4, 4, 4, 4);
	
	public Swing swing;
	public boolean readOnly;
	
	private BitmapFont font;

	public SwingGraphic(World world, float x, float y, Swing swing, boolean readOnly) {
		super(world, x, y, 32, 32, "slot.png");
		this.swing = swing;
		this.readOnly = readOnly;
		this.d_width = 92;
		this.d_height = 16;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
		
	}
	
	public void Swap(SwingGraphic g){
		Swing temp = swing;
		if(!readOnly)this.swing = g.swing;
		if(!g.readOnly)g.swing = temp;
	}

	@Override
	public void post() {
	}
	
	public void draw(SpriteBatch batch){
		BACKGROUND.draw(batch, x, y, d_width, d_height);
		font.draw(batch, swing.name, x + 4, y + 14);
	}

}
