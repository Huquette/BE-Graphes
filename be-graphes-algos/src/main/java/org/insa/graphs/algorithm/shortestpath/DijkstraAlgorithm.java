package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Point;

import java.util.List;

import java.util.ArrayList;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	private static final Status OPTIMAL = null;
	private Node origin;
	private ArrayList<Label> listeLabel = new ArrayList<Label>();
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	//debut init
        final ShortestPathData data = getInputData();
        Graph graphe = this.data.getGraph();
        Node noeudOrigine = data.getOrigin();
        Node noeudTerminal = data.getDestination();
        List<Node> listeNoeuds = graphe.getNodes();
        int nombreNoeuds = graphe.size();
        System.out.println("avant for 1 plus\n");
        for (int i=0; i<nombreNoeuds; i++) {
            Label label = new Label(i, listeNoeuds.get(i));
        	if (i == noeudOrigine.getId()) {
        		label.setCost(0);
        	}
        	listeLabel.add(label);
        }
        //On a fini l'init
        BinaryHeap<Label> tasLabel = new BinaryHeap<Label>();
        tasLabel.insert(listeLabel.get(noeudOrigine.getId()));
        Label labelMin;
        //List <Arc> listeSuccesseurs = new List<Arc>();
        List <Arc> listeSuccesseurs = new ArrayList<>();
        System.out.println("avant while \n");
        while((!tasLabel.isEmpty())&&(!(listeLabel.get(noeudTerminal.getId())).marque())) {
        	labelMin = tasLabel.findMin();
        	notifyNodeMarked(labelMin.getSommetCourant());
        	listeLabel.get(labelMin.getNumNoeud()).setMarque();
        	listeSuccesseurs = listeLabel.get(labelMin.getNumNoeud()).getSommetCourant().getSuccessors();
        	for (int i = 0; i < listeSuccesseurs.size(); i++) 
        	{
        	  Node noeudDest = listeSuccesseurs.get(i).getDestination();
        	  float longueur = listeSuccesseurs.get(i).getLength();
        	  float nouvelleLongueur = longueur + labelMin.getCost();
        	  if (nouvelleLongueur < listeLabel.get(noeudDest.getId()).getCost()) {
        		  listeLabel.get(noeudDest.getId()).setCost(nouvelleLongueur);
        		  listeLabel.get(noeudDest.getId()).setPere(labelMin.getSommetCourant());
        		  tasLabel.insert(listeLabel.get(noeudDest.getId()));
        	  }
        	}
        	tasLabel.remove(labelMin);
            if (tasLabel.isEmpty()) {
            	ShortestPathSolution solution = new ShortestPathSolution(data, Status.INFEASIBLE, null);
                return solution;
            }
        }
        List<Node> chemin_final = new ArrayList<>();
        int i=0;
        chemin_final.add(i, noeudTerminal);
        System.out.println("avant while final \n");
        while (listeLabel.get(noeudTerminal.getId()).getSommetCourant() != noeudOrigine) {
        	noeudTerminal = listeLabel.get(noeudTerminal.getId()).getPere();
            chemin_final.add(i, noeudTerminal);
        }
        System.out.println("avant reverse \n");
        for (i=0; i<chemin_final.size(); i++) {
        	Node intermediaire = chemin_final.get(i);
        	chemin_final.set(i, chemin_final.get(chemin_final.size()-1-i));
        	chemin_final.set(chemin_final.size()-1-i, intermediaire);
        }
        Path path_final = Path.createShortestPathFromNodes(graphe, chemin_final);
        ShortestPathSolution solution = new ShortestPathSolution(data, Status.OPTIMAL, path_final);
        return solution;
    }
    
}
