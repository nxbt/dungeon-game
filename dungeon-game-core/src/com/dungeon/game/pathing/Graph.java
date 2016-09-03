package com.dungeon.game.pathing;

public class Graph {
	
	public GraphLevel[] graphLevels;
	
	public int levels;
	
	public Graph(GraphLevel[] graphLevels){
		this.graphLevels = graphLevels;
		levels = graphLevels.length;
	}

}
