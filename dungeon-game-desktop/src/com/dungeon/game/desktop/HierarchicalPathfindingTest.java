package com.dungeon.game.desktop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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


public class HierarchicalPathfindingTest extends ApplicationAdapter {
	
	private static final int numLevelOneNodes = 6; //the number of level 1 nodes on the map
	
	private static final Color[] nodeColors = new Color[]{ //colors to be used to draw the nodes
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
	
	private HierarchicalGraph graph; //the hierarchical graph of nodes
	
	private HierarchicalPathFinder<Node> pathfinder; //the actual hierarchical pathfinder
	
	private float scale = 100f; //scale used for rendering;
	
	private float mouseX; //coordinates of the mouse
	
	private float mouseY;
	
	private Node startNode; //the node to begin pathfinding at
	
	private Node endNode; //the node to end pathfinding at
	
	private Path path; //the path between the nodes
	
	public void create() {
		Gdx.graphics.setWindowedMode(1280, 720); 
		
		Gdx.input.setCursorCatched(true);
		
		shapeRenderer = new ShapeRenderer();
		
		//create the camera and viewport
		cam = new OrthographicCamera();
		cam.zoom = 4f;
		view = new FitViewport(640, 360, cam);
		view.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(scale*levelZeroNodeMap.length / 2, scale*levelZeroNodeMap[0].length / 2, 0);
		
		//set mouse X and Y to 0 my default
		mouseX = 0;
		
		mouseY = 0;
		
		Node[][] levelZeroNodesByIndex = new Node[levelZeroNodeMap.length][levelZeroNodeMap[0].length]; // a grid of level 0 nodes to make it easy to establish connections later by using a index
		ArrayList<Node> levelZeroNodes = new ArrayList<Node>(); //List of level zero nodes
		Node[] levelOneNodes = new Node[numLevelOneNodes]; //Array of level one nodes
		
		
		//create the level 1 nodes
		for(int i = 0; i < levelOneNodes.length; i++){
			//find where the level one node should be placed by averaging the position of all level 0 nodes of the same ID
			int total = 0; //how many level zero nodes of this id we've found so far;
			float totalX = 0; //the sum of their x positions
			float totalY = 0; //the sum of their y positions
			for(int k = 0; k < levelZeroNodeMap.length; k++){
				for(int e = 0; e < levelZeroNodeMap[0].length; e++){
					if(levelZeroNodeMap[k][e] == i){
						total++; //add one to total
						totalX+=k; //add the x of this node to totalX
						totalY+=e; //add the y of this node to totalY
					}
				}
			}
			totalX/=total; //divide by the total to get the average;
			totalY/=total; //divide by the total to get the average;
			
			//create a level one node!
			levelOneNodes[i] = new Node(totalX, totalY, 1, i, new Color(nodeColors[i].r*0.6f, nodeColors[i].g*0.6f, nodeColors[i].b*0.6f, 1)); 
			
		}

		int levelZeroNodeIndexer = 0;// this variable is used to give the level 0 nodes a unique index
		
		//create the level 0 nodes
		for(int i = 0; i < levelZeroNodeMap.length; i++){
			for(int k = 0; k < levelZeroNodeMap[0].length; k++){
				if(levelZeroNodeMap[i][k] != -1){ //-1 means there is no node here, so we don't need to make one
					Node n = new Node(i, k, 1, levelZeroNodeIndexer, nodeColors[levelZeroNodeMap[i][k]]); //create the node with an index from the levelZeroNodeIndexer and color from the color array based on this node's id
					Node levelOneNode = levelOneNodes[levelZeroNodeMap[i][k]]; //get the corresponding level one node using the id of this node from levelZeroNodeMap
					n.upNode = levelOneNode; //set this node's upNode to the corresponding levelOneNode
					
					//We want to set the level one Node's downNode to the closest corresponding level zero node...
					if(levelOneNode.downNode == null)levelOneNode.downNode = n; //so if the downNode is undefined just set it to the current Node
					//but if it's not undefined we only set it to the current node if the current node is closer than the current downNode
					else if(levelOneNode.findDistance(n.x, n.y) < levelOneNode.findDistance(levelOneNode.downNode.x, levelOneNode.downNode.y)){
						levelOneNode.downNode = n;
					}
					levelZeroNodesByIndex[i][k] = n; //add the level zero node to the indexed 2d array
					levelZeroNodes.add(n); //add the level zero node to the arrayList
					levelZeroNodeIndexer++; //increment the indexer, so the next level zero node has a new index
				}
			}
		}
		
		//make connections between adjacent level 0 nodes and level 1 nodes
		for(int i = 0; i < levelZeroNodesByIndex.length; i++){
			for(int k = 0; k < levelZeroNodesByIndex[0].length; k++){
				if(levelZeroNodesByIndex[i][k] != null){ //check if this i (x) and k (y) are defined in the 2d array of level zero nodes
					
					Node n = levelZeroNodesByIndex[i][k]; //make a temp variable called n that hold the node for this position
					
					if(i > 0 && levelZeroNodesByIndex[i - 1][k] != null){ //if there is a node to the left of this one...
						//give this one a connection to it...
						n.makeConnection(levelZeroNodesByIndex[i - 1][k], (n.cost + levelZeroNodesByIndex[i - 1][k].cost) / 2f);
						
						//then we need to check if there is already a connection between the two nodes' level one nodes
						boolean upNodesConnected = false;
						for(com.badlogic.gdx.ai.pfa.Connection<Node> c: n.upNode.getConnections()){
							if(c.getToNode().equals(levelZeroNodesByIndex[i - 1][k].upNode))upNodesConnected = true;
						}
						//if there is not already a connection, then make one!
						if(!upNodesConnected)n.upNode.makeConnection(levelZeroNodesByIndex[i - 1][k].upNode, 1);
					}
					
					if(i < levelZeroNodesByIndex.length - 1 && levelZeroNodesByIndex[i + 1][k] != null){ //if there is a node to the right of this one...
						//give this one a connection to it...
						n.makeConnection(levelZeroNodesByIndex[i + 1][k], (n.cost + levelZeroNodesByIndex[i + 1][k].cost) / 2f);
						
						//then we need to check if there is already a connection between the two nodes' level one nodes
						boolean upNodesConnected = false;
						for(com.badlogic.gdx.ai.pfa.Connection<Node> c: n.upNode.getConnections()){
							if(c.getToNode().equals(levelZeroNodesByIndex[i + 1][k].upNode))upNodesConnected = true;
						}
						//if there is not already a connection, then make one!
						if(!upNodesConnected)n.upNode.makeConnection(levelZeroNodesByIndex[i + 1][k].upNode, 1);
					}
					
					if(k > 0 && levelZeroNodesByIndex[i][k - 1] != null){ //if there is a node to the bottom of this one...
						//give this one a connection to it...
						n.makeConnection(levelZeroNodesByIndex[i][k - 1], (n.cost + levelZeroNodesByIndex[i][k - 1].cost) / 2f);
						
						//then we need to check if there is already a connection between the two nodes' level one nodes
						boolean upNodesConnected = false;
						for(com.badlogic.gdx.ai.pfa.Connection<Node> c: n.upNode.getConnections()){
							if(c.getToNode().equals(levelZeroNodesByIndex[i][k - 1].upNode))upNodesConnected = true;
						}
						//if there is not already a connection, then make one!
						if(!upNodesConnected)n.upNode.makeConnection(levelZeroNodesByIndex[i][k - 1].upNode, 1);
					}
					
					if(k < levelZeroNodesByIndex[0].length - 1 && levelZeroNodesByIndex[i][k + 1] != null){ //if there is a node to the top of this one...
						//give this one a connection to it...
						n.makeConnection(levelZeroNodesByIndex[i][k + 1], (n.cost + levelZeroNodesByIndex[i][k + 1].cost) / 2f);
						
						//then we need to check if there is already a connection between the two nodes' level one nodes
						boolean upNodesConnected = false;
						for(com.badlogic.gdx.ai.pfa.Connection<Node> c: n.upNode.getConnections()){
							if(c.getToNode().equals(levelZeroNodesByIndex[i][k + 1].upNode))upNodesConnected = true;
						}
						//if there is not already a connection, then make one!
						if(!upNodesConnected)n.upNode.makeConnection(levelZeroNodesByIndex[i][k + 1].upNode, 1);
					}

				}
			}
		}
		
		//make the hierarchical graph
		graph = new HierarchicalGraph(new Node[][]{levelZeroNodes.toArray(new Node[levelZeroNodes.size()]), levelOneNodes});
		
		//make the pathfinder
		pathfinder = new HierarchicalPathFinder<Node>(graph, new IndexedAStarPathFinder<Node>(graph));
		
		//define the path to be filled later
		path = new Path();
		
	}

	@Override
	public void render () {
		//move the mouse
		mouseX += Gdx.input.getDeltaX();
		mouseY -= Gdx.input.getDeltaY();
		
		//if left click is down, set the start node
		if(Gdx.input.isButtonPressed(0)){
			graph.setLevel(0);
			startNode = graph.getClosestNode(mouseX/scale, mouseY/scale);
		}
		
		//if right click is down, set the end node
		if(Gdx.input.isButtonPressed(1)){
			graph.setLevel(0);
			endNode = graph.getClosestNode(mouseX/scale, mouseY/scale);
		}
		
		//if both the start node and end node are set, but not equal, find the path between them
		if(startNode != null && endNode != null && !startNode.equals(endNode)){
			graph.setLevel(0);
			path.clear();
			pathfinder.searchNodePath(startNode, endNode, new Heuristic(), path);
			if(path.nodes.size() == 0)System.out.println("no path found"); //if a path is not found, print to the console.
		}
		
		//clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//update the camera
		cam.update();
		
		//begin the shapeRenderer
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.setAutoShapeType(true);
		
		//draw all the level one nodes
		for(Node n: graph.nodes[1]){
			shapeRenderer.setColor(n.color); //get the color of the corresponding node
			shapeRenderer.circle(scale*n.x, scale*n.y, 50);
			
		}
		
		//draw all level zero nodes
		for(Node n: graph.nodes[0]){
			shapeRenderer.setColor(n.color); //get the color of the node
			shapeRenderer.circle(scale*n.x, scale*n.y, 20);
		}
		
		shapeRenderer.set(ShapeType.Filled);

		//draw the start node in black if it is set
		if(startNode != null){
			shapeRenderer.setColor(Color.BLACK);
			shapeRenderer.circle(scale*startNode.x, scale*startNode.y, 10);
		}
		
		//draw the end node in white if it is set
		if(endNode != null){
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.circle(scale*endNode.x, scale*endNode.y, 10);
		}
		
		//if the start and end nodes are define but unequal, draw the path between them in a white line
		if(startNode != null && endNode != null && !startNode.equals(endNode)){
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.set(ShapeType.Line);
			Node preNode = null; //used to store the previous node on the path
			for(Node n: path.nodes){
				if(preNode != null){ //if its not the first node in the path
					shapeRenderer.line(preNode.x*scale, preNode.y*scale,n.x*scale,n.y*scale); //draw a line from the previous node on the path to this one
				}
				preNode = n; //set preNode to the current node.
			}
		}
		
		//draw the mouse as a gray circle
		shapeRenderer.set(ShapeType.Filled);
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.circle(mouseX, mouseY, 5);
		shapeRenderer.end();
	}
	
	public static void main (String[] arg) { //MAIN METHOD
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); //make the configuration
		new LwjglApplication(new HierarchicalPathfindingTest(), config); //create the application
	}
	
