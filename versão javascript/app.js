import { Graph, Vertice, Edge } from "./Graph.js";

function isEulerian(graph) {
    return [...graph.getVerticesCopy()].every(vertice =>
        graph.getAdjacenciesCopy(vertice).size() % 2 === 0
    );
}

function getConnectedComponents(graph) {
    const graphVertices = [];
    const pendingVertices = graph.getVerticesCopy();
    const graphs = [];
    let currentGraph = null;

    while (graphVertices.length > 0 || pendingVertices.size() > 0) {
        if (graphVertices.length === 0) {
            currentGraph = new Graph();
            graphs.push(currentGraph);

            const vertice = pendingVertices.values().next().value;
            pendingVertices.delete(vertice);
            graphVertices.push(vertice);
        } else {
            const vertice = graphVertices.shift();
            pendingVertices.delete(vertice);

            for (const adjacentVertice of graph.getAdjacenciesCopy(vertice)) {
                if (pendingVertices.has(adjacentVertice)) {
                    currentGraph.addEdge(new Edge(vertice, adjacentVertice));
                    graphVertices.push(adjacentVertice);
                }
            }
        }
    }

    return graphs;
}


function getEdgesFromInput(inputLine) {
    if (!/((\[[^,\[\]]+,[^,\[\]]+\]).*)*/.test(inputLine)) {
        return null;
    }

    const regex = /\[(\w+),(\w+)\]/g;
    const rawEdges = [];
    
    let match;
    while ((match = regex.exec(inputLine)) !== null) {
        rawEdges.push(match.slice(1, 3));  // Pega o par de vértices
    }

    if (rawEdges.length === 0) {
        return null;
    }

    const graph = new Graph();
    for (const [v1, v2] of rawEdges) {
        const vertex1 = new Vertice(v1.trim());
        const vertex2 = new Vertice(v2.trim());
        graph.addEdge(new Edge(vertex1, vertex2));
    }

    return graph;
}

document.addEventListener("DOMContentLoaded", () => {
    const processButton = document.getElementById("process-button");
    const inputField = document.getElementById("edges-input");
    const outputArea = document.getElementById("output-area");

    processButton.addEventListener("click", () => {
        const input = inputField.value.trim();
        const graph = getEdgesFromInput(input);

        if (!graph) {
            outputArea.value = "Entrada inválida! Por favor, digite as arestas no formato correto.";
            return;
        }

        let output = "Entrada válida!\n";
        output += `Arestas: ${graph.toString()}\n\n`;

        const connectedComponents = getConnectedComponents(graph);
        output += "Componentes conexos:\n";
        connectedComponents.forEach((component, index) => {
            output += `Grafo ${index + 1}: ${component.toString()}\n`;
        });

        if (connectedComponents.length === 1 && isEulerian(graph)) {
            output += "\nO grafo é euleriano!";
        } else {
            output += "\nO grafo não é euleriano!";
        }

        outputArea.value = output;
    });
});