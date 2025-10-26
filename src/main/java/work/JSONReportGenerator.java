package work;

import java.io.*;
import java.util.*;

public class JSONReportGenerator {

    public static void generateReport(List<TestResult> results, String filename) {
        if (results.isEmpty()) {
            System.err.println("No results to generate report");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("{");
            writer.println("  \"mstComparisonResults\": [");

            for (int i = 0; i < results.size(); i++) {
                TestResult result = results.get(i);
                writer.println("    {");
                writer.println("      \"graphId\": " + (i + 1) + ",");
                writer.println("      \"vertices\": " + result.vertices + ",");
                writer.println("      \"edges\": " + result.edges + ",");
                writer.println("      \"isConnected\": " + result.isConnected + ",");

                writer.println("      \"primAlgorithm\": {");
                writer.println("        \"totalCost\": " + result.primCost + ",");
                writer.println("        \"executionTimeMs\": " + result.primTime + ",");
                writer.println("        \"operationCount\": " + result.primOperations + ",");
                writer.println("        \"mstEdgesCount\": " + result.primEdgesCount);
                writer.println("      },");

                writer.println("      \"kruskalAlgorithm\": {");
                writer.println("        \"totalCost\": " + result.kruskalCost + ",");
                writer.println("        \"executionTimeMs\": " + result.kruskalTime + ",");
                writer.println("        \"operationCount\": " + result.kruskalOperations + ",");
                writer.println("        \"mstEdgesCount\": " + result.kruskalEdgesCount);
                writer.println("      },");

                writer.println("      \"comparison\": {");
                writer.println("        \"costsMatch\": " + result.costsMatch + ",");
                writer.println("        \"timeDifferenceMs\": " + result.timeDifference);
                writer.println("      }");

                writer.print("    }");
                if (i < results.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }

            writer.println("  ]");
            writer.println("}");

            System.out.println("JSON report successfully saved to: " + filename);

        } catch (IOException e) {
            System.err.println("Error saving JSON report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}