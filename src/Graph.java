import java.util.*;

public class Graph {
    private final List<Edge> edges;
    private final Set<Vertice> vertices;
    private final Map<Vertice, Set<Vertice>> adjacencyMap;

    public Graph() {
        this.edges = new ArrayList<>();
        this.vertices = new HashSet<>();
        this.adjacencyMap = new HashMap<>();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        updateVerticesAndConnections(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
        updateVerticesAndConnectionsAfterRemoval(edge);
    }

    public Set<Vertice> getAdjacenciesCopy(Vertice vertice) {
        return new HashSet<>(adjacencyMap.getOrDefault(vertice, Collections.emptySet()));
    }

    public void addVertice(Vertice vertice) {
        vertices.add(vertice);
    }

    public void removeVertice(Vertice vertice) {
        vertices.remove(vertice);
        adjacencyMap.remove(vertice);

        for (Set<Vertice> connectedVertices : adjacencyMap.values()) {
            connectedVertices.remove(vertice);
        }
    }

    public Set<Vertice> getVerticesCopy() {
        return new HashSet<>(vertices);
    }

    public List<Edge> getEdgesCopy() {
        return new ArrayList<>(edges);
    }

    public Iterable<Vertice> verticesIterable() {
        return vertices::iterator;
    }

    public Iterable<Edge> edgesIterable() {
        return edges::iterator;
    }

    public Edge getEdge(int index) {
        return edges.get(index);
    }

    public int edgesSize() {
        return edges.size();
    }

    public boolean edgesIsEmpty() {
        return edges.isEmpty();
    }

    @Override
    public String toString() {
        return edges.toString();
    }

    private void updateVerticesAndConnections(Edge edge) {
        vertices.add(edge.vertex1());
        vertices.add(edge.vertex2());

        adjacencyMap
                .computeIfAbsent(edge.vertex1(), k -> new HashSet<>())
                .add(edge.vertex2());

        adjacencyMap
                .computeIfAbsent(edge.vertex2(), k -> new HashSet<>())
                .add(edge.vertex1());
    }

    private void updateVerticesAndConnectionsAfterRemoval(Edge edge) {
        vertices.remove(edge.vertex1());
        vertices.remove(edge.vertex2());

        Set<Vertice> connectedToV1 = adjacencyMap.get(edge.vertex1());
        Set<Vertice> connectedToV2 = adjacencyMap.get(edge.vertex2());

        if (connectedToV1 != null) {
            connectedToV1.remove(edge.vertex2());
            if (connectedToV1.isEmpty()) {
                adjacencyMap.remove(edge.vertex1());
            }
        }

        if (connectedToV2 != null) {
            connectedToV2.remove(edge.vertex1());
            if (connectedToV2.isEmpty()) {
                adjacencyMap.remove(edge.vertex2());
            }
        }
    }
}