package org.apd.algorithm;

import javafx.scene.control.TextArea;
import org.apd.ApplicationHandler;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphReader implements Observer {
    private static final Logger LOGGER = Logger.getLogger(AlgorithmAPD.class.getName());

    private Graph graph;

    public GraphReader(Graph graph, TextArea textArea) {
        this.graph = graph;
        LOGGER.setLevel(Level.INFO);
        Handler handler = new ApplicationHandler(textArea);
        LOGGER.addHandler(handler);
    }

    public void addEdge(Edge edge) throws Exception {
        if (!graph.addEdge(edge)) {
            LOGGER.log(Level.WARNING, "Try to add edge: " +  edge.toString());
            throw new Exception("edge already exist");
        }
    }

    public void readGraphFromFile(File file) throws Exception {
        LOGGER.log(Level.INFO, "Read graph from file: " + file.getName());
        var scanner = new Scanner(file).useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()) {
            String[] curLine = scanner.nextLine().split(" ");
            Edge edge = new Edge(new Vertex(curLine[0]), new Vertex(curLine[1]), Integer.parseInt(curLine[2]));
            addEdge(edge);
        }
    }

    public void removeEdge(Edge edge) {
        graph.removeEdge(edge);
    }

    public void removeVertex(Vertex vertex) {
        graph.removeVertex(vertex);
    }

    @Override
    public void updateNotify(boolean isOn) {
        LOGGER.setLevel(Level.OFF);
    }
}
