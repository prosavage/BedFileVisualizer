package net.prosavage.bedfilevisualizer;

import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.model.activity.MutableActivityBase;
import com.flexganttfx.model.layout.GanttLayout;
import com.flexganttfx.view.graphics.GraphicsBase;
import com.flexganttfx.view.graphics.renderer.ActivityBarRenderer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import com.flexganttfx.view.GanttChart;

import java.util.List;


public class ThePlot {
    int ROW_HEIGHT = 10;

    class BED extends MutableActivityBase<Read> {
        public BED(Read read) {
            setUserObject(read);
            setName(read.chromosome);
//            setStartTime(bed_cell_data.getStart());
//            setEndTime(bed_cell_data.getEnd());
        }
    }

    class Read <T> extends com.flexganttfx.model.activity.ActivityBase<T>{
        private String chromosome;
        private int start;
        private int end;
        public Read(String chromosome, int start, int end) {
            this.chromosome = chromosome;
            this.start = start;
            this.end = end;
        }
    }
//    class BED2 extends Row<BED2, BED2, BEDCell> {
//        public BED2(String name) {
//            super(name);
//        }
//    }
//
//    public Node generatePlot() {
//
//       return node;
//    }

    public Node test() {
        GanttChart gantt_chart = new GanttChart();
        GraphicsBase<?> graphics = gantt_chart.getGraphics();
        graphics.setActivityRenderer(
                Read.class, // the type of activities that will be rendered
                GanttLayout.class, // the type of layout where the renderer will be used
                new ActivityBarRenderer<>(graphics, "ReadRenderer")); // the actual renderer instance


        Layer layer = new Layer("Read");
        gantt_chart.getLayers().add(layer);

        //Read read_1 = new BED("chromosome");
        //b747.addActivity(layer, new Flight(new FlightData("flight1", 1)));
        //b747.addActivity(layer, new Flight(new FlightData("flight2", 2)));
        //b747.addActivity(layer, new Flight(new FlightData("flight3", 3)));
        return null;
    }

    public Node getRow(String chromosome, String name, int min, int max, List<BEDCell> bed_cells) {
        Canvas canvas = new Canvas();
        int width = max - min;
        canvas.setWidth(width);
        canvas.setHeight(ROW_HEIGHT);
        GraphicsContext graphics_context = canvas.getGraphicsContext2D();
        graphics_context.setFill(Color.BLACK);
        graphics_context.fillRoundRect(0, 0, width, ROW_HEIGHT/2, 0, 0);
        graphics_context.setFill(Color.MAGENTA);
        for (BEDCell bed_cell : bed_cells) {
            width = bed_cell.getEnd() - bed_cell.getStart();
            graphics_context.fillRoundRect(bed_cell.getStart(), 0, width, ROW_HEIGHT, 10, 10);
        }
        return canvas;
    }

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
