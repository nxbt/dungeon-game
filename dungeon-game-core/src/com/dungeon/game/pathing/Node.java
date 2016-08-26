package com.dungeon.game.pathing;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import com.dungeon.game.world.Tile;

public class Node {
	
	private static int CUR_INDEX = 0;
	private Array<Connection<Node>> connections = new Array<Connection<Node>>();
	public float cost;
	private int index;
	public float x; //x and y of nodes are in tiles not pixels
	public float y;
	
	public Node(float x, float y, float cost){
		index = CUR_INDEX;
		CUR_INDEX++;
		this.cost = cost;
		this.x = x;
		this.y = y;
	}
	
	public void madeConnection(Node end, float cost){
		connections.add(new com.dungeon.game.pathing.Connection(this, end, cost));
	}
	
	public int getIndex(){
		return index;
	}
	
	public Array<Connection<Node>> getConnections(){
		return connections;
	}
	
	public static void resetIndex(){
		CUR_INDEX = 0;
	}
	
	public float findDistance(float x, float y){
		return (float) Math.sqrt((x-this.x*Tile.TS) * (x-this.x*Tile.TS) + (y-this.y*Tile.TS) * (y-this.y*Tile.TS));
	}
	
}
