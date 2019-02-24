package net.prosavage.bedfilevisualizer;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class ThePlot {
    private Node node = new Pane();

    public Node generatePlot() {
        Canvas canvas = new Canvas();
        canvas.setWidth(500);
        canvas.setHeight(500);
        GraphicsContext graphics_context = canvas.getGraphicsContext2D();
        graphics_context.setFill(Color.MAGENTA);
        graphics_context.fillRoundRect(0, 0, 500, 500, 10, 10);
        graphics_context.setFill(Color.BLACK);
        graphics_context.fillRoundRect(10, 10, 50, 50, 10, 10);
        return canvas;
    }

}
