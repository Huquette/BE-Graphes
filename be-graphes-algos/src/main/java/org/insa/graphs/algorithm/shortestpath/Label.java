package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;

import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>  {
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
	public Label(int numNoeud) {
		this.numNoeud = numNoeud;
		this.sommet_courant = sommet_courant;
		this.trouve = false;
		this.cost = 999999999;
		this.pere = null;
	}
	public void setCost(int cost){
		this.cost = cost;
	}
	public boolean marque() {
		return this.trouve;
	}
	  /**
     * Compare the ID of this node with the ID of the given node.
     * 
     * @param other Node to compare this node with.
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Label other) {
        return Integer.compare(getCost(), other.getCost());
    }
}
