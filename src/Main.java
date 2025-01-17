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

        System.out.println("Entrada válida!");
        System.out.println("Arestas: " + edges);

        var connectedGraphs = getConnectedGraphs(vertices, edges);
        //System.out.println("Componentes conectados: " + connectedGraphs);
    }

    private static List<Vertice> getVerticesFromEdges(List<Edge> edges) {
        Set<Vertice> uniqueVertices = new HashSet<>();
        edges.forEach(edge -> uniqueVertices.addAll(List.of(edge.vertex1(), edge.vertex2())));

        return new ArrayList<>(uniqueVertices);
    }

    private static List<Graph> getConnectedGraphs(List<Vertice> vertices, List<Edge> edges) {
        for(var vertice : vertices) {

        }

        return null;
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