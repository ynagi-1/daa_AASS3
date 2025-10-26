package work;

import java.io.*;
import java.util.*;

public class JSONFileReader {

    public static List<Graph> readGraphsFromFile(String filename) {
        List<Graph> graphs = new ArrayList<>();

        try {
            File file = new File(filename);
            System.out.println("Looking for file: " + file.getAbsolutePath());
            System.out.println("File exists: " + file.exists());

            if (!file.exists()) {
                // Попробуем найти файл в других местах
                File[] possiblePaths = {
                        new File("input.json"),
                        new File("src/input.json"),
                        new File("./src/input.json"),
                        new File("src/main/java/input.json")
                };

                for (File path : possiblePaths) {
                    System.out.println("Trying: " + path.getAbsolutePath());
                    if (path.exists()) {
                        file = path;
                        System.out.println("Found file at: " + path.getAbsolutePath());
                        break;
                    }
                }
            }

            if (!file.exists()) {
                System.err.println("File not found: " + filename);
                return graphs;
            }

            Scanner scanner = new Scanner(file);
            StringBuilder jsonContent = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonContent.append(scanner.nextLine());
            }
            scanner.close();

            String content = jsonContent.toString();
            System.out.println("File content (first 500 chars): " + content.substring(0, Math.min(500, content.length())));

            // Простой парсинг JSON
            parseSimpleJSON(content, graphs);

        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return graphs;
    }

    private static void parseSimpleJSON(String content, List<Graph> graphs) {
        try {
            int graphStart = content.indexOf("\"graphs\"");
            if (graphStart == -1) {
                System.err.println("No 'graphs' found in JSON");
                return;
            }

            int arrayStart = content.indexOf("[", graphStart);
            if (arrayStart == -1) {
                System.err.println("No array start found after 'graphs'");
                return;
            }

            int arrayEnd = findMatchingBracket(content, arrayStart);
            if (arrayEnd == -1) {
                System.err.println("No matching bracket found for graphs array");
                return;
            }

            String graphsArray = content.substring(arrayStart + 1, arrayEnd);
            System.out.println("Graphs array length: " + graphsArray.length());

            // Разделяем на отдельные графы
            List<String> graphStrings = splitGraphs(graphsArray);
            System.out.println("Found " + graphStrings.size() + " graph objects");

            for (String graphStr : graphStrings) {
                try {
                    Graph graph = parseGraphObject(graphStr);
                    if (graph != null) {
                        graphs.add(graph);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing graph: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.err.println("Error in parseSimpleJSON: " + e.getMessage());
        }
    }

    private static List<String> splitGraphs(String graphsArray) {
        List<String> graphs = new ArrayList<>();
        int depth = 0;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < graphsArray.length(); i++) {
            char c = graphsArray.charAt(i);

            if (c == '{') {
                depth++;
            } else if (c == '}') {
                depth--;
            }

            if (depth > 0) {
                current.append(c);
            }

            if (depth == 0 && current.length() > 0) {
                graphs.add(current.toString());
                current = new StringBuilder();
                // Пропускаем запятую если есть
                if (i + 1 < graphsArray.length() && graphsArray.charAt(i + 1) == ',') {
                    i++;
                }
            }
        }

        return graphs;
    }

    private static Graph parseGraphObject(String graphStr) {
        try {
            // Извлекаем vertices
            int vertices = extractValueInt(graphStr, "vertices");
            if (vertices == 0) {
                System.err.println("No vertices found");
                return null;
            }

            Graph graph = new Graph(vertices);

            // Извлекаем edges
            int edgesStart = graphStr.indexOf("\"edges\"");
            if (edgesStart == -1) {
                System.err.println("No edges found");
                return graph;
            }

            int edgesArrayStart = graphStr.indexOf("[", edgesStart);
            int edgesArrayEnd = findMatchingBracket(graphStr, edgesArrayStart);

            if (edgesArrayStart == -1 || edgesArrayEnd == -1) {
                System.err.println("Invalid edges array");
                return graph;
            }

            String edgesArray = graphStr.substring(edgesArrayStart + 1, edgesArrayEnd);
            parseEdgesArray(edgesArray, graph);

            System.out.println("Parsed graph: " + vertices + " vertices, " + graph.getEdges().size() + " edges");
            return graph;

        } catch (Exception e) {
            System.err.println("Error parsing graph object: " + e.getMessage());
            return null;
        }
    }

    private static void parseEdgesArray(String edgesArray, Graph graph) {
        List<String> edgeStrings = splitObjects(edgesArray);

        for (String edgeStr : edgeStrings) {
            try {
                int source = extractValueInt(edgeStr, "source");
                int destination = extractValueInt(edgeStr, "destination");
                int weight = extractValueInt(edgeStr, "weight");

                graph.addEdge(source, destination, weight);
            } catch (Exception e) {
                System.err.println("Error parsing edge: " + e.getMessage());
            }
        }
    }

    private static List<String> splitObjects(String array) {
        List<String> objects = new ArrayList<>();
        int depth = 0;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < array.length(); i++) {
            char c = array.charAt(i);

            if (c == '{') {
                depth++;
            } else if (c == '}') {
                depth--;
            }

            if (depth > 0) {
                current.append(c);
            }

            if (depth == 0 && current.length() > 0) {
                objects.add("{" + current.toString() + "}");
                current = new StringBuilder();
                // Пропускаем запятую если есть
                if (i + 1 < array.length() && array.charAt(i + 1) == ',') {
                    i++;
                }
            }
        }

        return objects;
    }

    private static int extractValueInt(String json, String key) {
        try {
            String pattern = "\"" + key + "\":";
            int keyIndex = json.indexOf(pattern);
            if (keyIndex == -1) return 0;

            int valueStart = keyIndex + pattern.length();
            int valueEnd = json.indexOf(",", valueStart);
            if (valueEnd == -1) valueEnd = json.indexOf("}", valueStart);
            if (valueEnd == -1) valueEnd = json.length();

            String valueStr = json.substring(valueStart, valueEnd).trim();
            // Убираем кавычки если есть
            valueStr = valueStr.replace("\"", "");

            return Integer.parseInt(valueStr);
        } catch (Exception e) {
            System.err.println("Error extracting " + key + " from: " + json);
            return 0;
        }
    }

    private static int findMatchingBracket(String str, int startIndex) {
        int depth = 1;
        for (int i = startIndex + 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '[' || c == '{') {
                depth++;
            } else if (c == ']' || c == '}') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
}