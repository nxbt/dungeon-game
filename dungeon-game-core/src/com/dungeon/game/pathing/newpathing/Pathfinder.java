package com.dungeon.game.pathing.newpathing;

import java.util.ArrayList;

public class Pathfinder {
	
	public Graph graph;
	
	public Pathfinder(Graph graph){
		this.graph = graph;
	}
	
	public Path findPath(int sX, int sY, int eX, int eY){ //find path between two given points
		Node startNode = graph.graphLevels[0].getCloseNode(sX, sY);
		Node endNode = graph.graphLevels[0].getCloseNode(eX, eY);
		
		if(startNode.upNode.equals(endNode.upNode)){
			
		}
		return null;
	}
	
	public Path findPath(Node startNode, Node endNode, int level){ //find path between the two nodes at the given level
		final boolean[] closed = new boolean[graph.graphLevels[level].nodes.size()];
		final Node[] pointing = new Node[graph.graphLevels[level].nodes.size()];
		final float[] totalCost = new float[graph.graphLevels[level].nodes.size()];
		boolean searching = true;
		
		for(int i = 0; i < totalCost.length; i++){
			totalCost[i] = Integer.MAX_VALUE;
		}
		totalCost[startNode.index] = 0;
		
		closed[startNode.index] = true;
		
		ArrayList<Node> queue = new ArrayList<Node>(){
			
			private static final long serialVersionUID = 4114125320286689735L; //what does this mean??

			public boolean add(Node n){
				for(int i = 0; i < size(); i++){
					if(totalCost[n.index] < totalCost[get(i).index]){
						super.add(i, n);
						return true;
					}
				}
				super.add(n);
				return true;
			}
		};
		
		queue.addAll(startNode.outNodes);
		
		queue.add(startNode);
		
		//this aint gonna work perfect, yah def not working!
		while(searching){
			Node n = queue.remove(0);
			int index = n.index;
			closed[index] = true;
			for(Node n1: n.outNodes){
				if(!closed[n1.index]){
					if(totalCost[n.index] + n.costs.get(n.outNodes.indexOf(n1)) < totalCost[n1.index]){
						pointing[n1.index] = n;
						totalCost[n1.index] = totalCost[n.index] + n.costs.get(n.outNodes.indexOf(n1));
					}
					if(n1.equals(endNode))searching = false;
					queue.add(n1);
				}
			}
			if(queue.size() == 0)searching = false;
		}
		
//		for(int i = 0; i < closed.length; i++){
//			System.out.println("Index: " + i + " IsClosed: " + closed[i] + " Cost: " + totalCost[i] + " Pointing at Index: " + ((pointing[i] == null)?"null":pointing[i].index));
//		}
		
		Path p = new Path(endNode);
		searching = true;
		while(searching){
			if(p.nodes.get(0).index == startNode.index){
				searching = false;
				break;
			}
			p.addNode(pointing[p.nodes.get(0).index]);
		}
		return p;
	}

}
