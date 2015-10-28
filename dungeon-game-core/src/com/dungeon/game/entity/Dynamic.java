package com.dungeon.game.entity;

//abstract class for dynamic entities, or entities that move and respond to physics.
public abstract class Dynamic extends Entity {
	private float dx;
	private float dy;
	
	private double acel;
	private double fric;
	private double mvel;
	
	private boolean inp_up;
	private boolean inp_dn;
	private boolean inp_lt;
	private boolean inp_rt;
	
	public Dynamic() {
		super();
	}
	
	public Dynamic(int x, int y) {
		super(x, y);
	}
	
	public Dynamic(String name, int x, int y) {
		super(name, x, y);
	}

	public Dynamic(String name, int x, int y, boolean solid) {
		super(name, x, y, solid);
	}
	
	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public double getAcel() {
		return acel;
	}

	public void setAcel(double acel) {
		this.acel = acel;
	}

	public double getFric() {
		return fric;
	}

	public void setFric(double fric) {
		this.fric = fric;
	}

	public double getMvel() {
		return mvel;
	}

	public void setMvel(double mvel) {
		this.mvel = mvel;
	}
	
	public boolean isInp_up() {
		return inp_up;
	}

	public void setInp_up(boolean inp_up) {
		this.inp_up = inp_up;
	}

	public boolean isInp_dn() {
		return inp_dn;
	}

	public void setInp_dn(boolean inp_dn) {
		this.inp_dn = inp_dn;
	}

	public boolean isInp_lt() {
		return inp_lt;
	}

	public void setInp_lt(boolean inp_lt) {
		this.inp_lt = inp_lt;
	}

	public boolean isInp_rt() {
		return inp_rt;
	}

	public void setInp_rt(boolean inp_rt) {
		this.inp_rt = inp_rt;
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
		
		setX(getX()+(int)dx);
		setY(getY()+(int)dy);
	}
}















