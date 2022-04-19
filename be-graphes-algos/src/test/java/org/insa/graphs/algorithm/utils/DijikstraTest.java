package org.insa.graphs.algorithm.utils;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Random;

public class DijikstraTest {


    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;

    // Some paths...
    private static Path emptyPath, singleNodePath, shortPath, longPath, loopPath, longLoopPath,
            invalidPath;
    public static Graph graph;

    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
    	final String mapName = "/home/darthenu/toulouse.mapgr";
      

        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // TODO: Read the graph.
        graph = reader.read();
        System.out.println(graph.size());

    }

    @SuppressWarnings("deprecation")
	@Test
    public void testConstructor() {
    	int n1;
    	int n2; 
    	Node noeud1;
    	Node noeud2;
    	ShortestPathData data;
    	DijkstraAlgorithm dijkstra;
    	BellmanFordAlgorithm bellman;
    	ShortestPathSolution runDij;
    	ShortestPathSolution runBell;
    	Random random = new Random();
    	for (int i=0; i<1000; i++) {
    		n1 = random.nextInt(graph.size());	
    		System.out.println(n1);
    		n2  = random.nextInt(graph.size());
    		System.out.println(n2);
    		noeud1 = graph.getNodes().get(n1);
    		noeud2 = graph.getNodes().get(n2);
    		data = new ShortestPathData(graph, noeud1, noeud2, ArcInspectorFactory.getAllFilters().get(0));
    		dijkstra = new DijkstraAlgorithm(data);
    		bellman = new BellmanFordAlgorithm(data);
    		runDij = dijkstra.run();
    		runBell = bellman.run();
    		if (runDij.isFeasible() && (n1 != n2)) {
    			assertEquals(runDij.isFeasible(),runBell.isFeasible());
    			assertEquals(runDij.getPath().getLength(),runBell.getPath().getLength(),1);
    		}
    	}
    }

}
