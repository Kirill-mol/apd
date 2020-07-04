package org.apd;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        //var javaVersion = SystemInfo.javaVersion();
        //var javafxVersion = SystemInfo.javafxVersion();

        var appUI = new AppUI();

        var stageEdit = new Stage();
        stageEdit.setTitle("Edit");
        stageEdit.getIcons().add(appUI.imgAppIcon);
        stageEdit.setScene(appUI.sceneEdit);
        stageEdit.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle("Prim Beta");
        stage.getIcons().add(appUI.imgAppIcon);
        stage.setResizable(false);
        stage.setScene(appUI.sceneCover);
        stage.centerOnScreen();
        stage.show();

        appUI.btnStart.setOnAction(actionEvent -> {
            stage.setScene(appUI.sceneMain);
            GraphUI.drawGraph(appUI.boxDraw, 8);
            stage.setResizable(true);
        });

        appUI.btnEditGraph.setOnAction(actionEvent -> {
            stageEdit.show();
        });

        appUI.boxDraw.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                GraphUI.moveGraphX(t1.doubleValue() - number.doubleValue());
            }
        });

        appUI.boxDraw.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                GraphUI.moveGraphY(t1.doubleValue() - number.doubleValue());
            }
        });
    }

    public static int is10(){
        return 10;
    }

    public static void main(String[] args) {
        launch();
    }

}