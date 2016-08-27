package com.dungeon.game.pathing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Path implements GraphPath<Node> {
	
	public ArrayList<Node> nodes;
	
	public World world;
	
	public Path(World world){
		super();
		this.world = world;
		nodes = new ArrayList<Node>();
	}

	@Override
	public Iterator<Node> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
	
	public ArrayList<int[]> getPath(){
		ArrayList<int[]> path = new ArrayList<int[]>();
		for(int i = 0; i < nodes.size(); i++){
			path.add(new int[]{(int) nodes.get(i).x,(int) nodes.get(i).y});
		}
		return path;
		
	}
	
	public int[] getTargTile(){
		ArrayList<int[]> path = getPath();
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
