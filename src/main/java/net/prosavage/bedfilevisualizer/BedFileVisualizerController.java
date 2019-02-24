package net.prosavage.bedfilevisualizer;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.prosavage.bedfilevisualizer.bedfileclient.BedFileClient;
import net.prosavage.bedfilevisualizer.bedfileclient.BedFileReader;
import net.prosavage.bedfilevisualizer.bedfileclient.BedFileReaderWithIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static net.prosavage.bedfilevisualizer.bedfileclient.BedFileReader.ReadBedFile;

public class BedFileVisualizerController {
    @FXML private AnchorPane anchor_pane;
    @FXML private Button open_bed_files_button, test_button;
    @FXML private TextField track_count_text_field, minimum_base_pair_overlap_text_field, overlap_count_text_field;
    @FXML private VBox plot_node;
    @FXML
    ScatterChart scatterChart;
    @FXML Pane scatterPlotPlane, mary_graph;


    private final int DEFAULT_MINIMUM_BASE_PAIR_OVERLAP_COUNT = 1000;
    private List<File> files;
    private String[] chromosomes = {"chr1","chr2","chr3","chr4","chr5","chr6","chr7","chr8","chr9","chr10",
                                    "chr11","chr12","chr13","chr14","chr15","chr16","chr17","chr18","chr19","chr20",
                                    "chr21","chr22","chr23","chrX","chrY","chrM"};
    private Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN,
                              Color.AZURE, Color.BLUE, Color.INDIGO, Color.PURPLE, Color.MAGENTA,
                              Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN,
                              Color.AZURE, Color.BLUE, Color.INDIGO, Color.PURPLE, Color.MAGENTA,
                              Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};

    private Stage getStage() {
        return (Stage) anchor_pane.getScene().getWindow();
    }

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



    @FXML
    private void onActionOpenBedFilesButton(ActionEvent action_event) {
        System.out.println("yooooo");
        FileChooser file_chooser = new FileChooser();
        FileChooser.ExtensionFilter extension_filter = new FileChooser.ExtensionFilter("BED files (*.bed)", "*.bed");
        file_chooser.getExtensionFilters().add(extension_filter);
        files = file_chooser.showOpenMultipleDialog(getStage());
        for (File file : files) {
            System.out.println(file.getName());
        }
        int number_of_tracks = files.size();
        track_count_text_field.setText(Integer.toString(number_of_tracks));
        overlap_count_text_field.setText(Integer.toString(number_of_tracks));
        init(); //Don't do this here plz. Run this when controller first loads
    }

    @FXML
    private void onActionGeneratePlotButton(ActionEvent action_event) {
        if (files == null || files.isEmpty()) {
            System.out.println("NO!");
            return;  // Show a pop-up dialog
        }
        try {
            int minimum_overlap = Integer.parseInt(minimum_base_pair_overlap_text_field.getText());
            int k = Integer.parseInt(overlap_count_text_field.getText());
            if (k == 2) {
                BedFileClient bed_file_client = new BedFileClient(this.files);

                File file = new File(getClass().getResource("/TEST1.bed").getPath());
                if (overlap_count_text_field.getText() == null || overlap_count_text_field.getText().isEmpty()) {
                    overlap_count_text_field.setText(files.size() + "");
                }
                File output_file1 = bed_file_client.runWindow(100, file, Integer.parseInt(overlap_count_text_field.getText()));
                System.out.println("Done running bed file client run window~~~"+output_file1.getAbsolutePath());
                File output_file2 = bed_file_client.runIntersect(new File("TEST2.bed"));
                System.out.println("Done running bed file client run intersect~~~"+output_file2.getAbsolutePath());

                BEDCell[] PlotData = ReadBedFile(output_file1);

            }
            ThePlot the_plot = new ThePlot();
//            plot_node.getChildren().add(the_plot.generatePlot());
        } catch (NumberFormatException exception) {
            System.out.println("Shouldn't happen!!!!");
            return; // Show a pop-up dialog
        }
    }

    private void makeTextFieldNumberic(TextField text_field) {
        text_field.textProperty().addListener((observable, old_value, new_value) -> {
            if (new_value.matches("\\d*") != true) {
                text_field.setText(new_value.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void onActionTestButton(ActionEvent action_event) {
        mary_graph.setOpacity(1);
        mary_graph.setScaleZ(99999);
        scatterPlotPlane.setOpacity(0);
        scatterPlotPlane.setScaleZ(99);
        FileChooser file_chooser = new FileChooser();
        FileChooser.ExtensionFilter extension_filter = new FileChooser.ExtensionFilter("BED files (*.bed)", "*.bed");
        file_chooser.getExtensionFilters().add(extension_filter);
        List<File> files = file_chooser.showOpenMultipleDialog(getStage());
        ArrayList<BedFileReaderWithIterator> file_readers = new ArrayList<BedFileReaderWithIterator>();
        for (File file : files) {
            try {
                file_readers.add(new BedFileReaderWithIterator(file));
            } catch (FileNotFoundException exception){
                System.out.println("FILE NOT FOUND OH NOOOOOOO"); // Through popup or smth
                return;
            }
        }
        for (int i = 0; i < chromosomes.length; i++) {
            String current_chromosome = chromosomes[i];
            Color current_color = colors[i];
            int min = -1;
            int max = -1;
            for (BedFileReaderWithIterator file_reader : file_readers) {
                ArrayList<BEDCell> chromosome_bed_cells = new ArrayList<>();
                BEDCell bed_cell;
                try {
                    while ((bed_cell = file_reader.next()) != null) {
                        if (current_chromosome.equals(bed_cell.getChromosome()) != true) {
                            file_readers.get(i).holdBEDCell(bed_cell);
                            break;
                        }
                        if (bed_cell.getStart() < min || min < 0) {
                            min = bed_cell.getStart();
                        }
                        if (bed_cell.getEnd() > max || min < 0) {
                            max = bed_cell.getEnd();
                        }
                        chromosome_bed_cells.add(bed_cell);
                    }
                    file_reader.chromosome_bed_cells = chromosome_bed_cells;
                } catch (IOException exception) {
                    System.out.println("IOException oh noooo"); //throw some popup
                    return;
                }
            }
            for (BedFileReaderWithIterator file_reader : file_readers) {
                ThePlot the_plot = new ThePlot();
                Node row = the_plot.getRow(current_chromosome, file_reader.getName(), min, max, file_reader.chromosome_bed_cells, current_color);
                plot_node.getChildren().add(row);
            }
        }
    }

    private void init() {
        makeTextFieldNumberic(minimum_base_pair_overlap_text_field);
        minimum_base_pair_overlap_text_field.setText(Integer.toString(DEFAULT_MINIMUM_BASE_PAIR_OVERLAP_COUNT));
        makeTextFieldNumberic(overlap_count_text_field);
    }
}
//I got it! your welc :D
//
//wow nice code
//
//%%%%%%%%%%**************,,,,,,,******************************************************//(((#######################((((((
//%%%%%%%%***************,,,,,,,,,*****************************************************//(((#############(((((((((((((///
//%%%%%(,****************,,,,,,,,,,****************************************************//(((###########((((((((((////////
//,,*********************,,,,,,,,,,****************************************************//(((##########(((((((/////////***
//***********************,,,,,,,,,,,***************************************************//(((##########((((((///////******
//************************,,,,,,,,,,,**************************************************//(((##########(((((///////*******
//************************,,,,,,,,,,,**************************************************//(((##########(((((///////******,
//*************************,,,,,,,,,,,*************************************************///(((#########(((((///////******,
//***********************************,*************************************************///(((#######((((((////////******,
//**************************************************************************************//(((((###((((((((/////////*****,
//**************,,,,..******************************************************************///((((((((((((((///////////*****
//***************,,,,,,.%*.//////********************************************************////(((((((((//////////////*****
//***************..,,,,.%%%%%*,////*******************************************************//////////////////////////*****
//**************,,,.,,,.%%%%%%%%%.************************************************************************/*///*********.
//**************/////,,.%%%%%%%%%%%%#.****************************************************,,,,,,,,,,,,,,,,,,,,,,,./%%%.,,
//*************///////,,%%%%%%%%%%%%%%%%.*************************************************,,,,,,,,,,,,,,,,,.,%%%%%%%%%.,,
//************/////////.,%%%%%%%%%%%%%%%%%%,**********************************************,,,,,,,,,,,,.*%%%%%%%%%%%%%#,,,
//************//////////.%%%%%%%%%%%%%%%%%%%%%.********************************************,,,,,,,.#%%%%%%%%%%%%%%%%%,,,.
//***********//////////**.%%%%%%%%%%%%%%%%%%%%%%,******************************************,,,.#%%%%%%%%%%%%%%%%%%%%%,,.,
//**********//////////****,%%%%%%%%%%%%%%%%%%%%%%%%,*************************************./%%%%%%%%%%%%%%%%%%%%%%%%%.,,,,
//**********/////////******,#%%%%%%%%%%%%%%%%%%%%%%%#,*****************,,,,,,,*******.*%%%%%%%%%%%%%%%%%%%%%%%%%%%%/.,,,,
//*********//////////*****,,,,%%%%%%%%%%%%%%%%%%%%%%%%*(#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*.,,,,,
//********//////////*****,,,,,.#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%.,,,,,,,
//***********************,,,,,,,.%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%.******,,
//**********************,,,,,,,,,,*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%,//*******,
//******,,,,,,,,,,,,,,,,,,,,,,,,,,,,/%%%%%,%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%.///////*****
//*****,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,*%.%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%./////////****,
//*****,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,(%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%.%%%.*//////////*****,
//****,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,/%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%.****////////******,
//****,,,,,,,,....,,,,,,,,,,,,,,,,,,,.%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%.*****************,
//***,,,,,,..........,,,,,,,,,,,,,,,.%%%%%%%%%%%**(,,.%%%%%%%%%%%%%%%%%%%%%%%%%%%%.(,,,#%%%%%%%%%%%%%%%,****************,
//**,,,,,,...........,,,,,,,,,,,,**,%%%%%%%%%%%*%@@&,,.%%%%%%%%%%%%%%%%%%%%%%%%%%,@@@,,,#%%%%%%%%%%%%%%#,**************,,
//**,,,,,.............,,,,,,,,,****/%%%%%%%%%%%.,,,,,,.%%%%%%%%%%%%%%%%%%%%%%%%%%,,,,,,,(%%%%%%%%%%%%%%%/************,,,,
//*,,,,,..............,,,,,,,,,***.%%%%%%%%%%%%%.,,,,,(%%%%%%%%%%%%%%%%%%%%%%%%%%,,,,,,.%%%%%%%%%%%%%%%%%.,,***,,,,,,,,,,
//*,,,,,..............,,,,,,,,***,#%%%%%%%%%%%%%%%(/#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%#*#%%%%%%%%%%%%%%%%%%%/,,,,,,,,,,,,,,,
//,,,,,................,,,,,,,***,%%%%%%%%%%%%%%%%%%%%%%%%%%%%%...,%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%.,,,,,,,,,,,,,,
//,,,,,...............,,,,,,,,**,%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%(..,%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%(,,,,,,,,,,,,,,
//,,,,,,,,..........,,,,,,,,****.%%(////////%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%/////////%%%%%%%%.,,,,,,,,,,,,,
//,,,,,,,,,,,,,,,,,,,,,,,,******(%///////////#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%////////////#%%%%%%%**********,,,
//,,,,,,,,,,,,,,,,,,,***********%%///////////(%%%%%%%%%%%%%%%%%%%%####%%%%%%%%%%%%%%%%%%/////////////%%%%%%%.**********,,
//,,,,**************************%%%//////////%%%%%%%%%%%%%%%%#,(########,#%%%%%%%%%%%%%%////////////%%%%%%%%#***********,
//,***********************//////#%%%%#////%%%%%%%%%%%%%%%%%%((###########,%%%%%%%%%%%%%%%%/////////%%%%%%%%%%.///*******,
//*********/////////////////////.%%%%%%%%%%%%%%%%%%%%%%%%%%%#/###########//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%(/////*****,
//*******////////////////////////#%%%%%%%%%%%%%%%%%%%%%%%%%%%**##########,#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%./////*****
//******/////////////////////////*(%%%%%%%%%%%%%%%%%%%%%%%%%%%%/,/#####(,%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%(/////*****
//******//////////////////////////*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*////****,
//*******/////////////////////////**%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%.///*****,
//*********//////////////////////****/%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%///******,
//************/////////////////*******#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%,*******,
//************************************/%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%.******,,
//***********************************,%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%******,,,
