package net.prosavage.bedfilevisualizer.manhattanchart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import kotlin.contracts.ContractBuilderKt;
import net.prosavage.bedfilevisualizer.BEDCell;
import net.prosavage.bedfilevisualizer.bedfileclient.BedFileClient;
import net.prosavage.bedfilevisualizer.bedfileclient.BedFileReader;

import java.io.File;
import java.util.*;

public class Controller {


	@FXML
	ScatterChart scatterChart;


	@FXML
	Button doTheThing;




	@FXML
	public void onClick() {
		ArrayList<String> categories = new ArrayList<>();
		BEDCell[] bedCells = BedFileReader.ReadBedFile(new File(getClass().getResource("/bedfiles/iCellNeuron_HTTLOC_CAPCxHTT_REP3.bed").getFile()));
		HashMap<Integer, List<BEDCell>> bedCellMap = new HashMap<>();
		for (BEDCell bedCell : bedCells) {
			if (!categories.contains(bedCell.getChromosome())) {
				categories.add(bedCell.getChromosome());
			}
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
			if (!bedCellMap.containsKey(chromosome)) {
				bedCellMap.put(chromosome, new ArrayList<>(Arrays.asList(bedCell)));
			}
			bedCellMap.get(chromosome).add(bedCell);
		}

		for (Integer chromosome : bedCellMap.keySet()) {
			double interval = (double) 1 / bedCellMap.get(chromosome).size();
			int counter = 0;
			XYChart.Series series = new XYChart.Series();
			for (BEDCell bedCell : bedCellMap.get(chromosome)) {
				counter++;
				int range = bedCell.getEnd() - bedCell.getStart();
				if (range > 10000) {
					range = (int) Math.log(range);
				}
				series.getData().add(new XYChart.Data(((double) chromosome + (counter * interval)), range));
			}
			scatterChart.getData().add(series);
		}





	}
}
