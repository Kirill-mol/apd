package org.apd.algorithm;

import javafx.scene.control.TextArea;
import org.apd.ApplicationHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

public class Graph {
    private static final Logger LOGGER = Logger.getLogger(Graph.class.getName());

    private List<Edge> edgesList;
    private List<Character> vertexesList;


    public Graph() {
        LOGGER.info("Create new graph");
        edgesList = new ArrayList<>();
        vertexesList = new LinkedList<>();
    }

    public Graph(TextArea textArea){
        LOGGER.info("Create new graph");
        edgesList = new ArrayList<>();
        vertexesList = new LinkedList<>();
        LOGGER.setLevel(Level.INFO);
        Handler handler = new ApplicationHandler(textArea);
        LOGGER.addHandler(handler);
    }


    public boolean addEdge(Edge edge) {
        LOGGER.log(Level.INFO,"Try to add new edge: {0}", edge);
        if(!checkEdge(edge)) return false;
        LOGGER.log(Level.INFO,"Edge: {0} doesn't exist in the graph", edge);
        edgesList.add(edge);
        if (!vertexesList.contains(edge.getBegin())) {
            LOGGER.log(Level.INFO, "Vertex: '{0}' is new in graph, add it to vertexes list", edge.getBegin().toString());
            vertexesList.add(edge.getBegin());
        }
        if (!vertexesList.contains(edge.getEnd())){
            LOGGER.log(Level.INFO, "Vertex: '{0}' is new in graph, add it to vertexes list", edge.getEnd().toString());
            vertexesList.add(edge.getEnd());
        }
        return true;
    }

    private boolean checkEdge(Edge edge) {
        for (Edge curEdge : edgesList) {
            if (curEdge.equals(edge)) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        LOGGER.log(Level.INFO, "Clear graph");
        edgesList.clear();
        vertexesList.clear();
    }

    public void removeEdge(Edge edge) {
        LOGGER.log(Level.INFO, "Remove edge: {0}, from graph", edge);
        edgesList.remove(edge);
        boolean isContainFirstVertex = false;
        boolean isContainSecondVertex = false;
        for (Edge curEdge : edgesList) {
            if (curEdge.getBegin() == edge.getBegin() || curEdge.getEnd() == edge.getBegin())
                isContainFirstVertex = true;
            if (curEdge.getBegin() == edge.getEnd() || curEdge.getEnd() == edge.getEnd()) isContainSecondVertex = true;
        }
        if (!isContainFirstVertex) {
            vertexesList.remove(edge.getBegin());
            LOGGER.log(Level.CONFIG, "No edges, connected to vertex: '{0}'", edge.getBegin());
        }
        if (!isContainSecondVertex) {
            LOGGER.log(Level.CONFIG, "No edges, connected to vertex: '{0}'", edge.getEnd());
            vertexesList.remove(edge.getEnd());
        }
    }

    public void removeVertex(Character vertex) {
        LOGGER.log(Level.CONFIG, "Remove vertex: '{0}' from graph", vertex);
        vertexesList.remove(vertex);
        edgesList.removeIf(edge -> edge.getBegin() == vertex || edge.getEnd() == vertex);
    }

    public List<Edge> getEdgesList() {
        return edgesList;
    }

    public List<Character> getVertexesList() {
        return vertexesList;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (Edge edge : edgesList) {
            sb.append(edge.toString()).append("\n");
        }
        return sb.toString();
    }
}
