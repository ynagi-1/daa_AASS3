package work;

import java.util.*;

public class PrimMST {
    private Graph graph;
    private List<Edge> mstEdges;
    private int totalCost;
    private long executionTime;
    private int operationCount;

    public PrimMST(Graph graph) {
        this.graph = graph;
        this.mstEdges = new ArrayList<>();
    }

    public void findMST() {
        long startTime = System.nanoTime();
        operationCount = 0;

        int vertices = graph.getVertices();
        if (vertices == 0) {
            executionTime = (System.nanoTime() - startTime) / 1000000;
            return;
        }

        boolean[] inMST = new boolean[vertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        inMST[0] = true;
        operationCount++;

        for (Edge edge : graph.getEdges()) {
            operationCount++;
            if (edge.getSource() == 0 || edge.getDestination() == 0) {
                pq.add(edge);
                operationCount++;
            }
        }

        while (!pq.isEmpty() && mstEdges.size() < vertices - 1) {
            Edge edge = pq.poll();
            operationCount++;

            int u = edge.getSource();
            int v = edge.getDestination();

            if (inMST[u] && inMST[v]) {
                operationCount++;
                continue;
            }

            mstEdges.add(edge);
            totalCost += edge.getWeight();
            operationCount += 2;

            int newVertex = inMST[u] ? v : u;
            inMST[newVertex] = true;
            operationCount++;

            for (Edge e : graph.getEdges()) {
                operationCount++;
                if ((e.getSource() == newVertex && !inMST[e.getDestination()]) ||
                        (e.getDestination() == newVertex && !inMST[e.getSource()])) {
                    pq.add(e);
                    operationCount++;
                }
            }
        }

        executionTime = (System.nanoTime() - startTime) / 1000000;
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public int getOperationCount() {
        return operationCount;
    }
}