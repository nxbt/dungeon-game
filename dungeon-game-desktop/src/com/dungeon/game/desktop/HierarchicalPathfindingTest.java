package com.dungeon.game.desktop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.HierarchicalPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedHierarchicalGraph;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dungeon.game.world.Tile;


public class HierarchicalPathfindingTest extends ApplicationAdapter {
	
	private static final int numLevelOneNodes = 6; //the number of level 1 nodes on the map
	
	private static final Color[] nodeColors = new Color[]{
			Color.RED,
			Color.BLUE,
			Color.GREEN,
			Color.YELLOW,
			Color.PURPLE,
			Color.ORANGE
	};
	
	private static final int[][] levelZeroNodeMap = new int[][]{ //data for level zero nodes, stored in a grid
		new int[]{ 0,  0,  0, -1,  4,  4,  4, -1,  5,  5}, 		//the number is what level 1 node to link to,
		new int[]{ 0,  0,  0,  0,  4,  4,  4,  4,  5,  5}, 		//ie: all number 2 nodes link to the same level 1 node and so on
		new int[]{ 0,  0,  0, -1,  4,  4,  4, -1,  5,  5}, 		// -1 means no node at this position
		new int[]{-1,  0, -1, -1, -1, -1,  3, -1, -1, -1}, 
		new int[]{ 1,  1,  1,  1, -1,  3,  3,  3, -1, -1}, 
		new int[]{ 1,  1,  1,  1,  1,  3,  3,  3, -1, -1}, 
		new int[]{ 1,  1,  1,  1, -1,  3,  3,  3, -1, -1}, 
		new int[]{-1,  1, -1, -1, -1, -1,  2, -1, -1, -1}, 
		new int[]{ 2,  2,  2,  2,  2,  2,  2, -1, -1, -1}, 
		new int[]{ 2,  2,  2,  2,  2,  2,  2, -1, -1, -1}, 
	};
	
	private ShapeRenderer shapeRenderer; //the shapeRenderer
	
	private OrthographicCamera cam; // the camera
	
	private Viewport view; // the viewport
	
	private int width; //width of the map
	
	private int height; //height of the map
	
	private HierarchicalGraph graph; //the hierarchical graph of nodes
	
	private HierarchicalPathFinder<Node> pathfinder; //the actual hierarchical pathfinder
	
	private float scale = 100f; //scale used for rendering;
	
	private float mouseX;
	
	private float mouseY;
	
	private Node startNode; //the node to begin pathfinding at
	
	private Node endNode; //the node to end pathfinding at
	
	private Path path; //the path between the nodes
	
