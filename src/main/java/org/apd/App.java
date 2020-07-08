package org.apd;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import org.apd.algorithm.AlgorithmAPD;
import org.apd.algorithm.Graph;
import org.apd.ui.AppUI;
import org.apd.ui.GraphUI;

import java.util.logging.Logger;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        //var javaVersion = SystemInfo.javaVersion();
        //var javafxVersion = SystemInfo.javafxVersion();

        var appUI = new AppUI();
        var graphUI = new GraphUI(appUI.boxDraw);
        var graph = new Graph(appUI.boxTxtLog);
        var apd = new AlgorithmAPD(graph);
        var controller = new Controller(appUI, graphUI, graph, apd);

        appUI.btnStart.setOnAction(actionEvent -> {
            stage.setScene(appUI.sceneMain);
            stage.setResizable(true);
        });

        appUI.btnEditGraph.setOnAction(actionEvent -> {
            appUI.windowEdit.show();
        });

        Logger.getLogger("log");

        appUI.btnOK.setOnAction(actionEvent -> {
            appUI.windowEdit.close();
        });

        appUI.btnAddFromFile.setOnAction(actionEvent -> {
            var file = appUI.windowAddFromFile.showOpenDialog(appUI.windowEdit);
            controller.openFile(file);
        });

        appUI.btnAddE.setOnAction(actionEvent -> {
            controller.addEdge();
        });

        appUI.btnDeleteE.setOnAction(actionEvent -> {
            controller.deleteEdge();
        });

        appUI.btnDeleteV.setOnAction(actionEvent -> {
            controller.deleteVertex();
        });

        appUI.btnDeleteGraph.setOnAction(actionEvent -> {
            controller.deleteGraph();
        });

        appUI.btnStepForward.setOnAction(actionEvent -> {
            controller.nextStep();
        });

        appUI.btnResult.setOnAction(actionEvent -> {
            controller.result();
        });

        appUI.btnSaveResult.setOnAction(actionEvent -> {
           var dir = appUI.windowSaveResult.showDialog(stage);
           controller.saveResult(dir);
        });

        appUI.boxDraw.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                graphUI.moveGraph();
            }
        });

        appUI.boxDraw.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                graphUI.moveGraph();
            }
        });

        stage.setTitle("Prim Beta");
        stage.getIcons().add(appUI.imgAppIcon);
        stage.setResizable(false);
        stage.setScene(appUI.sceneCover);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}