package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.Tile;

public class Area {
	ArrayList<Area> adjacentAreas; //Holds reference to all adjacent Areas
	ArrayList<int[][]> Edges; //Holds points where this Area Borders other Areas
	ArrayList<int[][][][]> minPaths; //Holds information for the minimum viable paths from one edge to another;
	Tile[][] tm; //contains tile data for the area; 
	ArrayList<Entity> entities; //contains data for all entities in the area;
}
