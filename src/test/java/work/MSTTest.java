package work;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class MSTTest {

    @Test
    void testSmallGraphBothAlgorithms() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);

        PrimMST prim = new PrimMST(graph);
        KruskalMST kruskal = new KruskalMST(graph);

        prim.findMST();
        kruskal.findMST();


        assertEquals(prim.getTotalCost(), kruskal.getTotalCost());

        assertEquals(3, prim.getMstEdges().size());
        assertEquals(3, kruskal.getMstEdges().size());


        assertEquals(19, prim.getTotalCost());
    }

    @Test
    void testMSTProperties() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 3);
        graph.addEdge(2, 3, 4);
        graph.addEdge(2, 4, 2);
        graph.addEdge(3, 4, 3);
        graph.addEdge(3, 5, 2);
        graph.addEdge(4, 5, 1);

        PrimMST prim = new PrimMST(graph);
        prim.findMST();

        assertEquals(5, prim.getMstEdges().size());

        assertTrue(prim.getTotalCost() > 0);
    }

    @Test
    void testSingleVertex() {
        Graph graph = new Graph(1);

        PrimMST prim = new PrimMST(graph);
        KruskalMST kruskal = new KruskalMST(graph);

        prim.findMST();
        kruskal.findMST();

        assertEquals(0, prim.getTotalCost());
        assertEquals(0, kruskal.getTotalCost());
        assertEquals(0, prim.getMstEdges().size());
        assertEquals(0, kruskal.getMstEdges().size());
    }

    @Test
    void testTwoVertices() {
        Graph graph = new Graph(2);
        graph.addEdge(0, 1, 5);

        PrimMST prim = new PrimMST(graph);
        KruskalMST kruskal = new KruskalMST(graph);

        prim.findMST();
        kruskal.findMST();

        assertEquals(5, prim.getTotalCost());
        assertEquals(5, kruskal.getTotalCost());
        assertEquals(1, prim.getMstEdges().size());
        assertEquals(1, kruskal.getMstEdges().size());
    }

    @Test
    void testPerformanceMetrics() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 4);
        graph.addEdge(2, 3, 5);
        graph.addEdge(3, 4, 6);

        PrimMST prim = new PrimMST(graph);
        KruskalMST kruskal = new KruskalMST(graph);

        prim.findMST();
        kruskal.findMST();

        assertTrue(prim.getExecutionTime() >= 0);
        assertTrue(kruskal.getExecutionTime() >= 0);

        assertTrue(prim.getOperationCount() > 0);
        assertTrue(kruskal.getOperationCount() > 0);
    }
}