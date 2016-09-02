package com.dungeon.game.pathing.newpathing;

import java.util.ArrayList;

import com.dungeon.game.world.Tile;

public class Node {
	
	public ArrayList<Node> outNodes; //node that can be moved to from this one
	
	public ArrayList<Node> inNodes; //this node can be moved to from these nodes;
	
	public ArrayList<Float> costs; //cost of moving to the corresponding node
	
	public float x; //x position of this node
	public float y; //y postion of this node
	
	public float xDistance;
	
	public float yDistance;
	
	public int index; //index for the indexed pathfinder to use
	
	public Node downNode; //the node that this one links to on the graphLevel below this one
	
	public ArrayList<Node> downNodes; //all nodes that link up to this one from the graph level below
	
	public Node upNode; //the node that this one links to on the graphLevel above this one
	
	public Node(float x, float y, int index){
		this.x = x;
		this.y = y;
		xDistance = x * Tile.TS;
		yDistance = y * Tile.TS;
		this.index = index;
		outNodes = new ArrayList<Node>();
		inNodes = new ArrayList<Node>();
		costs = new ArrayList<Float>();
		downNodes = new ArrayList<Node>();
	}
	
	//adds an adjacent node with the corresponding cost
	public void addConnection(Node n, float c){
		outNodes.add(n);
		n.inNodes.add(this);
		costs.add(c);
	}
	
	//returns the distance between the given point and this node
	public float findDistance(float x, float y){
		float xDiff = (x-xDistance);
		float yDiff = (y-yDistance);
		return (float) Math.sqrt(xDiff*xDiff + yDiff*yDiff);
	}
	
	public void setDownNode(){
		Node closeNode = downNodes.get(0);
		for(Node n: downNodes){
			if(n.findDistance(x, y) < closeNode.findDistance(x, y))closeNode = n;
		}
		downNode = closeNode;
	}
	
	public boolean isAdjacentTo(Node other) {
		for(Node n: downNodes){
			for(Node n2: n.outNodes){
				if(n2.upNode.equals(other))return true;
			}
		}
		return false;
	}
}
