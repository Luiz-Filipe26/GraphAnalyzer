import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o conjunto de arestas: ");
        var edges = getEdgesFromInput(input);

        if (edges == null) {
            System.out.println("Entrada inválida!");
            return;
        }

        var vertices = getVerticesFromEdges(edges);
        var verticeToConnecteds = getVerticeToConnecteds(edges);

        System.out.println("Entrada válida!");
        System.out.println("Arestas: " + edges);

        var connectedGraphs = getConnectedComponents(vertices, verticeToConnecteds);
        System.out.println("Componentes conectados: " + connectedGraphs);
    }

    private static Map<Vertice, Set<Vertice>> getVerticeToConnecteds(List<Edge> edges) {
        Map<Vertice, Set<Vertice>> verticeToConnecteds = new HashMap<>();

        for(var edge : edges) {
            if (!verticeToConnecteds.containsKey(edge.vertex1())) {
                verticeToConnecteds.put(edge.vertex1(), new HashSet<>());
            }
            if (!verticeToConnecteds.containsKey(edge.vertex2())) {
                verticeToConnecteds.put(edge.vertex2(), new HashSet<>());
            }

            verticeToConnecteds.get(edge.vertex1()).add(edge.vertex2());
            verticeToConnecteds.get(edge.vertex2()).add(edge.vertex1());
        }

        return verticeToConnecteds;
    }

    private static Set<Vertice> getVerticesFromEdges(List<Edge> edges) {
        Set<Vertice> uniqueVertices = new HashSet<>();
        edges.forEach(edge -> uniqueVertices.addAll(List.of(edge.vertex1(), edge.vertex2())));

        return uniqueVertices;
    }

    private static List<Graph> getConnectedComponents(Set<Vertice> vertices, Map<Vertice, Set<Vertice>> verticeToConnecteds) {
        Queue<Vertice> graphVertices = new LinkedList<>();
        Queue<Vertice> pendingVertices = new LinkedList<>(vertices);
        List<Graph> graphs = new ArrayList<>();

        Graph currentGraph = null;

        while(!graphVertices.isEmpty() || !pendingVertices.isEmpty()) {
            if(graphVertices.isEmpty()) {
                currentGraph = new Graph();
                graphs.add(currentGraph);
                var vertice = pendingVertices.poll();

                graphVertices.offer(vertice);
            }
            else {
                var vertice = graphVertices.poll();
                var connectedVertices = verticeToConnecteds
                                            .get(vertice)
                                            .stream()
                                            .filter(pendingVertices::contains)
                                            .toList();
                for(var connectedVertice : connectedVertices) {
                    currentGraph.add(new Edge(vertice, connectedVertice));
                    pendingVertices.remove(connectedVertice);
                    graphVertices.offer(connectedVertice);
                }

            }


        }

        return graphs;
    }

    private static List<Edge> getEdgesFromInput(Scanner input) {
        String inputLine = input.nextLine();

        if (!inputLine.matches("((\\[[^,\\[\\]]+,[^,\\[\\]]+\\]).*)*")) {
            return null;
        }

        String[] rawEdges = inputLine.replaceAll("^\\[|\\]$", "")
                .split("\\]([^]])*\\[");

        List<Edge> edges = new ArrayList<>();
        for (var rawEdge : rawEdges) {
            String[] vertices = rawEdge.split(",");
            if (vertices.length != 2) {
                return null;
            }

            var v1 = new Vertice(vertices[0].trim());
            var v2 = new Vertice(vertices[1].trim());

            edges.add(new Edge(v1, v2));
        }

        return edges;
    }
}