package work;

public class TestResult {
    public int vertices;
    public int edges;
    public boolean isConnected;

    public int primCost;
    public long primTime;
    public int primOperations;
    public int primEdgesCount;

    public int kruskalCost;
    public long kruskalTime;
    public int kruskalOperations;
    public int kruskalEdgesCount;

    public boolean costsMatch;
    public long timeDifference;

    public TestResult(Graph graph, PrimMST prim, KruskalMST kruskal) {
        this.vertices = graph.getVertices();
        this.edges = graph.getEdges().size();
        this.isConnected = graph.isConnected();

        this.primCost = prim.getTotalCost();
        this.primTime = prim.getExecutionTime();
        this.primOperations = prim.getOperationCount();
        this.primEdgesCount = prim.getMstEdges().size();

        this.kruskalCost = kruskal.getTotalCost();
        this.kruskalTime = kruskal.getExecutionTime();
        this.kruskalOperations = kruskal.getOperationCount();
        this.kruskalEdgesCount = kruskal.getMstEdges().size();

        this.costsMatch = (primCost == kruskalCost);
        this.timeDifference = kruskalTime - primTime;
    }
}