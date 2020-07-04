package org.apd.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    public static class Edge {

        private Character begin, end;
        private int weight;

        public Edge(Character start, Character stop, int weight) {
            this.begin = start;
            this.end = stop;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return begin + " " + end + " " + weight;
        }
    }

    private List<Edge> edgesList;
    private List<Character> vertexes;

    public Graph() {
        edgesList = new ArrayList<>();
        vertexes = new LinkedList<>();
    }

    public void addEdge(Character firstTopName, Character secondTopName, int weight) {
        edgesList.add(new Edge(firstTopName, secondTopName, weight));
        if(!vertexes.contains(firstTopName)) vertexes.add(firstTopName);
        if(!vertexes.contains(secondTopName)) vertexes.add(secondTopName);
    }


    public List<Edge> findMinimumSpanningTree(){
        List<Edge> minimumSpanningTree = new ArrayList<>();
        List<Edge> notUsedEdges = new ArrayList<>(edgesList);
        List<Character> usedVertexes = new LinkedList<>();
        List<Character> notUsedVertexes = new LinkedList<>(vertexes);
        usedVertexes.add(notUsedVertexes.remove(0));
        while (notUsedVertexes.size() > 0) {
            int minE = -1;
            for (int i = 0; i < notUsedEdges.size(); i++) {
                if(((usedVertexes.indexOf(notUsedEdges.get(i).begin) != -1) &&
                        (notUsedVertexes.indexOf(notUsedEdges.get(i).end) != -1)) ||
                        ((usedVertexes.indexOf(notUsedEdges.get(i).end) != -1) &&
                                (notUsedVertexes.indexOf(notUsedEdges.get(i).begin) != -1))){
                    if (minE != -1){
                        if(notUsedEdges.get(i).weight < notUsedEdges.get(minE).weight)
                            minE = i;
                    }
                    else
                        minE = i;
                }

            }

            if (usedVertexes.indexOf(notUsedEdges.get(minE).begin) != -1){
                usedVertexes.add(notUsedEdges.get(minE).end);
                notUsedVertexes.remove(notUsedEdges.get(minE).end);
            }
            else {
                usedVertexes.add(notUsedEdges.get(minE).begin);
                notUsedVertexes.remove(notUsedEdges.get(minE).begin);
            }
            minimumSpanningTree.add(notUsedEdges.get(minE));
            notUsedEdges.remove(minE);
        }
        return minimumSpanningTree;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for(Edge edge : edgesList) {
            sb.append(edge.toString()).append("\n");
        }
        return sb.toString();
    }
}
