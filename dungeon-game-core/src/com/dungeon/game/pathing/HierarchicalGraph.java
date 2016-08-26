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
		Node curNode = node;
		int curLevel = inputLevel;
		while(curLevel != outputLevel){
			if(curLevel < outputLevel){
				curLevel++;
				if(curNode.upNode == null)System.out.println("up fuuuck " + inputLevel + " " + outputLevel + " " + node.name);
				curNode = curNode.upNode;
			}else{
				curLevel--;
				if(curNode.downNode == null)System.out.println("down fuuuck " + inputLevel + " " + outputLevel + " " + node.name);
				curNode = curNode.downNode;
//				if(curNode.downNodes.size() == 0)System.out.println(curNode.name);
//				Node closeNode = curNode.downNodes.get(0);
//				float closeNodeDist = closeNode.findDistance(node.x*Tile.TS, node.y*Tile.TS);
//				for(Node n: curNode.downNodes){
//					if(n.findDistance(node.x*Tile.TS, node.y*Tile.TS) < closeNodeDist){
//						closeNode = n;
//						closeNodeDist = n.findDistance(node.x*Tile.TS, node.y*Tile.TS);
//					}
//				}
//				curNode = closeNode;
			}
		}
		return curNode;
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