	public void create() {
		Gdx.graphics.setWindowedMode(1280, 720); 
		
		Gdx.input.setCursorCatched(true);
		
		shapeRenderer = new ShapeRenderer();
		
		cam = new OrthographicCamera();
		cam.zoom = 4f;
		view = new FitViewport(640, 360, cam);
		view.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(scale*levelZeroNodeMap.length / 2, scale*levelZeroNodeMap[0].length / 2, 0);
		
		mouseX = 0;
		
		mouseY = 0;
		
		Node[][] levelZeroNodesByIndex = new Node[levelZeroNodeMap.length][levelZeroNodeMap[0].length]; // a grid of level 0 nodes to make it easy to establish connections later
		ArrayList<Node> levelZeroNodes = new ArrayList<Node>();
		Node[] levelOneNodes = new Node[numLevelOneNodes];

		int levelZeroNodeIndexer = 0;
		
		
		//create the level 1 nodes
		for(int i = 0; i < levelOneNodes.length; i++){
			//find where the level one node should be placed by averaging the position of all level 0 nodes of the same ID
			int total = 0; //how many level zero nodes of this id we've found so far;
			float totalX = 0; //the sum of their x positions
			float totalY = 0; //the sum of their y positions
			for(int k = 0; k < levelZeroNodeMap.length; k++){
				for(int e = 0; e < levelZeroNodeMap[0].length; e++){
					if(levelZeroNodeMap[k][e] == i){
						total++;
						totalX+=k;
						totalY+=e;
					}
				}
			}
			totalX/=total; //divide by the total to get the average;
			totalY/=total; //divide by the total to get the average;
			levelOneNodes[i] = new Node(totalX, totalY, 1, i, new Color(nodeColors[i].r*0.6f, nodeColors[i].g*0.6f, nodeColors[i].b*0.6f, 1)); //we will set the x and y of level 1 nodes later as the average of their level 0 counterparts
			
		}
		
		//create the level 0 nodes
		for(int i = 0; i < levelZeroNodeMap.length; i++){
			for(int k = 0; k < levelZeroNodeMap[0].length; k++){
				if(levelZeroNodeMap[i][k] != -1){
					Node n = new Node(i, k, 1, levelZeroNodeIndexer, nodeColors[levelZeroNodeMap[i][k]]);
					Node levelOneNode = levelOneNodes[levelZeroNodeMap[i][k]];
					n.upNode = levelOneNode;
					if(levelOneNode.downNode == null)levelOneNode.downNode = n;
					else if(levelOneNode.findDistance(n.x, n.y) < levelOneNode.findDistance(levelOneNode.downNode.x, levelOneNode.downNode.y)){
						levelOneNode.downNode = n;
					}
					levelZeroNodesByIndex[i][k] = n;
					levelZeroNodes.add(n);
					levelZeroNodeIndexer++;
				}
			}
		}
		//make connections between adjacent level 0 nodes
		for(int i = 0; i < levelZeroNodesByIndex.length; i++){
			for(int k = 0; k < levelZeroNodesByIndex[0].length; k++){
				if(levelZeroNodesByIndex[i][k] != null){
					Node n = levelZeroNodesByIndex[i][k];
					if(i > 0 && levelZeroNodesByIndex[i - 1][k] != null){
						n.makeConnection(levelZeroNodesByIndex[i - 1][k], (n.cost + levelZeroNodesByIndex[i - 1][k].cost) / 2f);
						boolean upNodesConnected = false;
						for(com.badlogic.gdx.ai.pfa.Connection<Node> c: n.upNode.getConnections()){
							if(c.getToNode().equals(levelZeroNodesByIndex[i - 1][k].upNode))upNodesConnected = true;
						}
						if(!upNodesConnected)n.upNode.makeConnection(levelZeroNodesByIndex[i - 1][k].upNode, 1);
					}
					if(i < levelZeroNodesByIndex.length - 1 && levelZeroNodesByIndex[i + 1][k] != null){
						n.makeConnection(levelZeroNodesByIndex[i + 1][k], (n.cost + levelZeroNodesByIndex[i + 1][k].cost) / 2f);
						boolean upNodesConnected = false;
						for(com.badlogic.gdx.ai.pfa.Connection<Node> c: n.upNode.getConnections()){
							if(c.getToNode().equals(levelZeroNodesByIndex[i + 1][k].upNode))upNodesConnected = true;
						}
						if(!upNodesConnected)n.upNode.makeConnection(levelZeroNodesByIndex[i + 1][k].upNode, 1);
					}
					if(k > 0 && levelZeroNodesByIndex[i][k - 1] != null){
						n.makeConnection(levelZeroNodesByIndex[i][k - 1], (n.cost + levelZeroNodesByIndex[i][k - 1].cost) / 2f);
						boolean upNodesConnected = false;
						for(com.badlogic.gdx.ai.pfa.Connection<Node> c: n.upNode.getConnections()){
							if(c.getToNode().equals(levelZeroNodesByIndex[i][k - 1].upNode))upNodesConnected = true;
						}
						if(!upNodesConnected)n.upNode.makeConnection(levelZeroNodesByIndex[i][k - 1].upNode, 1);
					}
					if(k < levelZeroNodesByIndex[0].length - 1 && levelZeroNodesByIndex[i][k + 1] != null){
						n.makeConnection(levelZeroNodesByIndex[i][k + 1], (n.cost + levelZeroNodesByIndex[i][k + 1].cost) / 2f);
						boolean upNodesConnected = false;
						for(com.badlogic.gdx.ai.pfa.Connection<Node> c: n.upNode.getConnections()){
							if(c.getToNode().equals(levelZeroNodesByIndex[i][k + 1].upNode))upNodesConnected = true;
						}
						if(!upNodesConnected)n.upNode.makeConnection(levelZeroNodesByIndex[i][k + 1].upNode, 1);
					}

				}
			}
		}
		
		graph = new HierarchicalGraph(new Node[][]{levelZeroNodes.toArray(new Node[levelZeroNodes.size()]), levelOneNodes});
		
		pathfinder = new HierarchicalPathFinder(graph, new IndexedAStarPathFinder(graph));
		
		path = new Path();
		
	}

