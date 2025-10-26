package work;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("City Transportation Network Optimization - MST Algorithms");
        System.out.println("=========================================================\n");

        // Read graphs from JSON file
        System.out.println("Reading graphs from assign_3_input.json...");
        List<Graph> graphs = JSONFileReader.readGraphsFromFile("input.json");

        if (graphs.isEmpty()) {
            System.err.println("No graphs were loaded. Please check the input file.");
            return;
        }

        System.out.println("Successfully loaded " + graphs.size() + " graphs from file\n");

        List<TestResult> results = new ArrayList<>();

        // Test each graph with both algorithms
        for (int i = 0; i < graphs.size(); i++) {
            Graph graph = graphs.get(i);
            System.out.println("=== Testing Graph " + (i + 1) + " ===");
            System.out.println("Vertices: " + graph.getVertices() + ", Edges: " + graph.getEdges().size());
            System.out.println("Connected: " + graph.isConnected());

            PrimMST prim = new PrimMST(graph);
            KruskalMST kruskal = new KruskalMST(graph);

            // Run Prim's algorithm
            System.out.println("\nRunning Prim's algorithm...");
            prim.findMST();
            System.out.println("Prim - Cost: " + prim.getTotalCost() + ", Time: " +
                    prim.getExecutionTime() + "ms, Operations: " + prim.getOperationCount() +
                    ", MST Edges: " + prim.getMstEdges().size());

            // Run Kruskal's algorithm
            System.out.println("Running Kruskal's algorithm...");
            kruskal.findMST();
            System.out.println("Kruskal - Cost: " + kruskal.getTotalCost() + ", Time: " +
                    kruskal.getExecutionTime() + "ms, Operations: " + kruskal.getOperationCount() +
                    ", MST Edges: " + kruskal.getMstEdges().size());

            TestResult result = new TestResult(graph, prim, kruskal);
            results.add(result);

            System.out.println("Costs match: " + result.costsMatch);
            System.out.println("Time difference: " + result.timeDifference + "ms");
            System.out.println("✓ Completed Graph " + (i + 1) + "\n");
        }

        // Generate JSON report
        System.out.println("Generating JSON report...");
        JSONReportGenerator.generateReport(results, "output.json");

        // Generate CSV summary
        System.out.println("Generating CSV summary...");
        generateCSVSummary(results, "summary.csv");

        System.out.println("\n=== ALL TESTS COMPLETED ===");
        printFinalSummary(results);
    }

    private static void generateCSVSummary(List<TestResult> results, String filename) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            // Header
            writer.println("Graph,Vertices,Edges,IsConnected,PrimCost,KruskalCost,PrimTimeMs,KruskalTimeMs,PrimOperations,KruskalOperations,CostsMatch,TimeDifferenceMs");

            // Data rows
            for (int i = 0; i < results.size(); i++) {
                TestResult result = results.get(i);
                writer.printf("%d,%d,%d,%s,%d,%d,%d,%d,%d,%d,%s,%d%n",
                        i + 1,
                        result.vertices,
                        result.edges,
                        result.isConnected,
                        result.primCost,
                        result.kruskalCost,
                        result.primTime,
                        result.kruskalTime,
                        result.primOperations,
                        result.kruskalOperations,
                        result.costsMatch,
                        result.timeDifference
                );
            }

            System.out.println("CSV summary saved to: " + filename);
        } catch (java.io.IOException e) {
            System.err.println("Error saving CSV: " + e.getMessage());
        }
    }

    private static void printFinalSummary(List<TestResult> results) {
        int primWins = 0, kruskalWins = 0, ties = 0;
        int totalGraphs = results.size();
        int connectedGraphs = 0;

        for (TestResult result : results) {
            if (result.isConnected) connectedGraphs++;
            if (result.timeDifference < 0) primWins++;
            else if (result.timeDifference > 0) kruskalWins++;
            else ties++;
        }

        System.out.println("Total graphs tested: " + totalGraphs);
        System.out.println("Connected graphs: " + connectedGraphs);
        System.out.println("Disconnected graphs: " + (totalGraphs - connectedGraphs));
        System.out.println("Prim faster: " + primWins + " graphs");
        System.out.println("Kruskal faster: " + kruskalWins + " graphs");
        System.out.println("Ties: " + ties + " graphs");
        System.out.println("All costs matched: " + results.stream().allMatch(r -> r.costsMatch));
    }
}