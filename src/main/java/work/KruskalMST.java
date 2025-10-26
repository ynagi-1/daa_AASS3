package work;

import java.util.*;

public class KruskalMST {
    private Graph graph;
    private List<Edge> mstEdges;
    private int totalCost;
    private long executionTime;
    private int operationCount;

    public KruskalMST(Graph graph) {
        this.graph = graph;
        this.mstEdges = new ArrayList<>();
    }

    public void findMST() {
        long startTime = System.nanoTime();
        operationCount = 0;

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);
        operationCount += edges.size();

        UnionFind uf = new UnionFind(graph.getVertices());

        for (Edge edge : edges) {
            operationCount++;
            if (mstEdges.size() == graph.getVertices() - 1) break;

            int rootU = uf.find(edge.getSource());
            int rootV = uf.find(edge.getDestination());
            operationCount += 2;

            if (rootU != rootV) {
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                uf.union(rootU, rootV);
                operationCount += 3;
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

    private static class UnionFind {
        private int[] parent;
        private int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }
}