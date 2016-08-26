package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedHierarchicalGraph;
import com.badlogic.gdx.utils.Array;

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
		// TODO Auto-generated method stub
		return node;
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
