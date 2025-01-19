public record Edge(Vertice vertex1, Vertice vertex2) {
    @Override
    public String toString() {
        return "[v1: " + vertex1.name() + ", v2: " + vertex2.name() + "]";
    }
}