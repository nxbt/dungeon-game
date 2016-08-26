package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedHierarchicalGraph;
import com.badlogic.gdx.utils.Array;

public class HierarchicalGraph extends IndexedHierarchicalGraph<Node> {
	
	private Node[][] nodes;

	public HierarchicalGraph(Node[][] nodes) { //WTF HOW DO YOU DO THIS??? I think I got it?
		super(nodes.length);
		nodes = nodes;
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
		return null;
	}

}
