import ObjectSet from './ObjectSet.js';
import ObjectMap from './ObjectMap.js';

export class Vertice {
    constructor(name) {
        this.name = name;
    }

    toString() {
        return `[v: ${this.name}]`;
    }

    toStringId() {
        return this.name;
    }
}

export class Edge {
    constructor(vertex1, vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    toString() {
        return `[v1: ${this.vertex1.name}, v2: ${this.vertex2.name}]`;
    }

    toStringId() {
        return `${this.vertex1.toStringId()}-${this.vertex2.toStringId()}`;
    }
}

export class Graph {
    constructor() {
        this.edges = new ObjectSet();
        this.vertices = new ObjectSet();
        this.adjacencyMap = new ObjectMap();
    }

    addEdge(edge) {
        this.edges.add(edge);
        this.updateVerticesAndConnections(edge);
    }

    removeEdge(edge) {
        this.edges.delete(edge);
        this.updateVerticesAndConnectionsAfterRemoval(edge);
    }

    getAdjacenciesCopy(vertice) {
        const connections = this.adjacencyMap.get(vertice);
        return connections ? connections.clone() : new ObjectSet();
    }

    addVertice(vertice) {
        this.vertices.add(vertice);
    }

    removeVertice(vertice) {
        this.vertices.delete(vertice);
        this.adjacencyMap.delete(vertice);

        for (const connectedVertices of this.adjacencyMap.values()) {
            connectedVertices.delete(vertice);
        }
    }

    getVerticesCopy() {
        return this.vertices.clone();
    }

    getEdgesCopy() {
        return this.edges.clone();
    }

    toString() {
        return [...this.edges].toString();
    }

    updateVerticesAndConnections(edge) {
        this.vertices.add(edge.vertex1);
        this.vertices.add(edge.vertex2);

        if (!this.adjacencyMap.has(edge.vertex1)) {
            this.adjacencyMap.set(edge.vertex1, new ObjectSet());
        }
        this.adjacencyMap.get(edge.vertex1).add(edge.vertex2);

        if (!this.adjacencyMap.has(edge.vertex2)) {
            this.adjacencyMap.set(edge.vertex2, new ObjectSet());
        }
        this.adjacencyMap.get(edge.vertex2).add(edge.vertex1);
    }

    updateVerticesAndConnectionsAfterRemoval(edge) {
        const removeIfEmpty = (map, key, value) => {
            const set = map.get(key);
            if (set) {
                set.delete(value);
                if (set.size() === 0) map.delete(key);
            }
        };

        removeIfEmpty(this.adjacencyMap, edge.vertex1, edge.vertex2);
        removeIfEmpty(this.adjacencyMap, edge.vertex2, edge.vertex1);
    }
}
