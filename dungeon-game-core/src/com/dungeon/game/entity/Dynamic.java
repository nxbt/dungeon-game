package com.dungeon.game.entity;

import com.dungeon.game.world.*;

//abstract class for dynamic entities, or entities that move and respond to physics.
public abstract class Dynamic extends Entity {
	public float dx;
	public float dy;
	
	public float width;
	public float height;
	
	double acel;
	double fric;
	double mvel;
	
	public boolean inp_up;
	public boolean inp_dn;
	public boolean inp_lt;
	public boolean inp_rt;
	
	public Dynamic(String name, int x, int y) {
		super(name, x, y);
	}

	//entity update function; called on every frame; before the draw phase.
	@Override
	public void update(Floor floor) {
		norm();
		calc(floor);
		phys(floor);
	}
	
	//resets some variables at the start of every update cycles
	public void norm() {
		inp_up = false;
		inp_dn = false;
		inp_lt = false;
		inp_rt = false;
	}
	
	//calculates velocity and collisions for object
	public void phys(Floor floor) {
		double dirX = 0;
		double dirY = 0;
		
		if(inp_up) dirY++;
		if(inp_dn) dirY--;
		if(inp_rt) dirX++;
		if(inp_lt) dirX--;
		
		double len = Math.sqrt(dirX * dirX + dirY * dirY);
		
		if(len != 0) {
			dx += dirX/len*acel;
			dy += dirY/len*acel;
			
			len = Math.sqrt(dx * dx + dy * dy);
			
			if(len > mvel) {
				dx = (float) (dx/len*mvel);
				dy = (float) (dy/len*mvel);
			}
		}
		if(dx != 0 || dy != 0){
			len = Math.sqrt(dx * dx + dy * dy);
			
			if(len < fric) {
				dx = 0;
				dy = 0;
			}
			
			dx -= dx/len*fric;
			dy -= dy/len*fric;
		}
		
		x += dx;
		y += dy;
		
		int tile_lt = (int) (x/Tile.TS);
		int tile_dn = (int) (y/Tile.TS);
		int tile_rt = (int) ((x+width)/Tile.TS);
		int tile_up = (int) ((y+height)/Tile.TS);
		
		boolean dl = floor.tm[tile_dn][tile_lt].data == 1;
		boolean dr = floor.tm[tile_dn][tile_rt].data == 1;
		boolean ul = floor.tm[tile_up][tile_lt].data == 1;
		boolean ur = floor.tm[tile_up][tile_rt].data == 1;
		
		if(dl && dr) {
			y = (tile_dn+1) * Tile.TS;
			dy = 0;
			dl = false;
			dr = false;
		}
		if(ul && ur) {
			y = (tile_up * Tile.TS)-height;
			dy = 0;
			ul = false;
			ur = false;
		}
		if(ul && dl) {
			x = (tile_lt+1) * Tile.TS;
			dx = 0;
			dl = false;
			ul = false;
		}
		if(ur && dr) {
			x = (tile_rt * Tile.TS)-width;
			dx = 0;
			ur = false;
			dr = false;
		}
		if(dl) {
			if(this.x - (tile_lt*Tile.TS) > this.y - (tile_dn*Tile.TS)) {
				x = (tile_lt+1) * Tile.TS;
				dx = 0;
			}
			else {
				y = (tile_dn+1) * Tile.TS;
				dy = 0;
			}
			dl = false;
		}
		if(dr) {
			if(-(this.x - (tile_rt*Tile.TS)) > this.y - (tile_dn*Tile.TS)) {
				x = (tile_rt * Tile.TS)-width;
				dx = 0;
			}
			else {
				y = (tile_dn+1) * Tile.TS;
				dy = 0;
			}
			dr = false;
		}
		if(ul) {
			if(this.x - (tile_lt*Tile.TS) > -(this.y - (tile_up*Tile.TS))) {
				x = (tile_lt+1) * Tile.TS;
				dx = 0;
			}
			else {
				y = (tile_up * Tile.TS)-height;
				dy = 0;
			}
			ul = false;
		}
		if(ur) {
			if(this.x - (tile_rt*Tile.TS) < this.y - (tile_up*Tile.TS)) {
				x = (tile_rt * Tile.TS)-width;
				dx = 0;
			}
			else {
				y = (tile_up * Tile.TS)-height;
				dy = 0;
			}
			ur = false;
		}
	}
}















