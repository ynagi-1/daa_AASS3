package work;

import java.util.*;

public class Graph {
    private int vertices;
    private List<Edge> edges;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int source, int destination, int weight) {
        if (source < 0 || source >= vertices || destination < 0 || destination >= vertices) {
            throw new IllegalArgumentException("Invalid vertex index");
        }
        edges.add(new Edge(source, destination, weight));
    }

    public int getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public boolean isConnected() {
        if (vertices == 0) return true;

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
        }

        for (Edge edge : edges) {
            adj.get(edge.getSource()).add(edge.getDestination());
            adj.get(edge.getDestination()).add(edge.getSource());
        }

        boolean[] visited = new boolean[vertices];
        dfs(0, adj, visited);

        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }

    private void dfs(int vertex, List<List<Integer>> adj, boolean[] visited) {
        visited[vertex] = true;
        for (int neighbor : adj.get(vertex)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adj, visited);
            }
        }
    }
}