package org.apd;

import javafx.collections.FXCollections;
import javafx.scene.SnapshotParameters;
import org.apd.algorithm.*;
import org.apd.ui.AppUI;
import org.apd.ui.GraphUI;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

public class Controller {
    private final AppUI appUI;
    private final GraphUI graphUI;
    private final Graph graph;
    private final GraphReader graphReader;
    private final AlgorithmAPD apd;

    Controller(AppUI aUI, GraphUI gUI, GraphReader gr, Graph g, AlgorithmAPD a){
        appUI = aUI;
        graphUI = gUI;
        graphReader = gr;
        graph = g;
        apd = a;
    }

    public void openFile(File file){
        try {
            graphReader.readGraphFromFile(file);
            update();
            enableBtn();
        } catch (Exception e) {
            appUI.windowError.setHeaderText(e.getMessage());
            appUI.windowError.show();
        }
    }

    public void addEdge(){
        try {
            var scanner = new Scanner(appUI.boxTxtAddE.getText()).useDelimiter(System.getProperty("line.separator"));
            String[] curLine = scanner.nextLine().split(" ");
            Edge edge =  new Edge(new Vertex(curLine[0]), new Vertex(curLine[1]), Integer.parseInt(curLine[2]));
            graphReader.addEdge(edge);
            update();
            enableBtn();
        } catch (Exception e){
            appUI.windowError.setHeaderText(e.getMessage());
            appUI.windowError.show();
        }
    }

    public void deleteEdge(){
        try {
            Edge item = (Edge) appUI.boxTableAllGraph.getSelectionModel().getSelectedItem();
            graphReader.removeEdge(item);
            update();
            if (graph.getVertexesList().size() == 0){
                clear();
            }
        } catch (Exception e){
            appUI.windowError.setHeaderText(e.getMessage());
            appUI.windowError.show();
        }
    }

    public void deleteVertex(){
        try {
            var scanner = new Scanner(appUI.boxTxtAddE.getText()).useDelimiter(System.getProperty("line.separator"));
            String[] curLine = scanner.nextLine().split(" ");
            for (var str: curLine){
                graphReader.removeVertex(new Vertex(Character.toString(str.charAt(0))));
            }
            update();
            if (graph.getVertexesList().size() == 0){
                clear();
            }
        } catch (Exception e){
            appUI.windowError.setHeaderText(e.getMessage());
            appUI.windowError.show();
        }
    }

    public void clear(){
        apd.clear();
        update();
        disableBtn();
        appUI.btnEditGraph.setDisable(false);
    }

    public void nextStep(){
        try {
            var e = apd.nextEdgeAtMst();
            graphUI.addToSpanning(e);
            appUI.btnEditGraph.setDisable(true);

        } catch (Exception e){
            appUI.btnStepForward.setDisable(true);
            appUI.btnResult.setDisable(true);
            appUI.btnSaveResult.setDisable(false);
        }
    }

    public void result(){
        try {
            List<Edge> es = apd.result();
            for (var e: es){
                graphUI.addToSpanning(e);
            }
            appUI.btnStepForward.setDisable(true);
            appUI.btnResult.setDisable(true);
            appUI.btnEditGraph.setDisable(true);
            appUI.btnSaveResult.setDisable(false);
        } catch (Exception e){
            appUI.windowError.setHeaderText(e.getMessage());
            appUI.windowError.show();
        }
    }

    public void saveResult(File dir){
        try {
            var snapshot = appUI.boxDraw.snapshot(new SnapshotParameters(), null);
            var file = new File(dir.getPath() + "/result.png");
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        } catch (Exception e){
            appUI.windowError.setHeaderText(e.getMessage());
            appUI.windowError.show();
        }
    }

    private void enableBtn(){
        if (graph.getEdgesList().size() != 0){
            appUI.btnDeleteE.setDisable(false);
            appUI.btnClear.setDisable(false);
            appUI.btnDeleteV.setDisable(false);
            appUI.btnStepForward.setDisable(false);
            appUI.btnResult.setDisable(false);
        }
    }

    private void disableBtn(){
        if (graph.getEdgesList().size() == 0){
            appUI.btnDeleteE.setDisable(true);
            appUI.btnDeleteV.setDisable(true);
            appUI.btnStepForward.setDisable(true);
            appUI.btnSaveResult.setDisable(true);
            appUI.btnResult.setDisable(true);
            appUI.btnClear.setDisable(true);
        }
    }

    private void updateTableGraph(){
        var edges = graph.getEdgesList();
        var list = FXCollections.observableArrayList(edges);
        appUI.boxTableAllGraph.setItems(list);
    }

    private void updateBoxDraw(){
        graphUI.graphToUI(graph);
        graphUI.drawGraph();
    }

    private void update(){
        updateTableGraph();
        updateBoxDraw();
    }
}
