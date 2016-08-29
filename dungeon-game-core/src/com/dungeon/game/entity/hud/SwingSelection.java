package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.equipable.weapon.Melee;
import com.dungeon.game.item.equipable.weapon.swing.Rest;
import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class SwingSelection extends Hud {
	private final NinePatch BACKGROUND = new NinePatch(new Texture("desc_box.png"), 4, 4, 4, 4);
	
	private Melee weapon;
	public String[] allowedSwings;
	public String[] curSwings;
	public SwingGraphic selectedGraphic;

	public SwingSelection(World world, float x, float y, Melee weapon) {
		super(world, x, y, 32, 32, "slot.png");
		this.weapon = weapon;
		allowedSwings = weapon.getAllowedSwings();
		curSwings = new String[weapon.swings.swings.length-1];
		for(int i = 1; i < weapon.swings.swings.length; i++){
			curSwings[i-1] = weapon.swings.swings[i].getClass().getSimpleName();
		}
		dWidth = 100;
		dHeight = 212;
		for(int i = 0; i < allowedSwings.length; i++){
			try {
				addSubEntitiy(new SwingGraphic(world, 0, 0, (Swing) weapon.swingClass.getDeclaredMethod("getSwingByName", new Class<?>[]{World.class, String.class}).invoke(null, world, allowedSwings[i]), true), "Allowed Swing", 4, (allowedSwings.length-1)*18 - 18 * i + 4);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(int i = 0; i < curSwings.length; i++){
			try {
				addSubEntitiy(new SwingGraphic(world, 0, 0, (Swing) weapon.swingClass.getDeclaredMethod("getSwingByName", new Class<?>[]{World.class, String.class}).invoke(null, world, curSwings[i]), false), "Selected Swing", 4, dHeight - 18 * i - 22);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	public void calc(){
		for(Hud h: subEntities){
			if(h instanceof SwingGraphic){
				if(h.isHovered() && world.mouse.lb_pressed){
					if(selectedGraphic == null){
						selectedGraphic = (SwingGraphic) h;
					}else{
						((SwingGraphic) h).Swap(selectedGraphic);
						selectedGraphic = null;
					}
				}
			}
		}
		super.calc();
	}

	@Override
	public void post() {
		
	}
	
	public void draw(SpriteBatch batch){
		BACKGROUND.draw(batch, x, y, dWidth, dHeight);
		//draw the subEntities!
		for(int i = 0; i < subEntities.size(); i++){
			subEntities.get(i).draw(batch);
		}
	}

	public void setSwings() { //set the swings to the sword
		Hud[] swingGraphics = getSubEntity("Selected Swing");
		Swing[] newSwings = new Swing[swingGraphics.length+1];
		newSwings[0] = new Rest(world);
		for(int i = 0; i < swingGraphics.length; i++){
			try {
				newSwings[i+1] = (Swing) weapon.swingClass.getDeclaredMethod("getSwingByName", new Class<?>[]{World.class, String.class}).invoke(null, world, ((SwingGraphic)swingGraphics[i]).swing.getClass().getSimpleName());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		weapon.swings.setSwings(newSwings);
		
	}

}
