package com.dungeon.game.spell;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.effect.Tangle;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.SpellProjectile;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.equipable.weapon.Medium;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class TangleVines extends Spell {

	public TangleVines(World world) {
		super(world);
		this.sprite = new Texture("vine.png");
		mana = 5;
		cooldown = 120;
	}

	@Override
	public void cast(float x, float y, float angle, Medium medium) {
		float xOff = (float) (Math.cos((angle+135)/180*Math.PI)*30);

		float yOff = (float) (Math.sin((angle+135)/180*Math.PI)*30);
		
		SpellProjectile vines = new SpellProjectile(world, medium, this, (x+xOff), (y+yOff), angle, 4, new Polygon(new float[]{0,28,4,28,4,32,0,32}), 0, 32, 70, Item.SIZE, Item.SIZE, "vine.png");
		
		vines.fric = 0;
		
		vines.getBody(world.curFloor.box2dWorld);
		vines.bodyMade = true;

		
		Vector2 acelVec = new Vector2();
		
		acelVec.x = (float) Math.cos((angle+135)/180*Math.PI)*6/Tile.PPM;
		acelVec.y = (float) Math.sin((angle+135)/180*Math.PI)*6/Tile.PPM;
		vines.body.setLinearVelocity(acelVec);
		vines.prevAngle = acelVec.angle();
		
		world.entities.add(vines);
		
	}

	@Override
	public void hit(Character character, Medium weapon, SpellProjectile projectile) {
		character.addEffect(new Tangle(world, 300));
		character.addEffect(new Stun(world, 5));
		if(!character.knownEntities.contains(projectile.owner))character.knownEntities.add(projectile.owner);
		
	}

}