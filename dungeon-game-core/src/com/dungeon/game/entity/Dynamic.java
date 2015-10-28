package com.dungeon.game.entity;

//abstract class for dynamic entities, or entities that move and respond to physics.
public abstract class Dynamic extends Entity {
	public float dx;
	public float dy;
	
	double acel;
	double fric;
	double mvel;
	
	public boolean inp_up;
	public boolean inp_dn;
	public boolean inp_lt;
	public boolean inp_rt;
	
	public Dynamic() {
		super();
	}
	
	public Dynamic(int x, int y) {
		super(x, y);
	}
	
	public Dynamic(String name, int x, int y) {
		super(name, x, y);
	}

	//entity update function; called on every frame; before the draw phase.
	@Override
	public void update() {
		norm();
		calc();
		phys();
	}
	
	//resets some variables at the start of every update cycles
	public void norm() {
		inp_up = false;
		inp_dn = false;
		inp_lt = false;
		inp_rt = false;
	}
	
	//calculates velocity and collisions for object
	public void phys() {
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
		else if(dx != 0 || dy != 0){
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
	}
}















