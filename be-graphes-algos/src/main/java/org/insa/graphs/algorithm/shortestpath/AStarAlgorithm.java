package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {
	private ArrayList<LabelStar> listeLabel = new ArrayList<LabelStar>();
    public AStarAlgorithm(ShortestPathData data) {
        super(data); 
    }
    public Label newLabel(int numNoeud, Node sommet_courant, Node noeudDestination,  AbstractInputData Data,Graph graphe) {
    	return new LabelStar(numNoeud, sommet_courant, noeudDestination, Data,graphe);
    }
}
