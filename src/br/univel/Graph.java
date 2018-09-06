package br.univel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {

    private static final int UNDEFINED = -1;
    private int vertices[][];

    public Graph(int numberOfVertices) {
        vertices = new int[numberOfVertices][numberOfVertices];
    }

    public void makeEdge(int vertexOne, int vertexTwo, int time) {
        vertices[vertexOne][vertexTwo] = time;
        vertices[vertexTwo][vertexOne] = time;
    }

    public void removeEdge(int vertexOne, int vertexTwo) {
        vertices[vertexOne][vertexTwo] = 0;
        vertices[vertexTwo][vertexOne] = 0;
    }

    public int getCost(int vertexOne, int vertexTwo) {
        return vertices[vertexOne][vertexTwo];
    }

    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < vertices[vertex].length; i++) {
            if (vertices[vertex][i] > 0) {
                neighbors.add(i);
            }
        }

        return neighbors;
    }

    public List<Integer> path(int from, int to) {
        int cost[] = new int[vertices.length];
        int prev[] = new int[vertices.length];
        Set<Integer> unvisited = new HashSet<>();

        cost[from] = 0;

        for (int vertex = 0; vertex < vertices.length; vertex++) {
            if (vertex != from) {
                cost[vertex] = Integer.MAX_VALUE;
            }
            prev[vertex] = UNDEFINED;
            unvisited.add(vertex);
        }

        while (!unvisited.isEmpty()) {
            int near = closest(cost, unvisited);
            unvisited.remove(near);

            for (Integer neighbor : getNeighbors(near)) {
                int totalCost = cost[near] + getCost(near, neighbor);
                if (totalCost < cost[neighbor]) {
                    cost[neighbor] = totalCost;
                    prev[neighbor] = near;
                }
            }

            if (near == to) {
                return makePathList(prev, near);
            }
        }

        return Collections.emptyList();
    }

    private int closest(int[] distance, Set<Integer> unvisited) {
        double minimumDistance = Integer.MAX_VALUE;
        int minimumIndex = 0;

        for (Integer vertice : unvisited) {
            if (distance[vertice] < minimumDistance) {
                minimumDistance = distance[vertice];
                minimumIndex = vertice;
            }
        }

        return minimumIndex;
    }

    private List<Integer> makePathList(int[] previous, int last) {
        List<Integer> path = new ArrayList<>();
        path.add(last);

        while (previous[last] != UNDEFINED) {
            path.add(previous[last]);
            last = previous[last];
        }

        Collections.reverse(path);

        return path;
    }

}
