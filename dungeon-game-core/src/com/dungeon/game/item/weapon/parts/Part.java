package com.dungeon.game.item.weapon.parts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.Sword;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.utilities.TextHelper;
import com.dungeon.game.world.World;

public class Part implements Cloneable{	
	
	private static final Texture slot = new Texture("slot.png");
	
	public Texture sprite;
	
	//what type of weapon does this part go on?
	public int type;
	
	//types
	public static final int SWORD = 0;
	public static final int AXE = 1;
	public static final int BOW = 2;
	
	//what part of the weapon does it go on?
	public int part;
	
	//parts
	public static final int BLADE = 0;
	public static final int GUARD = 1;
	public static final int HILT = 2;
	
	//what is the unique Id?
	public int id;
	
	//each part has a unique id
	
	//what is the parts name?
	public String name;
	
	//which swings are allowed by this part?
	public String[] allowedSwings;
	
	//which swings are banned by this part?
	public String[] bannedSwings;
	
	//damage multiplier for this part
	public float dmgMult;
	
	//speed multiplier for this part
	public float speedMult;
	
	//knockback multiplier for this part
	public float knockMult;
	
	//stanima multiplier for this part
	public float weightMult;
	
	private BitmapFont font;
	
	public Part(String name, Texture sprite, int type, int part, int id, String[] allowedSwings, String[] bannedSwings, float dmgMult, float speedMult, float knockMult, float weightMult){
		this.name = name;
		this.sprite = sprite;
		this.type = type;
		this.part = part;
		this.id = id;
		this.allowedSwings = allowedSwings;
		this.bannedSwings = bannedSwings;
		this.dmgMult = dmgMult;
		this.speedMult = speedMult;
		this.knockMult = knockMult;
		this.weightMult = weightMult;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
		
	}
	
	public void hovered(World world){
		String text = "";
		if(type == SWORD){
			if(part == BLADE){
				text = Sword.BLADE_NAMES[id];
			}else if(part == GUARD){
				text = Sword.GUARD_NAMES[id];
			}else if(part == HILT){
				text = Sword.HILT_NAMES[id];
			}
		}
		world.descBox.updateText(text);
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		String text = "";
		if(type == SWORD){
			if(part == BLADE) {
				text = "Blade:";
			}
			else if(part == GUARD) {
				text = "Guard:";
			}
			else if(part == HILT) {
				text = "Hilt:";
			}

			font.draw(batch, text, x - 60, y + 22);
			batch.draw(slot, x, y, 32, 32);
			batch.draw(sprite, x, y, 32, 32);
			font.draw(batch, name, TextHelper.alignRight(name, x + 183), y + 22);
		}
	}
	
	public Part clone() {
		try {
			Part newPart = (Part) super.clone();
			newPart.allowedSwings = new String[allowedSwings.length];
			for(int i = 0; i < allowedSwings.length; i++)newPart.allowedSwings[i] = allowedSwings[i];
			newPart.bannedSwings = new String[bannedSwings.length];
			for(int i = 0; i < bannedSwings.length; i++)newPart.bannedSwings[i] = bannedSwings[i];
			return newPart;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static final Texture[] SWORD_BLADE_SPIRTES = Spritesheet.getSprites("swordBladeMap.png", 32, 32);
	public static final Texture[] SWORD_GUARD_SPIRTES = Spritesheet.getSprites("swordGuardMap.png", 32, 32);
	public static final Texture[] SWORD_HILT_SPIRTES = Spritesheet.getSprites("swordHiltMap.png", 32, 32); //why is guard spelled ua and not au??

	public static final int SWORD_BLADE_NUM = 5;
	public static final int SWORD_GUARD_NUM = 3;
	public static final int SWORD_HILT_NUM = 3;
	
	public static final Part[] SWORD_BLADES = new Part[]{
			new Part("Basic Blade", SWORD_BLADE_SPIRTES[0], SWORD, BLADE, 0, new String[]{}, new String[]{}, 1, 1, 1, 1),
			new Part("Light Sword", SWORD_BLADE_SPIRTES[1], SWORD, BLADE, 1, new String[]{}, new String[]{}, 1, 1, 1, 1),
			new Part("Broad Sword", SWORD_BLADE_SPIRTES[2], SWORD, BLADE, 2, new String[]{}, new String[]{}, 1, 1, 1, 1),
			new Part("Scimitar",    SWORD_BLADE_SPIRTES[3], SWORD, BLADE, 3, new String[]{}, new String[]{}, 1, 1, 1, 1),
			new Part("Needle",      SWORD_BLADE_SPIRTES[4], SWORD, BLADE, 4, new String[]{}, new String[]{}, 1, 1, 1, 1),
	};
	
	public static final Part[] SWORD_GUARDS = new Part[]{
			new Part("Defender's Guard", SWORD_GUARD_SPIRTES[0], SWORD, GUARD, 0, new String[]{}, new String[]{}, 1, 1, 1, 1),
			new Part("Squared Gaurd",    SWORD_GUARD_SPIRTES[1], SWORD, GUARD, 1, new String[]{}, new String[]{}, 1, 1, 1, 1),
			new Part("Spiked Guard",     SWORD_GUARD_SPIRTES[2], SWORD, GUARD, 2, new String[]{}, new String[]{}, 1, 1, 1, 1),

	};

	public static final Part[] SWORD_HILTS = new Part[]{
			new Part("Utilitarian Hilt", SWORD_HILT_SPIRTES[0], SWORD, HILT, 0, new String[]{}, new String[]{}, 1, 1, 1, 1),
			new Part("Square Hilt",      SWORD_HILT_SPIRTES[1], SWORD, HILT, 1, new String[]{}, new String[]{}, 1, 1, 1, 1),
			new Part("Spiked Hilt",      SWORD_HILT_SPIRTES[2], SWORD, HILT, 2, new String[]{}, new String[]{}, 1, 1, 1, 1),

	};

}
