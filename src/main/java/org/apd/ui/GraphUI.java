package org.apd.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.apd.algorithm.Edge;
import org.apd.algorithm.Graph;
import java.util.ArrayList;
import java.util.LinkedList;

public class GraphUI {
    private class GraphV extends Circle{
        private final Text name;

        GraphV(double centerX, double centerY, Character name){
            super(centerX, centerY,6.0);
            this.name = new Text("" + name);
            this.name.yProperty().set(centerY - 10.0);
            this.name.xProperty().set(centerX);
            this.name.setStyle("-fx-font-weight: bold; -fx-text-fill: #fff");
            setStyle("-fx-stroke: #fff; -fx-stroke-width: 2px; -fx-fill: " + AppUI.colorOrange);
        }
    }

    private class GraphE extends Line {
        private final GraphV v1;
        private final GraphV v2;
        private final Text weight;

        GraphE(int weight, GraphV v1, GraphV v2){
            super(v1.getCenterX(), v1.getCenterY(), v2.getCenterX(), v2.getCenterY());
            this.weight = new Text("" + weight);
            this.weight.yProperty().set((v1.getCenterY() + v2.getCenterY()) / 2.0);
            this.weight.xProperty().set((v2.getCenterX() + v1.getCenterX()) / 2.0);
            this.weight.setStyle("-fx-font-weight: bold");
            this.v1 = v1;
            this.v2 = v2;
            setStyle("-fx-stroke: #fff; -fx-stroke-width: 2px");
        }
    }

    private final LinkedList<GraphV> graphVertixes;
    private final ArrayList<GraphE> graphEdges;
    private final Pane boxDraw;
    private double centerX;
    private double centerY;
    private double radius;

    public GraphUI(Pane boxDraw){
        graphEdges = new ArrayList<>();
        graphVertixes = new LinkedList<>();
        this.boxDraw = boxDraw;
        update();
    }

    public void graphToUI(Graph g){
        update();
        clear();
        var edges = g.getEdgesList();
        var vs = g.getVertexesList();
        for (int i = 0; i < vs.size(); i++){
            double x = centerX + radius * Math.cos(2 * Math.PI * i / vs.size());
            double y = centerY + radius * Math.sin(2 * Math.PI * i / vs.size());
            var v = new GraphV(x, y, vs.get(i));
            graphVertixes.add(v);
        }
        for (org.apd.algorithm.Edge edge : edges) {
            int indexV0 = vs.indexOf(edge.getBegin());
            int indexV1 = vs.indexOf(edge.getEnd());
            var e = new GraphE(edge.getWeight(), graphVertixes.get(indexV0), graphVertixes.get(indexV1));
            graphEdges.add(e);
        }
    }

    public void addToSpanning(Edge e){
        for (var edge: graphEdges){
            if (e.getBegin() == edge.v1.name.getText().charAt(0) && e.getEnd() == edge.v2.name.getText().charAt(0)){
                edge.setStroke(Color.web(AppUI.colorGreen));
                edge.v1.setFill(Color.web(AppUI.colorGreen));
                edge.v2.setFill(Color.web(AppUI.colorGreen));
            }
        }
    }

    public void drawGraph(){
        for (GraphE graphEdge : graphEdges) {
            boxDraw.getChildren().addAll(graphEdge, graphEdge.weight);
        }
        for (GraphV graphVertix : graphVertixes) {
            boxDraw.getChildren().addAll(graphVertix, graphVertix.name);
        }
    }

    public void moveGraph(){
        update();
        updateCoordinates();
    }

    private void updateCoordinates(){
        for (int i = 0; i < graphVertixes.size(); i++){
            double x = centerX + radius * Math.cos(2 * Math.PI * i / graphVertixes.size());
            double y = centerY + radius * Math.sin(2 * Math.PI * i / graphVertixes.size());
            graphVertixes.get(i).setCenterX(x);
            graphVertixes.get(i).setCenterY(y);
            graphVertixes.get(i).name.xProperty().set(x);
            graphVertixes.get(i).name.yProperty().set(y - 10.0);
        }
        for (var e: graphEdges){
            e.setStartX(e.v1.getCenterX());
            e.setEndX(e.v2.getCenterX());
            e.setStartY(e.v1.getCenterY());
            e.setEndY(e.v2.getCenterY());
            e.weight.xProperty().set((e.v1.getCenterX() + e.v2.getCenterX()) / 2.0);
            e.weight.yProperty().set((e.v1.getCenterY() + e.v2.getCenterY()) / 2.0);
        }
    }

    private void update(){
        centerX = boxDraw.getWidth() / 2.0;
        centerY = boxDraw.getHeight() / 2.0;
        radius = centerX < centerY ? centerX - 12 : centerY - 12;
    }

    private void clear(){
        graphVertixes.clear();
        graphEdges.clear();
        boxDraw.getChildren().clear();
    }
}
