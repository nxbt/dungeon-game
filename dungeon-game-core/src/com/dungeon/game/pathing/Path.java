package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Path implements Cloneable{
	
	
	public ArrayList<Node> nodes; //the nodes on this path, first first, last last
	
	public float cost; //do we need this?
	
	public Path(Node n){
		nodes = new ArrayList<Node>();
		nodes.add(0, n);
		cost = 0;
	}
	
	public void addNode(Node n){
		Node prev = nodes.get(0); //n should always have a connection from n, if not it will crash, but thats better than adding an if check!
		cost+=n.costs.get(n.outNodes.indexOf(prev));
		nodes.add(0, n);
	}
	
	public Path clone() {
		try {
			return (Path) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<int[]> getPath(){
		ArrayList<int[]> path = new ArrayList<int[]>();
		for(int i = 0; i < nodes.size(); i++){
			path.add(new int[]{(int) nodes.get(i).x,(int) nodes.get(i).y});
		}
		return path;
	}
	
	public static int[] getTargTile(World world, ArrayList<int[]> path){
		if(path.size() == 1)return path.get(0);
		boolean changeDestination;
		Vector2 startPoint_bl = new Vector2(path.get(0)[0]*Tile.TS,path.get(0)[1]*Tile.TS);
		Vector2 startPoint_br = new Vector2(path.get(0)[0]*Tile.TS+Tile.TS,path.get(0)[1]*Tile.TS);
		Vector2 startPoint_tl = new Vector2(path.get(0)[0]*Tile.TS,path.get(0)[1]*Tile.TS+Tile.TS);
		Vector2 startPoint_tr = new Vector2(path.get(0)[0]*Tile.TS+Tile.TS,path.get(0)[1]*Tile.TS+Tile.TS);
		Vector2 endPoint_bl;
		Vector2 endPoint_br;
		Vector2 endPoint_tl;
		Vector2 endPoint_tr;
		int[] destination = path.get(1);
		Polygon tilePolygon;
		for(int[] point: path){
			changeDestination = true;
			endPoint_bl = new Vector2(point[0]*Tile.TS,point[1]*Tile.TS);
			endPoint_br = new Vector2(point[0]*Tile.TS+Tile.TS,point[1]*Tile.TS);
			endPoint_tl = new Vector2(point[0]*Tile.TS,point[1]*Tile.TS+Tile.TS);
			endPoint_tr = new Vector2(point[0]*Tile.TS+Tile.TS,point[1]*Tile.TS+Tile.TS);
			for(int i = Math.min(path.get(0)[1], point[1]); i <= Math.max(path.get(0)[1], point[1]);i++){
				for(int k = Math.min(path.get(0)[0], point[0]); k<= Math.max(path.get(0)[0], point[0]);k++){	
					tilePolygon = new Polygon(new float[]{k*Tile.TS, i*Tile.TS,(k+1)*Tile.TS, i*Tile.TS,(k+1)*Tile.TS, (i+1)*Tile.TS, k*Tile.TS, (i+1)*Tile.TS});
					if(world.curFloor.tm[i][k].data==1&&(Intersector.intersectSegmentPolygon(startPoint_bl, endPoint_bl, tilePolygon)||Intersector.intersectSegmentPolygon(startPoint_br, endPoint_br, tilePolygon)||Intersector.intersectSegmentPolygon(startPoint_tl, endPoint_tl, tilePolygon)||Intersector.intersectSegmentPolygon(startPoint_tr, endPoint_tr, tilePolygon))){
						changeDestination = false;
					}
				}
			}
			if(changeDestination)destination = point;
			else break;
		}
		return destination;
	}

}