	//node class
	
	private class Node{
		
		private Array<com.badlogic.gdx.ai.pfa.Connection<Node>> connections = new Array<com.badlogic.gdx.ai.pfa.Connection<Node>>(); //store all the connections for this node
		public float cost; //the cost of the node, will always be 1 for this test
		private int index; //the node's index
		public float x; //the node's x
		public float y; //the node's y
		public Node upNode; //the node above this one in the hierarchy, null if this node has no level above it
		public Node downNode; //the node below this one in the hierarchy, null if this node is on level 0
		public Color color; //the node's color
		
		public Node(float x, float y, float cost, int index, Color color){ //constructor
			this.index = index;
			this.cost = cost;
			this.x = x;
			this.y = y;
			this.color = color;
		}
		

		
		public void makeConnection(Node end, float cost){ //add a new connection from this node to another node
			connections.add(new Connection(this, end, cost));
		}
		
		public int getIndex(){ //return the index of this node
			return index;
		}
		
		public Array<com.badlogic.gdx.ai.pfa.Connection<Node>> getConnections(){ //return the Array of all connections from this node
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
		private Node startNode; //the startnode of this connection
		private Node endNode; //the endNode of this connection
		private float cost; //the cost, will always be 1 for this test

		
		public Connection(Node startNode, Node endNode, float cost){ //constructor
			this.startNode = startNode;
			this.endNode = endNode;
			this.cost = cost;
		}
		
		@Override
		public float getCost() { //returns the cost of this connection
			return cost;
		}

		@Override
		public Node getFromNode() { //returns the startNode of this connection
			return startNode;
		}

		@Override
		public Node getToNode() { //returns the toNode of this connection
			return endNode;
		}
		
	}
	
