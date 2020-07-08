package org.apd.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgorithmAPD {
    private static final Logger LOGGER = Logger.getLogger(AlgorithmAPD.class.getName());

    static {
        //LOGGER.setLevel(Level.OFF);
    }

    private Graph graph;

    private List<Edge> notUsedEdges = new ArrayList<>();
    private List<Character> usedVertexes = new ArrayList<>();
    private List<Character> notUsedVertexes = new ArrayList<>();
    private List<Edge> minimumSpanningTree = new ArrayList<>();

    private boolean isInitializedNotUsedLists = false;
    private boolean isConnectivityChecked = false;

    public AlgorithmAPD(Graph graph) {
        this.graph = graph;
    }

    public AlgorithmAPD() {
        this.graph = new Graph();
    }

    private void initialize() {
        if (!isInitializedNotUsedLists) {
            LOGGER.log(Level.INFO, "Initialize arrays before algorithm");
            notUsedEdges = new ArrayList<>(graph.getEdgesList());
            notUsedEdges.removeIf(edge -> edge.getBegin() == edge.getEnd());
            notUsedVertexes = new ArrayList<>(graph.getVertexesList());
            usedVertexes.add(notUsedVertexes.remove(0));
            isInitializedNotUsedLists = true;
        }

    }

    private boolean graphConnectivityCheck() {
        if (isConnectivityChecked) return true;
        LOGGER.log(Level.INFO, "Check graph connectivity");
        var checkedVertexesList = new boolean[graph.getVertexesList().size()];
        List<Edge> edgesList = graph.getEdgesList();
        List<Character> vertexesList = graph.getVertexesList();
        Stack<Character> stackForDFS = new Stack<>();
        stackForDFS.push(vertexesList.get(0));
        LOGGER.log(Level.CONFIG, "Start DFS, checking graph connectivity.");
        while (!stackForDFS.empty()) {
            Character curVertex = stackForDFS.pop();
            checkedVertexesList[vertexesList.indexOf(curVertex)] = true;
            LOGGER.log(Level.CONFIG, "Check vertex: '{0}' neighbours", curVertex);
            for (Edge edge : edgesList) {
                if ((edge.getBegin() == curVertex) && (!checkedVertexesList[vertexesList.indexOf(edge.getEnd())])) {
                    LOGGER.log(Level.CONFIG, "Add vertex: '{0}' to stack", edge.getEnd());
                    stackForDFS.push(edge.getEnd());
                }
                if ((edge.getEnd() == curVertex) && (!checkedVertexesList[vertexesList.indexOf(edge.getBegin())])) {
                    LOGGER.log(Level.CONFIG, "Add vertex: '{0}' to stack", edge.getBegin());
                    stackForDFS.push(edge.getBegin());
                }
            }
        }
        for (Boolean bool : checkedVertexesList) {
            if (!bool) {
                LOGGER.log(Level.INFO, "Graph isn't connected");
                return false;
            }
        }
        isConnectivityChecked = true;
        LOGGER.log(Level.INFO, "Graph is connected");
        return true;
    }

    public void clear() {
        LOGGER.log(Level.INFO, "Clear graph and all fields");
        notUsedEdges.clear();
        usedVertexes.clear();
        notUsedVertexes.clear();
        minimumSpanningTree.clear();
        isConnectivityChecked = false;
        isInitializedNotUsedLists = false;
        graph = new Graph();
    }

    public void removeEdge(Edge edge) {
        graph.removeEdge(edge);
    }

    public void removeVertex(Character vertex) {
        graph.removeVertex(vertex);
    }

    public void addEdge(Edge edge) throws Exception {
        if (!graph.addEdge(edge)) {
            LOGGER.log(Level.WARNING, "Try to add edge: '{0}'", edge.toString());
            throw new Exception("edge already exist");
        }
    }

    public void readGraphFromFile(File file) throws Exception {
        LOGGER.log(Level.INFO, "Read graph from file: {0}", file.getName());
        var scanner = new Scanner(file).useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()) {
            String[] curLine = scanner.nextLine().split(" ");
            Edge edge = new Edge(curLine[0].charAt(0), curLine[1].charAt(0), Integer.parseInt(curLine[2]));
            addEdge(edge);
        }
    }

    public List<Edge> result() throws Exception {
        initialize();
        /*if (!graphConnectivityCheck()) {
            throw new Exception("graph isn't connected");
        }*/
        LOGGER.log(Level.SEVERE, "Called function result, to find mst");
        while (notUsedVertexes.size() > 0) {
            nextEdgeAtMst();
        }
        return minimumSpanningTree;
    }

    public Edge nextEdgeAtMst() throws Exception {
        initialize();
        LOGGER.log(Level.INFO, "Called function to find next minimum edge for mst");
        if (!graphConnectivityCheck()){
            LOGGER.log(Level.WARNING, "Graph isn't connected");
            throw new Exception("graph isn't connected");
        }
        var result = new Edge('a', 'a', -1);
        if (notUsedVertexes.size() > 0) {
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
            LOGGER.log(Level.INFO, "Add edge: {0} to MST", result.toString());
            notUsedEdges.remove(minE);
        } else {
            throw new Exception("all vertexes added");
        }
        return result;
    }

    public List<Character> getUsedVertexes(){
        return usedVertexes;
    }

    public List<Edge> getMinimumSpanningTree(){
        return minimumSpanningTree;
    }
}
