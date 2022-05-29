
package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
		this.k = 4;
		// TODO Auto-generated constructor stub
	}
//Do run de l'aglo k means	
	
	protected ShortestPathSolution doRun() {
		Random random = new Random();
		final ShortestPathData data = getInputData();
        Graph graphe = this.data.getGraph();
        Node noeudOrigine = data.getOrigin();
        Node noeudTerminal = data.getDestination();
        List<Node> listeNoeuds = graphe.getNodes();
        int k = this.k;
        int nombreNoeuds = graphe.size();
        System.out.println(nombreNoeuds*nombreNoeuds);
        float [][]matCout= new float[nombreNoeuds][nombreNoeuds];
        float length;
        int [] affectationGroupe = new int[graphe.size()];
        int[] noeudSol = new int[this.k];
//défini un seuil pour déterminer si les centres ont peu bougé
        float seuil = Math.abs(graphe.getGraphInformation().getBoundingBox().getBottomRightPoint().getLatitude()-graphe.getGraphInformation().getBoundingBox().getTopLeftPoint().getLatitude());
        seuil = seuil*40008000 /(2*360*k);
        System.out.println(seuil);
//Réalisation des n*n dijkstra
        for (int i = 0; i<nombreNoeuds; i++) {
        	for (int j = 0; j<nombreNoeuds; j++) {
	        	ShortestPathData datainput = new ShortestPathData(graphe, listeNoeuds.get(i), listeNoeuds.get(j),ArcInspectorFactory.getAllFilters().get(0));
	        	ShortestPathSolution path = new AStarAlgorithm(datainput).doRun();
	        	if (path.isFeasible()) {
	        		length = path.getPath().getLength();
	        	}
	        	else {
	        		length = 999999999;
	        	}
	        	matCout[i][j] = length;
        	}
        }
 //Initialisation des tableaux que nous allons utiliser
	float distanceChange = 999999999;
	float [] pointLatitude = new float[k];
	float [] pointLongitude = new float[k];
	for (int i=0; i<this.k; i++) {
		noeudSol[i] = random.nextInt(nombreNoeuds);
		pointLatitude[i] = listeNoeuds.get(noeudSol[i]).getPoint().getLatitude();
		pointLongitude[i] = listeNoeuds.get(noeudSol[i]).getPoint().getLongitude();
	}
	int plusProche = 0;
	float [] nouvelleLatitude = new float[k];
	float [] nouvelleLongitude = new float[k];
	int [] nbrDansLeGroupe = new int[k];
	int nbit = 0;
//boucle du pgm
	System.out.println("fin dijkstra avnt boucle pgm");
	do {
//Vérifie que les groupes ne sont pas trop petit, évite les problème de groupe isolés
		for (int i=0; i<k; i++) {
		//System.out.println(noeudSol[i]);
			if (nbrDansLeGroupe[i] <  nombreNoeuds/((k* k))) {
				noeudSol[i] = random.nextInt(nombreNoeuds);
				pointLatitude[i] = listeNoeuds.get(noeudSol[i]).getPoint().getLatitude();
				pointLongitude[i] = listeNoeuds.get(noeudSol[i]).getPoint().getLongitude();
			}
		nbrDansLeGroupe[i]=0;
		nouvelleLatitude[i] = 0;
		nouvelleLongitude[i]=0;
		}

		//System.out.println("fin tab \n");
//affectactation des noeuds aux différents groupes
		for (int i=0; i<nombreNoeuds; i++) {
			plusProche = 0;
			for (int j=0; j<k;j++) {
				if (matCout[i][noeudSol[j]]< matCout[i][noeudSol[plusProche]]) {
					plusProche = j;
				}
			}
			affectationGroupe[i] = plusProche;
		}
//Nouveaux Centres
		int gp;
		//Calcul le centre de chaque groupe
		//Moyenne géométrique
		for (int i=0; i<nombreNoeuds; i++) {
			gp = affectationGroupe[i];
			nbrDansLeGroupe[gp]++;
			nouvelleLatitude[gp] += Math.log(listeNoeuds.get(i).getPoint().getLatitude());
			nouvelleLongitude[gp] += Math.log(listeNoeuds.get(i).getPoint().getLongitude());
		}
		
		for (int i=0; i<k; i++) {
			//System.out.println("groupe ");
			//System.out.println(i);
			nouvelleLatitude[i] = (float) Math.exp(nouvelleLatitude[i]/nbrDansLeGroupe[i]);
			nouvelleLongitude[i] = (float) Math.exp(nouvelleLongitude[i]/nbrDansLeGroupe[i]);	
			//System.out.println(nouvelleLatitude[i]);
			//System.out.println(nouvelleLongitude[i]);
		}
//On choisit le noeud le plus proche du centre
		float distanceIdeale;
		float latitudeIdeale;
		float longitudeIdeale;
		float distanceIdeale2;
		float latitudeIdeale2;
		float longitudeIdeale2;
		distanceChange = 0;
		for (int i =0; i<nombreNoeuds; i++) {
			for (int j = 0; j< k; j++) {
				latitudeIdeale = listeNoeuds.get(i).getPoint().getLatitude()-nouvelleLatitude[j];
				longitudeIdeale= listeNoeuds.get(i).getPoint().getLongitude()-nouvelleLongitude[j];
				distanceIdeale = latitudeIdeale*latitudeIdeale + longitudeIdeale*longitudeIdeale;
				latitudeIdeale2 = pointLatitude[j]-nouvelleLatitude[j];
				longitudeIdeale2= pointLongitude[j]-nouvelleLongitude[j];
				distanceIdeale2 = latitudeIdeale2*latitudeIdeale2 + longitudeIdeale2*longitudeIdeale2;
				if (distanceIdeale<distanceIdeale2) {
					//System.out.println(distanceIdeale);
					pointLatitude[j] = listeNoeuds.get(i).getPoint().getLatitude();
					pointLongitude[j] = listeNoeuds.get(i).getPoint().getLongitude();
					distanceChange +=Float.min(matCout[i][noeudSol[j]],matCout[noeudSol[j]][i]);
					noeudSol[j] = i;
				}
			}
		}
		nbit ++;
		System.out.println("distan,ce change");
		System.out.println(distanceChange);
	} while (((distanceChange > seuil)&&(nbit < k*k*nombreNoeuds*nombreNoeuds)));
	float res = 0;
	for (int i=0; i<nombreNoeuds; i++) {
		plusProche = 0;
		for (int j=0; j<k;j++) {
			if (matCout[i][noeudSol[j]]< matCout[i][noeudSol[plusProche]]) {
				plusProche = j;
			}
		}
		if (res< matCout[i][noeudSol[plusProche]]) {
			res  = matCout[i][noeudSol[plusProche]];
		}
	}
	System.out.println("pire accessibilité");
	System.out.println(res);
	System.out.println(nbit);
	System.out.println(k*k*nombreNoeuds*nombreNoeuds);
    for (int i=0; i<this.k; i++) {
    	//System.out.println("nouveau guys");
    	//System.out.println(nbrDansLeGroupe[i]);
    	//System.out.println(nouvelleLatitude[i]);
    	//System.out.println(nouvelleLongitude[i]);
    	//System.out.println("les valeurs");
    	//System.out.println(nbrDansLeGroupe[i]);
    	//System.out.println(pointLatitude[i]);
    	//System.out.println(pointLongitude[i]);
    	//System.out.println("valeur du point central");
    	//System.out.println(nbrDansLeGroupe[i]);
    	//System.out.println(listeNoeuds.get(noeudSol[i]).getPoint().getLatitude());
    	//System.out.println(listeNoeuds.get(noeudSol[i]).getPoint().getLongitude());
    	notifyVendeur(listeNoeuds.get(noeudSol[i]));
    }
    return new ShortestPathSolution(data, Status.INFEASIBLE);
}

//Do run de l'algo brut force
/*
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
	        	ShortestPathSolution path = new AStarAlgorithm(datainput).doRun();
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
    		//System.out.println("liste push stack");
    		//System.out.println(listeIndices);
    		//System.out.println(stack);
    		stack.push(new ArrayList<Integer>(listeIndices));
    		cmpt++;
    	}
    	//System.out.println(stack);
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
        	//System.out.println("lise testée de taille k\n");
        	//System.out.println(listeIndices);
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
        		//System.out.println("distance max");
        		//System.out.println(distanceMax);
        		//System.out.println("distance otal max");
        		//System.out.println(distanceTotalMax);
        		distanceTotalMax = distanceMax;
        		//System.out.println("b");
        		//System.out.println(listeIndices);
        		listeFinale = listeIndices;
        	}
        }
        System.out.println(listeFinale);
        for (int i=0; i<listeFinale.size(); i++) {
        	notifyVendeur(listeNoeuds.get(listeFinale.get(i)));
        }
        System.out.println(cmpt);
        return new ShortestPathSolution(data, Status.INFEASIBLE);
	}
	*/
	   public ShortestPathSolution getDoRun() {
	    	return this.doRun();
	    }
}
