package org.apd;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class AppUI {
    private static final class AppButton extends Button{
        AppButton(String txt, String color){
            super(txt);
            setStyle("-fx-border-radius: 0; -fx-text-fill: #fff; -fx-cursor: hand; -fx-font-weight: normal; -fx-background-color: " + color);
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
    public final Button btnStepBack;
    public final Button btnStepForward;
    public final Button btnResult;
    public final Button btnSaveResult;
    public final Button btnStart;
    public final Button btnAddE;
    public final Button btnAddFromFile;
    public final Button btnDeleteV;
    public final Button btnDeleteE;
    public final Button btnDeleteGraph;
    public final Button btnCancel;
    public final Button btnOK;

    public final Pane boxDraw;
    public final VBox boxTxtLog;
    public final TextField boxTxtAddE;
    public final VBox boxTxtAllGraph;

    public final Scene sceneCover;
    public final Scene sceneMain;
    public final Scene sceneEdit;

    AppUI(){
        imgAppIcon = new Image("file:src\\img\\icon-64.png");
        imgAppCover = new Image("file:src\\img\\cover.png");

        btnEditGraph = new AppButton("Edit graph", colorOrange);
        btnStepBack = new AppButton("Step back", colorCyan);
        btnStepForward = new AppButton("Step forward", colorCyan);
        btnResult = new AppButton("Result", colorCyan);
        btnSaveResult = new AppButton("Save result in file", colorGreen);
        btnStart = new AppButton("Start", colorBlue);
        btnAddE = new AppButton("Add edge", colorCyan);
        btnAddFromFile = new AppButton("Upload data from file", colorCyan);
        btnDeleteE = new AppButton("Delete edge", colorOrange);
        btnDeleteV = new AppButton("Delete vertex", colorOrange);
        btnDeleteGraph = new AppButton("Delete graph", colorOrange);
        btnCancel = new AppButton("Cancel", colorRed);
        btnOK = new AppButton("OK", colorGreen);

        boxDraw = new Pane();
        boxDraw.setStyle("-fx-background-color: #999; -fx-padding: 4px; -fx-max-width: 10000px");
        boxTxtLog = new VBox();
        boxTxtLog.setStyle("-fx-background-color: #ccc; -fx-text-fill: #000; -fx-max-width: 400px; -fx-padding: 15px");
        boxTxtAddE = new TextField("<First vertex> <Second vertex> <Weight>");
        boxTxtAddE.setMaxWidth(10000.0);
        HBox.setHgrow(boxTxtAddE, Priority.ALWAYS);
        boxTxtAllGraph = new VBox();
        boxTxtAllGraph.setStyle("-fx-background-color: #ccc; -fx-text-fill: #000; -fx-padding: 15px; -fx-max-width: 10000px");
        VBox.setVgrow(boxTxtAllGraph, Priority.ALWAYS);

        //Components of Main window
        var boxCover = new StackPane(new ImageView(imgAppCover), btnStart);
        var boxMainCommandsLeft = new HBox(btnStepBack, btnStepForward, btnResult);
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
        boxEditCommandsTop.setStyle("-fx-spacing: 5px; -fx-padding: 5px");
        var boxEditCommandsBottomLeft = new HBox(btnDeleteV, btnDeleteE, btnDeleteGraph);
        boxEditCommandsBottomLeft.setStyle("-fx-spacing: 5px");
        var boxEditCommandsBottomRight = new HBox(btnCancel, btnOK);
        boxEditCommandsBottomRight.setStyle("-fx-spacing: 5px");
        var boxEditCommandsBottom = new BorderPane();
        boxEditCommandsBottom.setStyle("-fx-padding: 5px");
        boxEditCommandsBottom.setLeft(boxEditCommandsBottomLeft);
        boxEditCommandsBottom.setRight(boxEditCommandsBottomRight);
        var boxEdit = new VBox(boxEditCommandsTop, boxTxtAllGraph, boxEditCommandsBottom);
        boxEdit.setStyle("-fx-min-height: 480px; -fx-min-width: 720px");

        sceneCover = new Scene(boxCover);
        sceneMain = new Scene(boxMain);
        sceneEdit = new Scene(boxEdit);
    }
}
