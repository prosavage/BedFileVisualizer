package net.prosavage.bedfilevisualizer.manhattanchart;

import javafx.fxml.FXML;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import net.prosavage.bedfilevisualizer.BEDCell;
import net.prosavage.bedfilevisualizer.bedfileclient.BedFileClient;
import net.prosavage.bedfilevisualizer.bedfileclient.BedFileReader;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Controller {


	@FXML
	ScatterChart scatterChart;


	@FXML
	Button doTheThing;




	@FXML
	public void onClick() {
		BEDCell[] bedCells = BedFileReader.ReadBedFile(new File(getClass().getResource("/TEST2.bed").getFile()));
		HashMap<Integer, List<BEDCell>> bedCellMap = new HashMap<>();


		for (BEDCell bedCell : bedCells) {
			int range = bedCell.getEnd() - bedCell.getStart();
			switch (bedCell.getChromosome()) {
				case "chrX":
					bedCell.setChromosome("chr24");
					break;
				case "chrY":
					bedCell.setChromosome("chr25");
					break;
				case "chrM":
					bedCell.setChromosome("chr26");
					break;
				default:
					break;
			}
			int chromosome = Integer.parseInt(bedCell.getChromosome().replace("chr", ""));
			bedCellMap.get(chromosome).add(bedCell);
		}

		for (Integer chromosome : bedCellMap.keySet()) {
			double interval = (double) 1 / bedCellMap.get(chromosome).size();
			int counter = 0;
			XYChart.Series series = new XYChart.Series();
			for (BEDCell bedCell : bedCellMap.get(chromosome)) {
				counter++;
				series.getData().add(new XYChart.Data((double) chromosome + (counter * interval), bedCell.getEnd() - bedCell.getStart()));
			}
			scatterChart.getData().add(series);

		((NumberAxis) scatterChart.getXAxis()).setUpperBound(26);
		}

	}
}
