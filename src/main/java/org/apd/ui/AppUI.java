package org.apd.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apd.algorithm.Edge;
import org.apd.algorithm.Graph;

public class AppUI {
    private static final class AppButton extends Button{
        AppButton(String txt, String color){
            super(txt);
            setStyle("-fx-border-radius: 0; -fx-text-fill: #fff; -fx-cursor: hand; -fx-font-weight: normal; -fx-background-color: " + color);
        }
        AppButton(String txt, String color, boolean disable){
            this(txt, color);
            setDisable(disable);
        }
    }

    public final Image imgAppIcon;
    public final Image imgAppCover;

    public static final String colorRed = "#EF2A0F";
    public static final String colorOrange = "#EF6D0F";
    public static final String colorGreen = "#39D276";
    public static final String colorCyan = "#26A6EE";
    public static final String colorBlue = "#331A79";

    public final Button btnEditGraph;
    public final Button btnStepForward;
    public final Button btnResult;
    public final Button btnSaveResult;
    public final Button btnStart;
    public final Button btnAddE;
    public final Button btnAddFromFile;
    public final Button btnDeleteV;
    public final Button btnDeleteE;
    public final Button btnDeleteGraph;
    public final Button btnOK;

    public final Pane boxDraw;
    public final TextArea boxTxtLog;
    public final TextField boxTxtAddE;
    public final TableView boxTableAllGraph;

    public final Scene sceneCover;
    public final Scene sceneMain;
    public final Scene sceneEdit;
    
    public final Stage windowEdit;
    public final DirectoryChooser windowSaveResult;
    public final FileChooser windowAddFromFile;
    public final Alert windowError;

    public AppUI(){
        imgAppIcon = new Image("file:src\\img\\icon-64.png");
        imgAppCover = new Image("file:src\\img\\cover.png");

        btnEditGraph = new AppButton("Edit graph", colorOrange);
        btnStepForward = new AppButton("Step forward", colorCyan, true);
        btnResult = new AppButton("Result", colorCyan, true);
        btnSaveResult = new AppButton("Save result in file", colorGreen, true);
        btnStart = new AppButton("Start", colorBlue);
        btnAddE = new AppButton("Add edge", colorCyan);
        btnAddFromFile = new AppButton("Upload data from file", colorCyan);
        btnDeleteE = new AppButton("Delete edge", colorOrange, true);
        btnDeleteV = new AppButton("Delete vertex", colorOrange, true);
        btnDeleteGraph = new AppButton("Delete graph", colorOrange, true);
        btnOK = new AppButton("OK", colorGreen);

        boxDraw = new Pane();
        boxDraw.setStyle("-fx-background-color: #999; -fx-padding: 4px; -fx-max-width: 10000px; -fx-pref-width: 1px; -fx-pref-height: 1px");
        boxTxtLog = new TextArea();
        boxTxtLog.editableProperty().setValue(false);
        boxTxtLog.setStyle("-fx-background-color: #ddd; -fx-text-fill: #000; -fx-max-width: 400px; -fx-highlight-fill: #ddd");
        boxTxtAddE = new TextField("<Vertex 1> <Vertex 2> <Weight> or <Vertex 1> <Vertex 2> <...> <Vertex N>");
        boxTxtAddE.setMaxWidth(10000.0);
        HBox.setHgrow(boxTxtAddE, Priority.ALWAYS);
        boxTableAllGraph = new TableView<Graph>();
        var vertex1 = new TableColumn<Edge, Character>("Vertex 1");
        vertex1.setCellValueFactory(new PropertyValueFactory<>("begin"));
        var vertex2 = new TableColumn<Edge, Character>("Vertex 2");
        vertex2.setCellValueFactory(new PropertyValueFactory<>("end"));
        var weight = new TableColumn<Edge, Integer>("Weight");
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        boxTableAllGraph.getColumns().addAll(vertex1, vertex2, weight);
        boxTableAllGraph.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        boxTableAllGraph.setMaxWidth(10000.0);
        VBox.setVgrow(boxTableAllGraph, Priority.ALWAYS);

        //Components of Main window
        var boxCover = new StackPane(new ImageView(imgAppCover), btnStart);
        var boxMainCommandsLeft = new HBox(btnStepForward, btnResult);
        boxMainCommandsLeft.setStyle("-fx-spacing: 5px");
        var boxMainCommandsRight = new HBox(btnEditGraph, btnSaveResult);
        boxMainCommandsRight.setStyle("-fx-spacing: 5px");
        var boxMainCommands = new BorderPane();
        boxMainCommands.setLeft(boxMainCommandsLeft);
        boxMainCommands.setRight(boxMainCommandsRight);
        boxMainCommands.setStyle("-fx-pref-width: 720px; -fx-background-color: #fff; -fx-padding: 5px");
        var boxMainDrawAndLog = new HBox(boxDraw, boxTxtLog);
        HBox.setHgrow(boxDraw, Priority.ALWAYS);
        HBox.setHgrow(boxTxtLog, Priority.ALWAYS);
        var boxMain = new VBox(boxMainCommands, boxMainDrawAndLog);
        boxMain.setStyle("-fx-min-height: 480px; -fx-min-width: 720px");
        VBox.setVgrow(boxMainDrawAndLog, Priority.ALWAYS);

        //Components of Edit Graph window
        var boxEditCommandsTop = new HBox(boxTxtAddE, btnAddE, btnAddFromFile);
        boxEditCommandsTop.setStyle("-fx-spacing: 5px; -fx-padding: 5px; -fx-background-color: #fff");
        var boxEditCommandsBottomLeft = new HBox(btnDeleteV, btnDeleteE, btnDeleteGraph);
        boxEditCommandsBottomLeft.setStyle("-fx-spacing: 5px");
        var boxEditCommandsBottomRight = new HBox(btnOK);
        boxEditCommandsBottomRight.setStyle("-fx-spacing: 5px");
        var boxEditCommandsBottom = new BorderPane();
        boxEditCommandsBottom.setStyle("-fx-padding: 5px; -fx-background-color: #fff");
        boxEditCommandsBottom.setLeft(boxEditCommandsBottomLeft);
        boxEditCommandsBottom.setRight(boxEditCommandsBottomRight);
        var boxEdit = new VBox(boxEditCommandsTop, boxTableAllGraph, boxEditCommandsBottom);
        boxEdit.setStyle("-fx-min-height: 480px; -fx-min-width: 720px");

        sceneCover = new Scene(boxCover);
        sceneMain = new Scene(boxMain);
        sceneEdit = new Scene(boxEdit);
        
        windowEdit = new Stage();
        windowEdit.setTitle("Edit");
        windowEdit.getIcons().add(imgAppIcon);
        windowEdit.setScene(sceneEdit);
        windowEdit.initModality(Modality.APPLICATION_MODAL);
        windowAddFromFile = new FileChooser();
        windowAddFromFile.setTitle("Select Text file with edges");
        windowAddFromFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));
        windowSaveResult = new DirectoryChooser();
        windowSaveResult.setTitle("Select directory to save result");
        windowError = new Alert(Alert.AlertType.ERROR);
    }
}
