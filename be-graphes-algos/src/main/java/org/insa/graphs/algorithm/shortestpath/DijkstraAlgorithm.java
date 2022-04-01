package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

import java.util.ArrayList;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	private Node origin;
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	//debut init
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        ArrayList<Label> listeLabel = new ArrayList<Label>();
        Graph graphe = this.data.getGraph();
        Node noeudOrigine = data.getOrigin();
        Node noeudTerminal = data.getDestination();
        int nombreNoeuds = graphe.size();
        for (int i=0; i<nombreNoeuds; i++) {
            Label label = new Label(i);
        	if (i == noeudOrigine.getId()) {
        		label.setCost(0);
        	}
        	listeLabel.set(i, label);
        }
        //On a fini l'init
        BinaryHeap<Label> tasLabel = new BinaryHeap<Label>();
        tasLabel.insert(listeLabel.get(0));
        while((!tasLabel.isEmpty())&&(listeLabel.get(noeudTerminal.getId())).marque()) {
        	
        }
        return solution;
    }
    
}
