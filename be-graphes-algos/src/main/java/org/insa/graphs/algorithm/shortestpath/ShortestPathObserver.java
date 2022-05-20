package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public interface ShortestPathObserver {
	
	/**
	 * Notify the observer that the origin has been processed.
	 * 
	 * @param node Origin.
	 */
	public void notifyOriginProcessed(Node node);
	
	/**
	 * Notify the observer that a node has been reached for the first
	 * time.
	 * 
	 * @param node Node that has been reached.
	 */
	public void notifyNodeReached(Node node);
	
	/**
	 * Notify the observer that a node has been marked, i.e. its final
	 * value has been set.
	 * 
	 * @param node Node that has been marked.
	 */
	public void notifyNodeMarked(Node node);

	/**
	 * Notify the observer that the destination has been reached.
	 * 
	 * @param node Destination.
	 */
	public void notifyDestinationReached(Node node);
	
	/**
	 * On ajoute un observeur pour le vendeur de muguet
	 * 
	 * @param node les endroits où on vend le muguet
	 */
	public void notifyVendeur(Node node);
	
}
