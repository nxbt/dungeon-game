package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import com.dungeon.game.world.Tile;

public class Node {
	
	private static int[] CUR_INDICES;
	private Array<Connection<Node>> connections = new Array<Connection<Node>>();
	public float cost;
	private int index;
	public float x; //x and y of nodes are in tiles not pixels
	public float y;
	public Node upNode;
	public Node downNode;
	public ArrayList<Node> downNodes;
	public String name;
	
	public Node(float x, float y, float cost, int level){
		index = CUR_INDICES[level];
		CUR_INDICES[level]++;
		this.cost = cost;
		this.x = x;
		this.y = y;
		this.name = "level "+level;
		downNodes = new ArrayList<Node>();
	}
	
	public void makeConnection(Node end, float cost){
		connections.add(new com.dungeon.game.pathing.Connection(this, end, cost));
	}
	
	public int getIndex(){
		return index;
	}
	
	public Array<Connection<Node>> getConnections(){
		return connections;
	}
	
	public static void resetIndex(int levels){
		CUR_INDICES = new int[levels];
		for(int i = 0; i < CUR_INDICES.length; i++){
			CUR_INDICES[i] = 0;
		}
	}
	
	public float findDistance(float x, float y){
		return (float) Math.sqrt((x-this.x*Tile.TS) * (x-this.x*Tile.TS) + (y-this.y*Tile.TS) * (y-this.y*Tile.TS));
	}

	public boolean isAdjacentTo(Node n2) {
		for(Node n: downNodes){
			for(Connection<Node> c: n.connections){
				if(c.getToNode().upNode.equals(n2))return true;
			}
		}
		return false;
	}
	
	public void findDownNode(){
		Node closeNode = downNodes.get(0);
		float closeNodeDist = closeNode.findDistance(x*Tile.TS, y*Tile.TS);
		for(Node n: downNodes){
			if(n.findDistance(x*Tile.TS, y*Tile.TS) < closeNodeDist){
				closeNode = n;
				closeNodeDist = n.findDistance(x*Tile.TS, y*Tile.TS);
			}
		}
		downNode = closeNode;
		if(downNode == null)System.out.println("FUCKITY");
	}
	
}
