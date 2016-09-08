package com.dungeon.game.world;

public class Tile implements Cloneable{
	public static final int TS = 32;
	public static final float PPM = 8f; // pixels per meter
	public int id;
	public int data;
	public static final int[] SOLIDS = new int[]{1,10,11,12,13,14,15};
	
	public Tile(int id) {
		this.id = id;
		data = (isSolid(id)) ? 1:0;
	}
	
	public static boolean isSolid(int id){
		for(int i = 0; i < SOLIDS.length; i++) if(SOLIDS[i] == id) return true;
		return false;
	}
	
	public static boolean isSolid(Tile tile){
		return isSolid(tile.id);
	}
	
	public Tile clone(){
		try {
			return (Tile) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
