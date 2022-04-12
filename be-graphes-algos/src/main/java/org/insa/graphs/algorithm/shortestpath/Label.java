package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;

import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>  {
	private int numNoeud;
	private Node sommet_courant;
	private Boolean marque;
	private double cost;
	private Node pere;
	public double getCost() {
		return this.cost;
	}
	public int getNumNoeud(){
		return this.numNoeud;
	}
	public Label(int numNoeud, Node sommet_courant) {
		this.numNoeud = numNoeud;
		this.sommet_courant = sommet_courant;
		this.marque = false;
		this.cost = 999999999;
		this.pere = null;
	}
	public void setCost(double cost){
		this.cost = cost;
	}
	public double getTotalCost() {
		return this.cost;
	}
	public void setMarque() {
		this.marque = true;
	}
	public boolean marque() {
		return this.marque;
	}
	public void setPere(Node pere) {
		this.pere = pere;
	}
	public Node getSommetCourant() {
		return this.sommet_courant;
	}
	public Node getPere() {
		return this.pere;
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
        return Double.compare(getTotalCost(), other.getTotalCost());
    }
	/**
	 * Compare the ID of this node with the ID of the given node.
	 * 
	 * @param other Node to compare this node with.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
}
