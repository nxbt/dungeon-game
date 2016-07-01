package com.dungeon.game.item.weapon;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.MeleeGraphic;
import com.dungeon.game.item.weapon.swing.Rest;
import com.dungeon.game.item.weapon.swing.Swing;
import com.dungeon.game.item.weapon.swing.SwingSet;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public class Sword extends Melee {
	
	public static final Texture[] BLADES = Spritesheet.getSprites("swordBladeMap.png", 32, 32);
	public static final Texture[] GUARDS = Spritesheet.getSprites("swordGuardMap.png", 32, 32);
	public static final Texture[] HILTS = Spritesheet.getSprites("swordHiltMap.png", 32, 32); //why is guard spelled ua and not au??
	private static final int BLADE_NUM = 5;
	private static final int GUARD_NUM = 3;
	private static final int HILT_NUM = 3;
	// how should we change name and stats based on the blade guard and hilt? Arrays? Text Files? Something Else? I'll do this for now
	private static final String[] BLADE_NAMES = new String[]{"Sword", "Light Sword", "Broad Sword", "Cutlass", "Needle"};
	//blade stat order is dmg, speed, knockback, stanima
	private static final float[][] BLADE_STATS = new float[][]{
		new float[]{1,1,1,1}, 
		new float[]{0.8f,1.3f,0.6f,0.7f},
		new float[]{1.2f,0.7f,1.3f, 1.3f},
		new float[]{1.5f,0.9f,0.4f, 0.9f},
		new float[]{0.6f,2f,0.3f, 0.3f}
		};
		
	private static final float[][] GUARD_STATS = new float[][]{
		new float[]{1,1,1,1},
		new float[]{1,1.05f,0.9f,1.05f},
		new float[]{1,0.95f,1.05f,1.1f}
		};
		
	private static final float[][] HILT_STATS = new float[][]{
		new float[]{1,1,1,1},
		new float[]{1,1,1.1f,1.1f},
		new float[]{1.05f,0.95f,0.9f,1.05f}
		};
	
	private final SwingSet[] BLADE_SWINGS = new SwingSet[]{
			new SwingSet(world, this, new Swing[]{new Rest(world, 20, 14, 82, -10), //sword
					new Swing(world, false, 10, 24, 70, 35, 8, 14, -55, -50, 0.7f, 1, -90, 0.4f, 1), 
					new Swing(world, false, 10, 16, -75, -80, 10, 20, 80, 45, 1, 1.3f, 90, 0.4f, 1),
					new Swing(world, false, 15, 12, 30, -7, 4, 28, 6, -3, 1.5f, 0.7f, 0, 0.4f, 0.8f)
					} ,false),
			new SwingSet(world, this, new Swing[]{new Rest(world, 20, 14, 82, -10), //light sword
					new Swing(world, false, 10, 24, 70, 35, 8, 14, -55, -50, 0.7f, 1, -90, 0.4f, 1), 
					new Swing(world, false, 10, 16, -75, -80, 10, 20, 80, 45, 1, 1.3f, 90, 0.4f, 1),
					new Swing(world, false, 15, 12, 30, -7, 4, 28, 6, -3, 1.5f, 0.7f, 0, 0.4f, 0.8f)
					} ,false),
			new SwingSet(world, this, new Swing[]{new Rest(world, 20, 14, 82, -10), //broad sword
					new Swing(world, false, 10, 24, 70, 35, 8, 14, -55, -50, 0.7f, 1, -90, 0.4f, 1), 
					new Swing(world, false, 10, 16, -75, -80, 10, 20, 80, 45, 1, 1.3f, 90, 0.4f, 1),
					new Swing(world, false, 15, 12, 30, -7, 4, 28, 6, -3, 1.5f, 0.7f, 0, 0.4f, 0.8f)
					} ,false),
			new SwingSet(world, this, new Swing[]{new Rest(world, 20, 14, 82, -10), //cutlass
					new Swing(world, false, 10, 24, 70, 35, 8, 14, -55, -50, 0.7f, 1, -90, 0.4f, 1), 
					new Swing(world, false, 10, 16, -75, -80, 10, 20, 80, 45, 1, 1.3f, 90, 0.4f, 1),
					} ,true),
			new SwingSet(world, this, new Swing[]{new Rest(world, 20, 14, 82, -10), //needle
					new Swing(world, false, 15, 12, 40, -7, 4, 28, 12, -3, 0.75f, 0.7f, 0, 0.4f, 0.5f),
					new Swing(world, false, 15, 12, 0, 0, 4, 28, 0, 0, 1.5f, 0.7f, 0, 0.4f, 1f),
					new Swing(world, false, 15, 12, -40, 7, 4, 28, -12, 3, 0.75f, 0.7f, 0, 0.4f, 1.5f)
					} , true)
	};
	
	private final Swing[][] GUARD_SWINGS = new Swing[][]{
		new Swing[]{},
		new Swing[]{},
		new Swing[]{new Swing(world, false, 10, 10, 20, -90, 10, 26, 15, -90, 0.5f, 1.5f, 90, 0, 2)}
	};
	
	private final Swing[][] HILT_SWINGS = new Swing[][]{
		new Swing[]{},
		new Swing[]{},
		new Swing[]{new Swing(world, false, 10, 15, 70, 150, 10, 26, 0, 90, 2f, 0.6f, 180, 0, 2)}
	};
	
	protected float[] dmgMult;
	protected float[] knockMult;
	
	public int blade;
	public int guard;
	public int hilt;
	
	public Sword(World world, float damage, float speed, float weight) {
		super(world, "sword.png");
		
		//generate the sprite, for now random, but in the future will be a parameter!
		blade = (int) (Math.random()*BLADE_NUM);
		guard = (int) (Math.random()*GUARD_NUM);
		hilt = (int) (Math.random()*HILT_NUM);
		if(!BLADES[blade].getTextureData().isPrepared()) BLADES[blade].getTextureData().prepare();
		Pixmap bladeMap = BLADES[blade].getTextureData().consumePixmap();
		if(!GUARDS[guard].getTextureData().isPrepared()) GUARDS[guard].getTextureData().prepare();
		Pixmap guardMap = GUARDS[guard].getTextureData().consumePixmap();
		if(!HILTS[hilt].getTextureData().isPrepared()) HILTS[hilt].getTextureData().prepare();
		Pixmap hiltMap = HILTS[hilt].getTextureData().consumePixmap();
		
		Pixmap spr = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		spr.drawPixmap(bladeMap, 0, 0);
		spr.drawPixmap(hiltMap, 0, 0);
		spr.drawPixmap(guardMap, 0, 0);
		sprite = new Texture(spr);
		spr.dispose();
		//for some reason all the pixmaps are all disposed already... thx libgdx!
		
		
		name = BLADE_NAMES[blade];
		
		hasHit = false;
		
		this.damage = damage * BLADE_STATS[blade][0] * GUARD_STATS[guard][0] * HILT_STATS[hilt][0];
		this.speed = speed * BLADE_STATS[blade][1] * GUARD_STATS[guard][1] * HILT_STATS[hilt][1];
		
		knockratio = 0.4f;
		knockstr = 10 * BLADE_STATS[blade][2]* GUARD_STATS[guard][2] * HILT_STATS[hilt][2];	
		
		this.weight = weight * BLADE_STATS[blade][3]* GUARD_STATS[guard][3] * HILT_STATS[hilt][3];	
		
		desc = "An incredibly reliable melee weapon.\n\n Damage: "+ Math.floor(this.damage*10)/10f + "\n Speed: "+ Math.floor(this.speed*10)/10f + "\n Knockback: "+ Math.floor(this.knockstr*10)/10f;

		
		dmgMult = new float[]{0.7f,1,1.5f};
		knockMult = new float[]{1,1.3f,0.7f};	
		
		graphic = new MeleeGraphic(world, this, new Polygon(new float[]{24,6,26,8,2,32,0,32,0,30}), 30, 2);
		
		swings = BLADE_SWINGS[blade]; // do we have to clone it? I guess not
		for(int i = 0; i < GUARD_SWINGS[guard].length; i++){
			swings.addSwing(GUARD_SWINGS[guard][i]);
		}
		for(int i = 0; i < HILT_SWINGS[hilt].length; i++){
			swings.addSwing(HILT_SWINGS[hilt][i]);
		}
		swings.setWorld(world); //is this neccicary? (I think so)
		
		hitEffects.add(new Stun(world, 30));
	}
	
	public float[] getPos(boolean mousedown, boolean mousepressed){
		swings.progressWeapon();
		
		return new float[]{distance,polarAngle,angle};
	}
	
	

	@Override
	public boolean isInUse() {
		return swings.isInUse;
	}

	public boolean inAttack() {
		return swings.isInAttack;
	}
	
	public void hit(Character c) {
		swings.hit(c);
	}
	
	public String getDesc() {
		return "The accepted standard for all melee weapons, and the standard for all sword class melee weapons. Using it will swing it in a short combo of swings, damaging the first target it comes in contact with. For this particular sword, the combo begins with a fronthand swing across the body, followed by a backhand swing, and ends with a jab.\n\n\"My sword shall lead me to glory!\" -final words of Tanturin, fabled warrior\n\n Damage: "+ Math.floor(damage*10)/10f  + "\n Speed: "+ Math.floor(this.speed*10)/10f + "\n Knockback: "+ Math.floor(this.knockstr*10)/10f;
	}

	@Override
	public void reset() {
		swings.reset();
	}
}
