package org.apd.algorithm;

import javafx.scene.control.TextArea;
import org.apd.ApplicationHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

public class Graph implements Observer {
    private static final Logger LOGGER = Logger.getLogger(Graph.class.getName());

    private List<Edge> edgesList;
    private List<Vertex> vertexesList;

    public Graph() {
        LOGGER.info("Create new graph");
        edgesList = new ArrayList<>();
        vertexesList = new LinkedList<>();
        LOGGER.removeHandler(new ConsoleHandler());
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
        LOGGER.log(Level.INFO,"Try to add new edge: " + edge.toString());
        if(!checkEdge(edge)) return false;
        LOGGER.log(Level.INFO,"Edge: " + edge.toString() + " doesn't exist in the graph");
        edgesList.add(edge);
        if (!vertexesList.contains(edge.getBegin())) {
            LOGGER.log(Level.INFO, "Vertex: " + edge.getBegin().toString() + " is new in graph, add it to vertexes list");
            vertexesList.add(edge.getBegin());
        }
        if (!vertexesList.contains(edge.getEnd())){
            LOGGER.log(Level.INFO, "Vertex: " + edge.getEnd().toString() + " is new in graph, add it to vertexes list");
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
        LOGGER.log(Level.INFO, "Remove edge: " + edge.toString() + ", from graph");
        edgesList.remove(edge);
        boolean isContainFirstVertex = false;
        boolean isContainSecondVertex = false;
        for (Edge curEdge : edgesList) {
            if (curEdge.getBegin().equals(edge.getBegin()) || curEdge.getEnd().equals(edge.getBegin()))
                isContainFirstVertex = true;
            if (curEdge.getBegin().equals(edge.getEnd()) || curEdge.getEnd().equals(edge.getEnd()))
                isContainSecondVertex = true;
        }
        if (!isContainFirstVertex) {
            vertexesList.remove(edge.getBegin());
            LOGGER.log(Level.CONFIG, "No edges, connected to vertex: " + edge.getBegin());
        }
        if (!isContainSecondVertex) {
            LOGGER.log(Level.CONFIG, "No edges, connected to vertex: " + edge.getEnd());
            vertexesList.remove(edge.getEnd());
        }
    }

    public void removeVertex(Vertex vertex) {
        LOGGER.log(Level.CONFIG, "Remove vertex: " + vertex + " from graph");
        vertexesList.remove(vertex);
        edgesList.removeIf(edge -> edge.getBegin().equals(vertex) || edge.getEnd().equals(vertex));
    }

    public List<Edge> getEdgesList() {
        return edgesList;
    }

    public List<Vertex> getVertexesList() {
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

    @Override
    public void updateNotify(boolean isOn) {
        LOGGER.setLevel(Level.OFF);
    }
}