	@Override
	public void render () {
		mouseX += Gdx.input.getDeltaX();
		mouseY -= Gdx.input.getDeltaY();
		
		if(Gdx.input.isButtonPressed(0)){
			graph.setLevel(0);
			startNode = graph.getClosestNode(mouseX/scale, mouseY/scale);
		}
		if(Gdx.input.isButtonPressed(1)){
			graph.setLevel(0);
			endNode = graph.getClosestNode(mouseX/scale, mouseY/scale);
		}
		
		if(startNode != null && endNode != null && !startNode.equals(endNode)){
			graph.setLevel(0);
			pathfinder.searchNodePath(startNode, endNode, new Heuristic(), path);
			if(path.nodes.size() == 0)System.out.println("no path found");
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.setAutoShapeType(true);
		for(Node n: graph.nodes[1]){
			shapeRenderer.setColor(n.color);
			shapeRenderer.circle(scale*n.x, scale*n.y, 50);
			
		}
		for(Node n: graph.nodes[0]){
			shapeRenderer.setColor(n.color);
			shapeRenderer.circle(scale*n.x, scale*n.y, 20);
			shapeRenderer.setColor(Color.WHITE);
//			for(com.badlogic.gdx.ai.pfa.Connection<Node> c: n.getConnections()){
//				for(com.badlogic.gdx.ai.pfa.Connection<Node> c2: c.getToNode().getConnections()){
//					if(c2.getToNode().equals(n)){
//						shapeRenderer.rectLine(scale*n.x, scale*n.y, scale*c.getToNode().x, scale*c.getToNode().y, 5);
//						break;
//					}
//				}
//			}
		}
		shapeRenderer.set(ShapeType.Filled);

		if(startNode != null){
			shapeRenderer.setColor(Color.BLACK);
			shapeRenderer.circle(scale*startNode.x, scale*startNode.y, 10);
		}
		if(endNode != null){
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.circle(scale*endNode.x, scale*endNode.y, 10);
		}
		if(startNode != null && endNode != null && !startNode.equals(endNode)){
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.set(ShapeType.Line);
			Node preNode = null;
			for(Node n: path.nodes){
				if(preNode != null){
					shapeRenderer.line(preNode.x*scale, preNode.y*scale,n.x*scale,n.y*scale);
				}
				preNode = n;
			}
		}
		shapeRenderer.set(ShapeType.Filled);
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.circle(mouseX, mouseY, 5);
		shapeRenderer.end();
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
		public Node upNode; //the node above this one in the hierarchy, null if this node has no level above it
		public Node downNode; //the node below this one in the hierarchy, null if this node is on level 0
		public Color color;
		
		public Node(float x, float y, float cost, int index, Color color){
			this.index = index;
			this.cost = cost;
			this.x = x;
			this.y = y;
			this.color = color;
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
			return (float) Math.sqrt((x-this.x) * (x-this.x) + (y-this.y) * (y-this.y));
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
			//since we will only ever use 2 level graphs, this code should do for translating nodes.
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
	
	//path implementation
	

	public class Path implements GraphPath<Node> {
		
		public ArrayList<Node> nodes;
		
		public Path(){
			nodes = new ArrayList<Node>();
		}

		@Override
		public Iterator<Node> iterator() {
			return nodes.iterator();
		}

		@Override
		public int getCount() {
			return nodes.size();
		}

		@Override
		public Node get(int index) {
			return nodes.get(index);
		}

		@Override
		public void add(Node node) {
			nodes.add(node);
			
		}

		@Override
		public void clear() {
			nodes.clear();
			
		}

		@Override
		public void reverse() {
			Collections.reverse(nodes);
		}
		
	}
}
