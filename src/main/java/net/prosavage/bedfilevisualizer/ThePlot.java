package net.prosavage.bedfilevisualizer;


import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;


public class ThePlot {
    int ROW_HEIGHT = 10;





    public Node getRow(String chromosome, String name, int min, int max, List<BEDCell> bed_cells, Color color) {
        Canvas canvas = new Canvas();
        double modifier = 3000.0/max;
        double width = (max - min)*modifier;
        canvas.setWidth(width);
        canvas.setHeight(ROW_HEIGHT);
        GraphicsContext graphics_context = canvas.getGraphicsContext2D();
        graphics_context.setFill(Color.CYAN);
        graphics_context.fillRoundRect(0, 0, width, ROW_HEIGHT/2, 0, 0);
        graphics_context.setFill(color);
        for (BEDCell bed_cell : bed_cells) {
            width = (bed_cell.getEnd() - bed_cell.getStart())*modifier;
            if (width < 1) {
                width = 1;
            }
            graphics_context.fillRoundRect(bed_cell.getStart()*modifier, 0, width, ROW_HEIGHT, 10, 10);
        }
        return canvas;
    }
private String chromosome;
//        private int start;
//        private
    public Node nolongerused() {
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
