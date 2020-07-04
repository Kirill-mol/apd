package org.apd.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AlgorithmAPD {
    private Graph graph;

    private List<Edge> mst = new ArrayList<>();
    private List<Edge> notUsedEdges = new ArrayList<>();
    private List<Character> usedVertexes = new ArrayList<>();
    private List<Character> notUsedVertexes = new ArrayList<>();
    private List<Edge> minimumSpanningTree = new ArrayList<>();

    private boolean isInitializedNotUsedLists = false;

    private void initialize(){
        if (!isInitializedNotUsedLists){
            notUsedEdges = new ArrayList<>(graph.getEdgesList());
            notUsedVertexes = new ArrayList<>(graph.getVertexesList());
            usedVertexes.add(notUsedVertexes.remove(0));
            isInitializedNotUsedLists = true;
        }

    }

    public AlgorithmAPD(Graph graph) {
        this.graph = graph;
    }

    public void removeEdge(Edge edge) {
        graph.removeEdge(edge);
    }

    public void removeVertex(Character vertex) {
        graph.removeVertex(vertex);
    }

    public void addEdge(Edge edge) {
        graph.addEdge(edge);
    }

    public void readGraphFromFile(File file) throws FileNotFoundException {
        var scanner = new Scanner(file).useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()){
            String[] curLine = scanner.nextLine().split(" ");
            Edge edge = new Edge(curLine[0].charAt(0), curLine[1].charAt(0), Integer.parseInt(curLine[2]));
            addEdge(edge);
        }
    }

    public List<Edge> result() {
        initialize();
        while (notUsedVertexes.size() > 0) {
            newEdgeAtMst();
        }
        return minimumSpanningTree;
    }

    public Edge newEdgeAtMst() {
        initialize();
        var result = new Edge('a', 'a', -1);
        if(notUsedVertexes.size() > 0) {
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
            result = notUsedEdges.get(minE);
            notUsedEdges.remove(minE);
        }
        return result;
    }
}
