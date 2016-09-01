package com.dungeon.game.pathing.newpathing;

import java.util.ArrayList;

public class GraphLevel {
	private int nodeIndexCounter;
	
	public ArrayList<Node> nodes;
	
	public GraphLevel(){
		nodes = new ArrayList<Node>();
		nodeIndexCounter = 0;
	}
	
	public Node addNode(float x, float y){
		Node n = new Node(x, y, nodeIndexCounter);
		nodes.add(n);
		nodeIndexCounter++;
		return n;
	}
	
	public Node getCloseNode(float x, float y){
		Node closeNode = nodes.get(0);
		float closeNodeDist = closeNode.findDistance(x, y);
		for(Node n: nodes){
			if(n.findDistance(x, y) < closeNodeDist){
				closeNode = n;
				closeNodeDist = n.findDistance(x, y);
			}
		}
		return closeNode;
	}
	
	
}
