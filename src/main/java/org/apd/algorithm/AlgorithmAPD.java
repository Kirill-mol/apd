package org.apd.algorithm;

import javafx.scene.control.TextArea;
import org.apd.ApplicationHandler;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgorithmAPD implements Observer{
    private static final Logger LOGGER = Logger.getLogger(AlgorithmAPD.class.getName());

    private Graph graph;

    private List<Edge> notUsedEdges = new ArrayList<>();
    private List<Vertex> usedVertexes = new ArrayList<>();
    private List<Vertex> notUsedVertexes = new ArrayList<>();
    private List<Edge> minimumSpanningTree = new ArrayList<>();

    private boolean isInitializedNotUsedLists = false;
    private boolean isConnectivityChecked = false;

    public AlgorithmAPD(Graph graph, TextArea textArea) {
        this.graph = graph;
        LOGGER.setLevel(Level.INFO);
        Handler handler = new ApplicationHandler(textArea);
        LOGGER.addHandler(handler);
    }
    public AlgorithmAPD(Graph graph) {
        this.graph = graph;
    }

    private void initialize() {
        if (!isInitializedNotUsedLists) {
            LOGGER.log(Level.INFO, "Initialize arrays before algorithm");
            notUsedEdges = new ArrayList<>(graph.getEdgesList());
            notUsedEdges.removeIf(edge -> edge.getBegin().equals(edge.getEnd()));
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
        List<Vertex> vertexesList = graph.getVertexesList();
        Stack<Vertex> stackForDFS = new Stack<>();
        stackForDFS.push(vertexesList.get(0));
        LOGGER.log(Level.CONFIG, "Start DFS, checking graph connectivity.");
        while (!stackForDFS.empty()) {
            Vertex curVertex = stackForDFS.pop();
            checkedVertexesList[vertexesList.indexOf(curVertex)] = true;
            LOGGER.log(Level.CONFIG, "Check vertex: " + curVertex.toString() + " neighbours");
            for (Edge edge : edgesList) {
                if ((edge.getBegin().equals(curVertex)) && (!checkedVertexesList[vertexesList.indexOf(edge.getEnd())])) {
                    LOGGER.log(Level.CONFIG, "Add vertex: " + edge.getEnd().toString() + " to stack");
                    stackForDFS.push(edge.getEnd());
                }
                if ((edge.getEnd().equals(curVertex)) && (!checkedVertexesList[vertexesList.indexOf(edge.getBegin())])) {
                    LOGGER.log(Level.CONFIG, "Add vertex: " + edge.getBegin().toString() + " to stack");
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
        graph.clear();
    }

    public void addEdge(Edge edge) throws Exception {
        if (!graph.addEdge(edge)) {
            LOGGER.log(Level.WARNING, "Try to add edge: " +  edge.toString());
            throw new Exception("edge already exist");
        }
    }

    public List<Edge> result() throws Exception {
        initialize();
        if (!graphConnectivityCheck()) {
            throw new Exception("graph isn't connected");
        }
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
        var result = new Edge(new Vertex("a"), new Vertex("a"), -1);
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
            LOGGER.log(Level.INFO, "Add edge: " + result.toString() + " to MST");
            notUsedEdges.remove(minE);
        } else {
            throw new Exception("all vertexes added");
        }
        return result;
    }

    @Override
    public void updateNotify(boolean isOn) {
        if(!isOn) {
            LOGGER.setLevel(Level.OFF);
        }
        else {
            LOGGER.setLevel(Level.ALL);
        }
    }
}
