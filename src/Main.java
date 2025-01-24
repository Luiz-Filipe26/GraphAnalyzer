import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o conjunto de arestas: ");
        var graph = getEdgesFromInput(input);

        if (graph == null) {
            System.out.println("Entrada inválida!");
            return;
        }

        System.out.println("Entrada válida!");
        System.out.println("Arestas: " + graph);

        var connectedComponents = getConnectedComponents(graph);
        System.out.println("Componentes conexos: ");

        int count = 0;
        for (var connectedComponent : connectedComponents) {
            count++;
            System.out.println("Grafo " + count + ": " + connectedComponent);
        }

        if(connectedComponents.size() == 1 && isEulerian(graph)) {
            System.out.println("O grafo é euleriano!");
        }
        else {
            System.out.println("O grafo não é euleriano!");
        }

        if(isComplete(graph)) {
            System.out.println("O grafo é completo!");
        }
        else {
            System.out.println("O grafo não é completo!");
        }
    }

    private static boolean isComplete(Graph graph) {
        var vertices = graph.getVerticesCopy();
        int numOfVertices = vertices.size();

        return vertices.stream()
                .allMatch(vertice -> graph.getAdjacenciesCopy(vertice).size() == numOfVertices - 1);
    }

    private static boolean isEulerian(Graph graph) {
        return graph.getVerticesCopy().stream()
                .noneMatch(vertice -> graph.getAdjacenciesCopy(vertice).size() % 2 != 0);
    }

    private static List<Graph> getConnectedComponents(Graph graph) {
        Queue<Vertice> graphVertices = new LinkedList<>();
        Set<Vertice> pendingVertices = graph.getVerticesCopy();
        List<Graph> graphs = new ArrayList<>();

        Graph currentGraph = null;

        while (!graphVertices.isEmpty() || !pendingVertices.isEmpty()) {
            if (graphVertices.isEmpty()) {
                currentGraph = new Graph();
                graphs.add(currentGraph);

                var vertice = pendingVertices.iterator().next();
                pendingVertices.remove(vertice);
                graphVertices.offer(vertice);
            } else {
                var vertice = graphVertices.poll();
                pendingVertices.remove(vertice);

                var adjacentVertices = graph.getAdjacenciesCopy(vertice)
                        .stream()
                        .filter(pendingVertices::contains)
                        .toList();

                for (var adjacentVertice : adjacentVertices) {
                    currentGraph.addEdge(new Edge(vertice, adjacentVertice));
                    graphVertices.offer(adjacentVertice);
                }
            }
        }

        return graphs;
    }

    private static Graph getEdgesFromInput(Scanner input) {
        String inputLine = input.nextLine();

        if (!inputLine.matches("((\\[[^,\\[\\]]+,[^,\\[\\]]+\\]).*)*")) {
            return null;
        }

        Pattern edgePattern = Pattern.compile("\\[([^,\\[\\]]+),([^,\\[\\]]+)\\]");
        Matcher matcher = edgePattern.matcher(inputLine);

        Graph graph = new Graph();
        while (matcher.find()) {
            var v1 = new Vertice(matcher.group(1).trim());
            var v2 = new Vertice(matcher.group(2).trim());

            graph.addEdge(new Edge(v1, v2));
        }

        return graph;
    }
}
