package com.dungeon.game.utilities;

import java.util.ArrayList;

public abstract class Pool<N> {

	private ArrayList<N> pool;
	
	public int total;
	
	public Pool(){
		pool = new ArrayList<N>();
	}
	
	public Pool(int num){
		pool = new ArrayList<N>();
		for(int i = 0; i < num; i++)pool.add(getNew());
	}
	
	public N get(){
		System.out.println(pool.size());
		if(pool.size() > 0)return pool.remove(0);
		else {
			total++;
			return getNew();
		}
	}
	
	public void dispose(N n){
		pool.add(n);
	}
	
	public abstract N getNew();
}
