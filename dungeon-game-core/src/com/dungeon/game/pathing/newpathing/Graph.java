package com.dungeon.game.pathing.newpathing;

public class Graph {
	
	public GraphLevel[] graphLevels;
	
	public int levels;
	
	public Graph(GraphLevel[] graphLevels){
		this.graphLevels = graphLevels;
		levels = graphLevels.length;
	}

}
