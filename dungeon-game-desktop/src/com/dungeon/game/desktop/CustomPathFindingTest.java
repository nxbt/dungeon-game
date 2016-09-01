package com.dungeon.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
	
	private static final int[][] indexArray = new int[][]{
		new int[]{(int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7)},
		new int[]{(int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7)},
		new int[]{(int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7)},
		new int[]{(int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7)},
		new int[]{(int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7), (int) (Math.random() + 0.7)},
	};
	
	private static final int[][] costArray = new int[][]{
		new int[]{(int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5)},
		new int[]{(int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5)},
		new int[]{(int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5)},
		new int[]{(int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5)},
		new int[]{(int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5), (int) (Math.random()*5)},
	};
	
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
		font.getData().setScale(2);
		
		//create the camera and viewport
		cam = new OrthographicCamera();
		cam.zoom = 4f;
		view = new FitViewport(640, 360, cam);
		view.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(indexArray.length / 2 *scale, indexArray.length / 2 * scale, 0);
		
		//set mouse X and Y to 0 my default
		mouseX = 0;
		
		mouseY = 0;
		
		GraphLevel gl = new GraphLevel();
		Node[][] nodeArray = new Node[indexArray.length][indexArray[0].length];
		
		for(int x = 0; x < indexArray.length; x++){
			for(int y = 0; y < indexArray[0].length; y++){
				/*if(indexArray[x][y] == 1)*/nodeArray[x][y] = gl.addNode(x, y);
			}
		}
		
		for(int x = 0; x < nodeArray.length; x++){
			for(int y = 0; y < nodeArray[0].length; y++){
				if(nodeArray[x][y] != null){
					if(x > 0 && nodeArray[x - 1][y] != null)nodeArray[x][y].addConnection(nodeArray[x - 1][y], (costArray[x][y] + costArray[x - 1][y])/2f);
					if(x < nodeArray.length - 1 && nodeArray[x + 1][y] != null)nodeArray[x][y].addConnection(nodeArray[x + 1][y], (costArray[x][y] + costArray[x + 1][y])/2f);
					if(y > 0 && nodeArray[x][y - 1] != null)nodeArray[x][y].addConnection(nodeArray[x][y - 1], (costArray[x][y] + costArray[x][y - 1])/2f);
					if(y < nodeArray.length - 1 && nodeArray[x][y + 1] != null)nodeArray[x][y].addConnection(nodeArray[x][y + 1], (costArray[x][y] + costArray[x][y + 1])/2f);
				}
			}
		}
		
		Graph graph = new Graph(new GraphLevel[]{gl});
		
		pathfinder = new Pathfinder(graph);
	}
	
	public void render(){
		//move the mouse
		mouseX += Gdx.input.getDeltaX();
		mouseY -= Gdx.input.getDeltaY();
		
		//if left click is down, set the start node
		if(Gdx.input.isButtonPressed(0)){
			startNode = pathfinder.graph.graphLevels[0].getCloseNode(mouseX/scale*Tile.TS, mouseY/scale*Tile.TS);
		}
		
		//if right click is down, set the end node
		if(Gdx.input.isButtonPressed(1)){
			endNode = pathfinder.graph.graphLevels[0].getCloseNode(mouseX/scale*Tile.TS, mouseY/scale*Tile.TS);
		}
		
		if(endNode != null && startNode != null){
			path = pathfinder.findPath(startNode, endNode, 0);
			System.out.println(path.cost);
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
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.circle(n.x*scale, n.y*scale, 30);
			for(int i = 0; i < n.outNodes.size(); i++){
				Node n2 = n.outNodes.get(i);
				shapeRenderer.setColor(new Color(0, n.costs.get(i)/5f, 0, 1));
				shapeRenderer.rectLine(n.x*scale,n.y*scale, n2.x*scale, n2.y*scale, 20);
			}
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
		

		
		batch.begin();
		batch.setProjectionMatrix(cam.combined);
		for(Node n: pathfinder.graph.graphLevels[0].nodes){
			for(int i = 0; i < n.outNodes.size(); i++){
				Node n2 = n.outNodes.get(i);
				font.draw(batch, ""+n.costs.get(i), (n.x+n2.x)*scale/2f - 26, (n.y+n2.y)*scale/2f + 12);
			}
		}
		batch.end();
	}
}
