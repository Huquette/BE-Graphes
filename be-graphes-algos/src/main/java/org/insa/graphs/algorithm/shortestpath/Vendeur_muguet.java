
package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
public class Vendeur_muguet extends ShortestPathAlgorithm{
	//k=2
	public int k;
	protected Vendeur_muguet(ShortestPathData data) {
		super(data);
		this.k = 6;
		// TODO Auto-generated constructor stub
	}
	protected ShortestPathSolution doRun() {
		final ShortestPathData data = getInputData();
        Graph graphe = this.data.getGraph();
        Node noeudOrigine = data.getOrigin();
        Node noeudTerminal = data.getDestination();
        List<Node> listeNoeuds = graphe.getNodes();
        int nombreNoeuds = graphe.size();
        float [][]matCout= new float[nombreNoeuds][nombreNoeuds];
        float length;
        for (int i = 0; i<nombreNoeuds; i++) {
        	for (int j = 0; j<nombreNoeuds; j++) {
	        	ShortestPathData datainput = new ShortestPathData(graphe, listeNoeuds.get(i), listeNoeuds.get(j),ArcInspectorFactory.getAllFilters().get(0));
	        	ShortestPathSolution path = new DijkstraAlgorithm(datainput).doRun();
	        	if (path.isFeasible()) {
	        		length = path.getPath().getLength();
	        	}
	        	else {
	        		length = 999999999;
	        	}
	        	matCout[i][j] = length;
        	}
        }
        
        List<Integer> listeIndices = new ArrayList<>();
        List<Integer> listeFinale = new ArrayList<>();
    	float distanceTotalMax = 99999999;
    	Stack<List<Integer>> stack = new Stack<List<Integer>>();
    	listeIndices.add(0, nombreNoeuds-1);
		stack.push(listeIndices);
		int cmpt =0;
    	for (int i=1; i<nombreNoeuds;i++) {
    		listeIndices.set(0, nombreNoeuds - i);
    		System.out.println("liste push stack");
    		System.out.println(listeIndices);
    		System.out.println(stack);
    		stack.push(new ArrayList<Integer>(listeIndices));
    		cmpt++;
    	}
    	System.out.println(stack);
        while (!stack.isEmpty()) {
        	boolean b =true;
        	while (stack.peek().size() != this.k) {
        		b =true;
        		//System.out.println("avant pop \n");
        		//System.out.println(stack);
        		listeIndices = stack.pop();
        		int tailleListe = listeIndices.size();
        		int nombreFinal = listeIndices.get(tailleListe-1);
        		if (nombreFinal == nombreNoeuds-1) {
        			b=false;
        		}
        		listeIndices.add(tailleListe,nombreFinal+1);
        		if (b) {
        			stack.push(new ArrayList<Integer>(listeIndices));
        			cmpt++;
        		}
        		for (int i = nombreFinal+2; i<nombreNoeuds;i++) {
        			b =true;
        			if (i == nombreNoeuds-1) {
            			b=false;
            		}
        			listeIndices.set(tailleListe,i);
        			if (b) {
            			stack.push(new ArrayList<Integer>(listeIndices));
            			cmpt++;
            		}
        		}
        	}
        	//System.out.println(stack);
        	listeIndices = stack.pop();
        	System.out.println("lise testée de taille k\n");
        	System.out.println(listeIndices);
        	float distanceMax = 0;
        	for (int  i = 0; i< nombreNoeuds; i++) {
        		float distance = 99999999;
        		for (int j = 0; j<this.k; j++) {
        			if (matCout[i][listeIndices.get(j)]< distance) {
        				distance = matCout[i][listeIndices.get(j)];
        			}
        		}
        		if (distanceMax < distance) {
        			distanceMax = distance;
        		}
        		if (distanceMax >= distanceTotalMax) {
        			b = false;
        			break;
        		}
        	}
        	if (b) {
        		System.out.println("distance max");
        		System.out.println(distanceMax);
        		System.out.println("distance otal max");
        		System.out.println(distanceTotalMax);
        		distanceTotalMax = distanceMax;
        		System.out.println("b");
        		System.out.println(listeIndices);
        		listeFinale = listeIndices;
        	}
        }
        System.out.println(listeFinale);
        for (int i=0; i<listeFinale.size(); i++) {
        	notifyNodeMarked(listeNoeuds.get(listeFinale.get(i)));
        }
        System.out.println(cmpt);
        return new ShortestPathSolution(data, Status.INFEASIBLE);
	}
	   public ShortestPathSolution getDoRun() {
	    	return this.doRun();
	    }
}
