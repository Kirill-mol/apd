package org.apd;

import javafx.collections.FXCollections;
import javafx.scene.SnapshotParameters;
import org.apd.algorithm.AlgorithmAPD;
import org.apd.algorithm.Edge;
import org.apd.algorithm.Graph;
import org.apd.ui.AppUI;
import org.apd.ui.GraphUI;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

public class Controller {
    private AppUI appUI;
    private GraphUI graphUI;
    private Graph graph;
    private AlgorithmAPD apd;

    Controller(AppUI aUI, GraphUI gUI, Graph g, AlgorithmAPD a){
        appUI = aUI;
        graphUI = gUI;
        graph = g;
        apd = a;
    }

    public void openFile(File file){
        try {
            apd.readGraphFromFile(file);
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
            Edge edge = new Edge(curLine[0].charAt(0), curLine[1].charAt(0), Integer.parseInt(curLine[2]));
            apd.addEdge(edge);
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
            apd.removeEdge(item);
            update();
            disableBtn();
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
                apd.removeVertex(str.charAt(0));
            }
            update();
            disableBtn();
        } catch (Exception e){
            appUI.windowError.setHeaderText(e.getMessage());
            appUI.windowError.show();
        }
    }

    public void deleteGraph(){
        apd.clear();
        update();
        disableBtn();
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
            appUI.btnDeleteGraph.setDisable(false);
            appUI.btnDeleteV.setDisable(false);
            appUI.btnStepForward.setDisable(false);
            appUI.btnResult.setDisable(false);
        }
    }

    private void disableBtn(){
        if (graph.getEdgesList().size() == 0){
            appUI.btnDeleteE.setDisable(true);
            appUI.btnDeleteGraph.setDisable(true);
            appUI.btnDeleteV.setDisable(true);
            appUI.btnStepForward.setDisable(true);
            appUI.btnSaveResult.setDisable(true);
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
