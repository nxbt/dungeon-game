package com.dungeon.game.pathing;

public class Connection implements com.badlogic.gdx.ai.pfa.Connection<Node> {
	private Node start;
	private Node end;
	private float cost;
	
	public Connection(Node start, Node end, float cost){
		this.start = start;
		this.end = end;
		this.cost = cost;
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return cost;
	}

	@Override
	public Node getFromNode() {
		// TODO Auto-generated method stub
		return start;
	}

	@Override
	public Node getToNode() {
		// TODO Auto-generated method stub
		return end;
	}

}
