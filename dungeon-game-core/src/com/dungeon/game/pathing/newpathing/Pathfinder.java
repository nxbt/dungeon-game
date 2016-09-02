package com.dungeon.game.pathing.newpathing;

import java.util.ArrayList;
import java.util.Collection;

public class Pathfinder {
	
	public Graph graph;
	
	public Pathfinder(Graph graph){
		this.graph = graph;
	}
	
	public Path findPath(int sX, int sY, int eX, int eY){ //find path between two given points
		Node startNode = graph.graphLevels[0].getCloseNode(sX, sY);
		Node endNode = graph.graphLevels[0].getCloseNode(eX, eY);
		Node curStartNode = startNode;
		Node curEndNode = endNode;
		int level = 0;
		do{
			if(curStartNode.upNode.equals(curEndNode.upNode)){
				return findPath(startNode, endNode, level);
			}else{
				curStartNode = curStartNode.upNode;
				curEndNode = curEndNode.upNode;
				level++;
			}
		}while(level < graph.graphLevels.length);
		return findPath(startNode, endNode, level);
	}
	
	public Path findPath(Node level0StartNode, Node level0EndNode, int level){ //find path between the two nodes at the given level
		Node startNode = level0StartNode;
		Node endNode = level0EndNode;
		for(int i = 0; i < level; i++){
			startNode = startNode.upNode;
			endNode = endNode.upNode;
		}
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
		
		queue.add(startNode);
		
		//this aint gonna work perfect, oh shit... it did?
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
		
		Path p = new Path(endNode);
		searching = true;
		while(searching){
			if(p.nodes.get(0).index == startNode.index){
				searching = false;
				break;
			}
			p.addNode(pointing[p.nodes.get(0).index]);
		}
		if(level == 0)return p;
		return findPath(p, level0StartNode, level - 1);
	}
	
	public Path findPath(Path p, Node level0StartNode, int level){ //is there a clever af way to combine the two pathfinding functions without sacrificing efficency?
		Node startNode = level0StartNode;
		Node endUpNode = p.nodes.get(1);
		Node endNode = null;
		for(int i = 0; i < level; i++){
			startNode = startNode.upNode;
		}
		
		//get a path from startNode to any node with endUpNode as it's upnode on the current level;
		
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
		
		queue.add(startNode);
		
		//this aint gonna work perfect, oh shit... it did?
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
					if(n1.upNode.equals(endUpNode)){
						endNode = n1;
						searching = false;
					}
					queue.add(n1);
				}
			}
			if(queue.size() == 0)searching = false;
		}
		
		p = new Path(endNode);
		searching = true;
		while(searching){
			if(p.nodes.get(0).index == startNode.index){
				searching = false;
				break;
			}
			p.addNode(pointing[p.nodes.get(0).index]);
		}
		if(level == 0)return p;
		return findPath(p, level0StartNode, level - 1);
	}

}
