package com.dungeon.game.pathing;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedHierarchicalGraph;
import com.badlogic.gdx.utils.Array;
import com.dungeon.game.world.Tile;

public class HierarchicalGraph extends IndexedHierarchicalGraph<Node> {
	
	public Node[][] nodes;

	public HierarchicalGraph(Node[][] nodes) { //WTF HOW DO YOU DO THIS??? I think I got it?
		super(nodes.length);
		this.nodes = nodes;
		levelCount = nodes.length;
	}

	@Override
	public int getIndex(Node node) {
		return node.getIndex();
	}

	@Override
	public int getNodeCount() {
		return nodes[level].length;
	}

	@Override
	public Array<Connection<Node>> getConnections(Node fromNode) {
		return fromNode.getConnections();
	}

	@Override
	public Node convertNodeBetweenLevels(int inputLevel, Node node, int outputLevel) {
		if(inputLevel == 0 && outputLevel == 1){
			if(node.upNode == null)System.out.println("up null");
			return node.upNode;
		}
		else if(inputLevel == 1 && outputLevel == 0) {
			if(node.downNode == null)System.out.println("down null");
			return node.downNode;
		}
		else if(inputLevel == outputLevel){
			if(node == null)System.out.println("fuck null");
			return node;
		}
		System.out.println("fuckity fuck fucker motherfucking fuck");
		System.out.println(inputLevel + " " + outputLevel);
		return null;
//		Node curNode = node;
//		int curLevel = inputLevel;
//		while(curLevel != outputLevel){
//			if(curLevel < outputLevel){
//				curLevel++;
//				if(curNode.upNode == null)System.out.println("up fuuuck " + inputLevel + " " + outputLevel + " " + node.name);
//				curNode = curNode.upNode;
//			}else{
//				curLevel--;
//				if(curNode.downNode == null)System.out.println("down fuuuck " + inputLevel + " " + outputLevel + " " + node.name);
//				curNode = curNode.downNode;
//			}
//		}
//		return curNode;
	}
	
	public Node getClosestNode(float x, float y){
		Node closeNode = nodes[level][0];
		float closeNodeDist = closeNode.findDistance(x, y);
		for(Node n: nodes[level]){
			if(n.findDistance(x, y) < closeNodeDist){
				closeNode = n;
				closeNodeDist = n.findDistance(x, y);
			}
		}
		return closeNode;
	}

}
