package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class LabelStar extends Label {
	private double distance;
	public LabelStar(int numNoeud, Node sommet_courant,Node noeudDestination, AbstractInputData Data,Graph graphe) {
		super(numNoeud, sommet_courant);
		if (Data.getMode() == AbstractInputData.Mode.TIME) {
			this.distance = Point.distance(sommet_courant.getPoint(), noeudDestination.getPoint())/(graphe.getGraphInformation().getMaximumSpeed()) * 3.6;
		}
		else {
			this.distance = Point.distance(sommet_courant.getPoint(), noeudDestination.getPoint());
		}
	}
	public double getDistance() {
		return this.distance;
	}
	public double getTotalCost() {
		return (double) (this.distance + this.getCost());
	}
	 /**
     * Compare the ID of this node with the ID of the given node.
     * 
     * @param other Node to compare this node with.
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
}
