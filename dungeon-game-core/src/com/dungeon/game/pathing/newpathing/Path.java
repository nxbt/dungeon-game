package com.dungeon.game.pathing.newpathing;

import java.util.ArrayList;

public class Path implements Cloneable{
	
	public ArrayList<Node> nodes; //the nodes on this path, first first, last last
	
	public float cost; //do we need this?
	
	public Path(Node n){
		nodes = new ArrayList<Node>();
		nodes.add(0, n);
		cost = 0;
	}
	
	public void addNode(Node n){
		Node prev = nodes.get(nodes.size() - 1); //n should always have a connection from n, if not it will crash, but thats better than adding an if check!
		cost+=prev.costs.get(prev.outNodes.indexOf(n));
	}
	public Path clone() {
		try {
			return (Path) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
