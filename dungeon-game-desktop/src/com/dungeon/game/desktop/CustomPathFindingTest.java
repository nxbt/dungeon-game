package com.dungeon.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dungeon.game.GeneratorTest;
import com.dungeon.game.pathing.newpathing.Graph;
import com.dungeon.game.pathing.newpathing.GraphLevel;
import com.dungeon.game.pathing.newpathing.Node;
import com.dungeon.game.pathing.newpathing.Path;
import com.dungeon.game.pathing.newpathing.Pathfinder;
import com.dungeon.game.world.Tile;

public class CustomPathFindingTest extends ApplicationAdapter {
	
	public static void main (String[] arg) {
		boolean launchGenTest = false;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		if(launchGenTest) new LwjglApplication(new GeneratorTest(), config);
			else new LwjglApplication(new CustomPathFindingTest(), config);
	}
	
	private ShapeRenderer shapeRenderer; //the shapeRenderer
	
	private SpriteBatch batch;
	
	private OrthographicCamera cam; // the camera
	
	private Viewport view; // the viewport
	
	private static final float scale = 100f; //scale used for rendering;
	
	private float mouseX; //coordinates of the mouse
	
	private float mouseY;
	
	private Pathfinder pathfinder;
	
	private Node startNode; //the node to begin pathfinding at
	
	private Node endNode; //the node to end pathfinding at
	
	private Path path;
	
	private BitmapFont font;
	
	public void create(){
		Gdx.graphics.setWindowedMode(1280, 720); 
		
		Gdx.input.setCursorCatched(true);
		
		batch = new SpriteBatch();
		
		shapeRenderer = new ShapeRenderer();
		
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.RED);
		font.getData().setScale(4);
		
		int width = 25;
		int height = 25;

