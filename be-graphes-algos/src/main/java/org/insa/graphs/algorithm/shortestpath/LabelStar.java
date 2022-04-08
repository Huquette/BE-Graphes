package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class LabelStar extends Label {
	private double distance;
	public LabelStar(int numNoeud, Node sommet_courant,Node noeudDestination) {
		super(numNoeud, sommet_courant);
		this.distance = Point.distance(sommet_courant.getPoint(), noeudDestination.getPoint());
	}
	public double getDistance() {
		return this.distance;
	}
	public float getTotalCost() {
		return (float) (this.distance + this.getCost());
	}
	 /**
     * Compare the ID of this node with the ID of the given node.
     * 
     * @param other Node to compare this node with.
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
}