	//heirarchicalGraph implementation
	

	public class HierarchicalGraph extends IndexedHierarchicalGraph<Node> {
		
		public Node[][] nodes; //2d array of nodes, the first dimension is the graph level, second dimension is all the nodes.

		public HierarchicalGraph(Node[][] nodes) { //constructor
			super(nodes.length);
			this.nodes = nodes;
		}

		@Override
		public int getIndex(Node node) { //get the index of a node
			// TODO Auto-generated method stub
			return node.getIndex();
		}

		@Override
		public int getNodeCount() {// returns how many nodes there are on the current level of the graph
			return nodes[level].length;
		}

		@Override
		public Array<com.badlogic.gdx.ai.pfa.Connection<Node>> getConnections(Node fromNode) { //gets a node's connections
			// TODO Auto-generated method stub
			return fromNode.getConnections();
		}

		@Override
		public Node convertNodeBetweenLevels(int inputLevel, Node node, int outputLevel) { //converts a node between levels
			//since we  use a 2 level graph for this test, this code should do for translating nodes.
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
			return 0; //for now we don't care about optimization, so the Heuristic will always return 0;
		}
	
	}
	
	//path implementation
	

	public class Path implements GraphPath<Node> {
		
		public ArrayList<Node> nodes; //the nodes on this path
		
		public Path(){ //constructor
			nodes = new ArrayList<Node>();
		}

		@Override
		public Iterator<Node> iterator() { //get the iterator
			return nodes.iterator();
		}

		@Override
		public int getCount() { //returns the length of this path
			return nodes.size();
		}

		@Override
		public Node get(int index) { //returns a node from the path at the given index
			return nodes.get(index);
		}

		@Override
		public void add(Node node) { //adds a node to the end of the path
			nodes.add(node);
			
		}

		@Override
		public void clear() { //clears the path
			nodes.clear();
			
		}

		@Override
		public void reverse() { //reverses the path
			Collections.reverse(nodes);
		}
		
	}
}
