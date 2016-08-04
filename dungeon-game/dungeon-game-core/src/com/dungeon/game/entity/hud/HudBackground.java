package com.dungeon.game.entity.hud;

import java.util.ArrayList;

import com.dungeon.game.world.World;

public class HudBackground extends Hud {
	ArrayList<Hud> hudEntities;
	
	public HudBackground(World world) {
		super(world, 0, 0, 640, 360, "hud.png");
		addSubEntitiy(new GoldCounter(world), "gold counter", 0, 76);
		addSubEntitiy(new MenuButton(world, 0, 0), "menu button", 4, world.cam.height-20);
		addSubEntitiy(new HelpButton(world, 0, 0), "help button", 24, world.cam.height-20);
		addSubEntitiy(new InvButton(world, 0, 0), "inv counter", world.cam.width-56, 76);
		addSubEntitiy(new PortraitBackground(world, 0, 0), "portrait background", world.cam.width-72, 4);
		addSubEntitiy(new Portrait(world, 0, 0), "portrait", world.cam.width-72, 4);
		addSubEntitiy(new HealthBar(world, 0, 0), "health bar", world.cam.width-36, 76);
		addSubEntitiy(new StamBar(world, 0, 0), "stanima bar", world.cam.width-24, 76);
		addSubEntitiy(new ManaBar(world, 0, 0), "mana bar", world.cam.width-12, 76);
		addSubEntitiy(new HudSlot(world, 0, 0, world.player.inv.slot[30]), "hud slot", world.cam.width-144, 40);
		addSubEntitiy(new HudSlot(world, 0, 0, world.player.inv.slot[31]), "hud slot", world.cam.width-108, 40);
		addSubEntitiy(new HudSlot(world, 0, 0, world.player.inv.slot[0]), "hud slot", world.cam.width-252, 4);
		addSubEntitiy(new HudSlot(world, 0, 0, world.player.inv.slot[1]), "hud slot", world.cam.width-216, 4);
		addSubEntitiy(new HudSlot(world, 0, 0, world.player.inv.slot[2]), "hud slot", world.cam.width-180, 4);
		addSubEntitiy(new HudSlot(world, 0, 0, world.player.inv.slot[3]), "hud slot", world.cam.width-144, 4);
		addSubEntitiy(new HudSlot(world, 0, 0, world.player.inv.slot[4]), "hud slot", world.cam.width-108, 4);
	}

	@Override
	public void calc() {
		super.calc();
	}
	
	public boolean isHovered(){
		for(int i = 0; i < subEntities.size(); i++) if(subEntities.get(i).isHovered())return true;
		return false;
	}

	@Override
	public void post() {

	}

}
