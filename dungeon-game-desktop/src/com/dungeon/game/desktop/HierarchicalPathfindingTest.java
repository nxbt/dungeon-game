package com.dungeon.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedHierarchicalGraph;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dungeon.game.pathing.Node;
import com.dungeon.game.world.Tile;

import java.util.ArrayList;

import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;


public class HierarchicalPathfindingTest extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer; //the shapeRenderer
	
	private OrthographicCamera cam; // the camera
	
	private Viewport view; // the viewport
	
	private int width; //width of the map
	
	private int height; //height of the map
	
	public void create() {
		Gdx.graphics.setWindowedMode(1280, 720); 
		
		shapeRenderer = new ShapeRenderer();
		
		cam = new OrthographicCamera();
		cam.zoom = 1;
		view = new FitViewport(640, 360, cam);
		view.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
		
	}
	
	public static void main (String[] arg) { //MAIN METHOD
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new HierarchicalPathfindingTest(), config);
	}
	
	//node class
	
	private class Node{
		
		private Array<com.badlogic.gdx.ai.pfa.Connection<Node>> connections = new Array<com.badlogic.gdx.ai.pfa.Connection<Node>>();
		public float cost;
		private int index;
		public float x;
		public float y;
		public Node upNode;
		public Node downNode;
		
		public Node(float x, float y, float cost, int index){
			this.index = index;
			this.cost = cost;
			this.x = x;
			this.y = y;
		}
		

		
		public void makeConnection(Node end, float cost){
			connections.add(new Connection(this, end, cost));
		}
		
		public int getIndex(){
			return index;
		}
		
		public Array<com.badlogic.gdx.ai.pfa.Connection<Node>> getConnections(){
			return connections;
		}
		
		//returns the distance between this node and a given x and y
		public float findDistance(float x, float y){
			return (float) Math.sqrt((x-this.x*Tile.TS) * (x-this.x*Tile.TS) + (y-this.y*Tile.TS) * (y-this.y*Tile.TS));
		}
	}
	
	//implementations of Heirarchical Pathfinding classes
	
	//connection implementation
	
	private class Connection implements com.badlogic.gdx.ai.pfa.Connection<Node>{
		private Node startNode;
		private Node endNode;
		private float cost;

		
		public Connection(Node startNode, Node endNode, float cost){
			this.startNode = startNode;
			this.endNode = endNode;
			this.cost = cost;
		}
		
		@Override
		public float getCost() {
			return cost;
		}

		@Override
		public Node getFromNode() {
			return startNode;
		}

		@Override
		public Node getToNode() {
			return endNode;
		}
		
	}
	
	//heirarchicalGraph implementation
	

	public class HierarchicalGraph extends IndexedHierarchicalGraph<Node> {
		
		public Node[][] nodes;

		public HierarchicalGraph(Node[][] nodes) {
			super(nodes.length);
			this.nodes = nodes;
		}

		@Override
		public int getIndex(Node node) {
			// TODO Auto-generated method stub
			return node.index;
		}

		@Override
		public int getNodeCount() {
			return nodes[level].length;
		}

		@Override
		public Array<com.badlogic.gdx.ai.pfa.Connection<Node>> getConnections(Node fromNode) {
			// TODO Auto-generated method stub
			return fromNode.getConnections();
		}

		@Override
		public Node convertNodeBetweenLevels(int inputLevel, Node node, int outputLevel) {
			if(inputLevel == outputLevel)return node;
			else if(inputLevel > outputLevel) return node.downNode;
			else return node.upNode;
		}
		
		//finds the closest node on the current graph level to this position
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
	
	//heuristic implementation
	
	private class Heuristic implements com.badlogic.gdx.ai.pfa.Heuristic<Node> {

		@Override
		public float estimate(Node node, Node endNode) {
			return 0; //for now we don't care about optimization, so the Hueristic will always return 0;
		}
	
	}
}
