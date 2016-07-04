package com.dungeon.game.item.weapon.part;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.weapon.Weapon;
import com.dungeon.game.item.weapon.part.sword.SwordPart;
import com.dungeon.game.item.weapon.part.sword.blade.BasicBlade;
import com.dungeon.game.item.weapon.part.sword.guard.BasicGuard;
import com.dungeon.game.item.weapon.part.sword.hilt.BasicHilt;
import com.dungeon.game.world.World;

public abstract class Part extends Item implements Cloneable{	
	
	protected static final Texture slot = new Texture("slot.png");

	public static int NUM;
	public static Texture[] SPRITES;
	
	//what type of weapon does this part go on?
	public int type;
	
	//types
	public static final int SWORD = 0;
	public static final int AXE = 1;
	public static final int BOW = 2;
	
	//what part of the weapon does it go on?
	public int part;
	
	//what is the unique Id?
	public int id;
	
	//each part has a unique id
	
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
	
	protected BitmapFont font;
	
	public interface PartGetter {
		void constructor();
	}
	
	public static PartGetter[] parts;
	
	public Part(String name, Texture sprite) {
		super(null, "slot.png");
		
		this.sprite = sprite;
		this.name = name;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
	}

	public void hovered(){
		world.descBox.updateText(getDesc());
	}
	
	public String getDesc(){
		return name + "\n Damage Multiplier: " + dmgMult + "\n Speed Multiplier: " + speedMult + "\n Knockback Multiplier: " + knockMult + "\n Weight Multiplier: " + weightMult;
	}
	
	public abstract void draw(SpriteBatch batch, float x, float y);
	
	public Part clone(World world) {
		Part newPart = (Part) super.clone();
		newPart.allowedSwings = new String[allowedSwings.length];
		newPart.world = world;
		for(int i = 0; i < allowedSwings.length; i++)newPart.allowedSwings[i] = allowedSwings[i];
		newPart.bannedSwings = new String[bannedSwings.length];
		for(int i = 0; i < bannedSwings.length; i++)newPart.bannedSwings[i] = bannedSwings[i];
		return newPart;
	}
	
	public void begin(Weapon weapon){}; // called when the part is added to the weapon used to add passiveEffects to the weapon
	
	public ArrayList<Effect> getEffects(){ //get the effects of this part
		return new ArrayList<Effect>();
	};
	
	public static final Part[] SWORD_BLADES = new Part[]{
			new BasicBlade()
//			new Part("Straight Sword", SWORD_BLADE_SPIRTES[0], SWORD, BLADE, 0, new String[]{}, new String[]{}, 1, 1, 1, 1),
//			new Part("Light Sword", SWORD_BLADE_SPIRTES[1], SWORD, BLADE, 1, new String[]{}, new String[]{}, 0.8f,1.3f,0.6f,0.7f),
//			new Part("Broad Sword", SWORD_BLADE_SPIRTES[2], SWORD, BLADE, 2, new String[]{}, new String[]{}, 1.2f,0.7f,1.3f, 1.3f),
//			new Part("Scimitar",    SWORD_BLADE_SPIRTES[3], SWORD, BLADE, 3, new String[]{}, new String[]{}, 1.5f,0.9f,0.4f, 0.9f),
//			new Part("Rapier",      SWORD_BLADE_SPIRTES[4], SWORD, BLADE, 4, new String[]{}, new String[]{}, 0.6f,2f,0.3f, 0.3f),
	};
	
	public static final Part[] SWORD_GUARDS = new Part[]{
			new BasicGuard()
//			new Part("Defender's Guard", SWORD_GUARD_SPIRTES[0], SWORD, GUARD, 0, new String[]{}, new String[]{}, 1, 1, 1, 1),
//			new Part("Squared Gaurd",    SWORD_GUARD_SPIRTES[1], SWORD, GUARD, 1, new String[]{}, new String[]{}, 1,1.05f,0.9f,1.05f),
//			new Part("Spiked Guard",     SWORD_GUARD_SPIRTES[2], SWORD, GUARD, 2, new String[]{}, new String[]{}, 1,0.95f,1.05f,1.1f),

	};

	public static final Part[] SWORD_HILTS = new Part[]{
			new BasicHilt()
//			new Part("Utilitarian Hilt", SWORD_HILT_SPIRTES[0], SWORD, HILT, 0, new String[]{}, new String[]{}, 1, 1, 1, 1),
//			new Part("Square Hilt",      SWORD_HILT_SPIRTES[1], SWORD, HILT, 1, new String[]{}, new String[]{}, 1,1,1.1f,1.1f),
//			new Part("Spiked Hilt",      SWORD_HILT_SPIRTES[2], SWORD, HILT, 2, new String[]{}, new String[]{}, 1.05f,0.95f,0.9f,1.05f),

	};

}