		int[][] indexArray  = new int[width][height];
		int[][] costArray  = new int[width][height];
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				indexArray[x][y] = (int)(Math.random() + 0.7);
				costArray[x][y] = (int)(Math.random() * 5);
			}
		}
		
		//create the camera and viewport
		cam = new OrthographicCamera();
		cam.zoom = 7f;
		view = new FitViewport(640, 360, cam);
		view.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(indexArray.length / 2 *scale, indexArray.length / 2 * scale, 0);
		
		//set mouse X and Y to 0 my default
		mouseX = 0;
		mouseY = 0;
		
		int[][] level1NodeCosts = new int[width/5][height/5];

		GraphLevel gl0 = new GraphLevel();
		GraphLevel gl1 = new GraphLevel();
		Node[][] nodeArray = new Node[width][height];
		for(int x = 0; x < width; x+=5){
			for(int y = 0; y < height; y+=5){
				gl1.addNode(x + 2.5f, y + 2.5f);
			}
		}
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				Node n = gl0.addNode(x + 0.5f, y + 0.5f);
				nodeArray[x][y] = n;
				int upNodeIndex = (int)(y/5) + (int)(x/5)*5;
				n.upNode = gl1.nodes.get(upNodeIndex);
				level1NodeCosts[x/5][y/5] += costArray[x][y];
				if(x%5 == 2 && y%5 == 2)gl1.nodes.get(upNodeIndex).downNode = n;
			}
		}
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(nodeArray[x][y] != null){
					if(x > 0 && nodeArray[x - 1][y] != null){
						nodeArray[x][y].addConnection(nodeArray[x - 1][y], (costArray[x][y] + costArray[x - 1][y])/2f);
						if(!nodeArray[x][y].upNode.equals(nodeArray[x - 1][y].upNode) && !nodeArray[x][y].upNode.outNodes.contains(nodeArray[x - 1][y].upNode)){
							float thisCost = level1NodeCosts[x/5][y/5];
							float otherCost = level1NodeCosts[(x - 1)/5][y/5];
							nodeArray[x][y].upNode.addConnection(nodeArray[x - 1][y].upNode, (thisCost + otherCost)/50f);
						}
					}
					if(x < nodeArray.length - 1 && nodeArray[x + 1][y] != null){
						nodeArray[x][y].addConnection(nodeArray[x + 1][y], (costArray[x][y] + costArray[x + 1][y])/2f);
						if(!nodeArray[x][y].upNode.equals(nodeArray[x + 1][y].upNode) && !nodeArray[x][y].upNode.outNodes.contains(nodeArray[x + 1][y].upNode)){
							float thisCost = level1NodeCosts[x/5][y/5];
							float otherCost = level1NodeCosts[(x + 1)/5][y/5];
							nodeArray[x][y].upNode.addConnection(nodeArray[x + 1][y].upNode, (thisCost + otherCost)/50f);
						}
					}
					if(y > 0 && nodeArray[x][y - 1] != null){
						nodeArray[x][y].addConnection(nodeArray[x][y - 1], (costArray[x][y] + costArray[x][y - 1])/2f);
						if(!nodeArray[x][y].upNode.equals(nodeArray[x][y - 1].upNode) && !nodeArray[x][y].upNode.outNodes.contains(nodeArray[x][y - 1].upNode)){
							float thisCost = level1NodeCosts[x/5][y/5];
							float otherCost = level1NodeCosts[x/5][(y - 1)/5];
							nodeArray[x][y].upNode.addConnection(nodeArray[x][y - 1].upNode, (thisCost + otherCost)/50f);
						}
					}
					if(y < nodeArray.length - 1 && nodeArray[x][y + 1] != null){
						nodeArray[x][y].addConnection(nodeArray[x][y + 1], (costArray[x][y] + costArray[x][y + 1])/2f);
						if(!nodeArray[x][y].upNode.equals(nodeArray[x][y + 1].upNode) && !nodeArray[x][y].upNode.outNodes.contains(nodeArray[x][y + 1].upNode)){
							float thisCost = level1NodeCosts[x/5][y/5];
							float otherCost = level1NodeCosts[x/5][(y + 1)/5];
							nodeArray[x][y].upNode.addConnection(nodeArray[x][y + 1].upNode, (thisCost + otherCost)/50f);
						}
					}
				}
			}
		}
		
		Graph graph = new Graph(new GraphLevel[]{gl0, gl1});
		
		pathfinder = new Pathfinder(graph);
	}
	
	public void render(){
		//move the mouse
		mouseX += Gdx.input.getDeltaX();
		mouseY -= Gdx.input.getDeltaY();
		
		boolean nodeChange = false;
		
		//if left click is down, set the start node
		if(Gdx.input.isKeyJustPressed(Keys.NUM_1)){
			startNode = pathfinder.graph.graphLevels[0].getCloseNode(mouseX/scale*Tile.TS, mouseY/scale*Tile.TS);
			nodeChange = true;
		}
		
		//if right click is down, set the end node
		if(Gdx.input.isKeyJustPressed(Keys.NUM_2)){
			endNode = pathfinder.graph.graphLevels[0].getCloseNode(mouseX/scale*Tile.TS, mouseY/scale*Tile.TS);
			nodeChange = true;
		}
		
		if(nodeChange && endNode != null && startNode != null){
			path = pathfinder.findPath(startNode.x*Tile.TS, startNode.y*Tile.TS, endNode.x*Tile.TS, endNode.y*Tile.TS);
//			System.out.println(path.cost);
		}

		//clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.setAutoShapeType(true);
		
		shapeRenderer.setColor(Color.GREEN);
		for(Node n: pathfinder.graph.graphLevels[0].nodes){
			for(int i = 0; i < n.outNodes.size(); i++){
				Node n2 = n.outNodes.get(i);
				shapeRenderer.setColor(new Color(0, n.costs.get(i)/5f, 0, 1));
				shapeRenderer.rectLine(n.x*scale,n.y*scale, n2.x*scale, n2.y*scale, 20);
			}
		}
		

		for(Node n: pathfinder.graph.graphLevels[0].nodes){
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.circle(n.x*scale, n.y*scale, 30);
			if(n.equals(startNode) && n.equals(endNode)){
				shapeRenderer.setColor(Color.GRAY);
				shapeRenderer.circle(n.x*scale, n.y*scale, 20);
				
			}else if(n.equals(startNode)){
				shapeRenderer.setColor(Color.WHITE);
				shapeRenderer.circle(n.x*scale, n.y*scale, 20);
				
			}else if(n.equals(endNode)){
				shapeRenderer.setColor(Color.BLACK);
				shapeRenderer.circle(n.x*scale, n.y*scale, 20);
			}
		}
		
		if(path != null){
			shapeRenderer.setColor(Color.GRAY);
			Node prevNode = path.nodes.get(0);
			for(Node n: path.nodes){
				if(prevNode != null){
					shapeRenderer.rectLine(prevNode.x*scale, prevNode.y*scale, n.x*scale, n.y*scale, 15);
				}
				prevNode = n;
			}
		}
		
		
		shapeRenderer.set(ShapeType.Filled);
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.circle(mouseX, mouseY, 5);
		shapeRenderer.end();
		

		
//		batch.begin();
//		batch.setProjectionMatrix(cam.combined);
//		for(Node n: pathfinder.graph.graphLevels[1].nodes){
//			for(int i = 0; i < n.outNodes.size(); i++){
//				Node n2 = n.outNodes.get(i);
//				font.draw(batch, ""+n.costs.get(i), (n.x+n2.x)*scale/2f - 50, (n.y+n2.y)*scale/2f + 20);
//			}
//		}
//		batch.end();
	}
}
