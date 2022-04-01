package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;

import org.insa.graphs.model.Node;

public class Label {
	private int numNoeud;
	private Node sommet_courant;
	private Boolean trouve;
	private int cost;
	private Node pere;
	public int getCost() {
		return this.cost;
	}
	public int getNumNoeud(){
		return this.numNoeud;
	}
	public Label(int numNoeud, Node sommet_courant) {
		this.numNoeud = numNoeud;
		this.sommet_courant = sommet_courant;
		this.trouve = false;
		this.cost = -1;
		this.pere = null;
	}
	public void setCost(int cost){
		this.cost = cost;
	}
}
