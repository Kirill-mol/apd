package org.apd.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    private List<Edge> edgesList;
    private List<Character> vertexesList;

    public Graph() {
        edgesList = new ArrayList<>();
        vertexesList = new LinkedList<>();
    }

    public void addEdge(Character firstTopName, Character secondTopName, int weight) {
        edgesList.add(new Edge(firstTopName, secondTopName, weight));
        if (!vertexesList.contains(firstTopName)) vertexesList.add(firstTopName);
        if (!vertexesList.contains(secondTopName)) vertexesList.add(secondTopName);
    }

    public void addEdge(Edge edge) {
        edgesList.add(edge);
        if (!vertexesList.contains(edge.getBegin())) vertexesList.add(edge.getBegin());
        if (!vertexesList.contains(edge.getEnd())) vertexesList.add(edge.getEnd());
    }

    public void clear() {
        edgesList.clear();
        vertexesList.clear();
    }

    public void removeEdge(Edge edge) {
        edgesList.remove(edge);
        boolean isContainFirstVertex = false;
        boolean isContainSecondVertex = false;
        for (Edge curEdge : edgesList) {
            if (curEdge.getBegin() == edge.getBegin() || curEdge.getEnd() == edge.getBegin())
                isContainFirstVertex = true;
            if (curEdge.getBegin() == edge.getEnd() || curEdge.getEnd() == edge.getEnd()) isContainSecondVertex = true;
        }
        if (!isContainFirstVertex) vertexesList.remove(edge.getBegin());
        if (!isContainSecondVertex) vertexesList.remove(edge.getEnd());
    }

    public void removeVertex(Character vertex) {
        vertexesList.remove(vertex);
        edgesList.removeIf(edge -> edge.getBegin() == vertex || edge.getEnd() == vertex);
    }

    public List<Edge> getEdgesList() {
        return edgesList;
    }

    public List<Character> getVertexesList() {
        return vertexesList;
    }

    /*public List<Edge> findMinimumSpanningTree() {
        List<Edge> minimumSpanningTree = new ArrayList<>();
        List<Edge> notUsedEdges = new ArrayList<>(edgesList);
        List<Character> usedVertexes = new LinkedList<>();
        List<Character> notUsedVertexes = new LinkedList<>(vertexesList);
        usedVertexes.add(notUsedVertexes.remove(0));
        while (notUsedVertexes.size() > 0) {
            int minE = -1;
            for (int i = 0; i < notUsedEdges.size(); i++) {
                if (((usedVertexes.indexOf(notUsedEdges.get(i).getBegin()) != -1) &&
                        (notUsedVertexes.indexOf(notUsedEdges.get(i).getEnd()) != -1)) ||
                        ((usedVertexes.indexOf(notUsedEdges.get(i).getEnd()) != -1) &&
                                (notUsedVertexes.indexOf(notUsedEdges.get(i).getBegin()) != -1))) {
                    if (minE != -1) {
                        if (notUsedEdges.get(i).getWeight() < notUsedEdges.get(minE).getWeight())
                            minE = i;
                    } else
                        minE = i;
                }

            }

            if (usedVertexes.indexOf(notUsedEdges.get(minE).getBegin()) != -1) {
                usedVertexes.add(notUsedEdges.get(minE).getEnd());
                notUsedVertexes.remove(notUsedEdges.get(minE).getEnd());
            } else {
                usedVertexes.add(notUsedEdges.get(minE).getBegin());
                notUsedVertexes.remove(notUsedEdges.get(minE).getBegin());
            }
            minimumSpanningTree.add(notUsedEdges.get(minE));
            notUsedEdges.remove(minE);
        }
        return minimumSpanningTree;
    }*/

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (Edge edge : edgesList) {
            sb.append(edge.toString()).append("\n");
        }
        return sb.toString();
    }
}
