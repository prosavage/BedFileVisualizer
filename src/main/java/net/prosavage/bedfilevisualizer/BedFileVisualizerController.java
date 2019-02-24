package net.prosavage.bedfilevisualizer;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.prosavage.bedfilevisualizer.bedfileclient.BedFileClient;
import net.prosavage.bedfilevisualizer.bedfileclient.BedFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static net.prosavage.bedfilevisualizer.bedfileclient.BedFileReader.ReadBedFile;

public class BedFileVisualizerController {
    @FXML private AnchorPane anchor_pane;
    @FXML private Button open_bed_files_button, test_button;
    @FXML private TextField track_count_text_field, minimum_base_pair_overlap_text_field, overlap_count_text_field;
    @FXML private VBox plot_node;

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
                File output_file1 = bed_file_client.runWindow(100, new File("TEST1.bed"));
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
        FileChooser file_chooser = new FileChooser();
        FileChooser.ExtensionFilter extension_filter = new FileChooser.ExtensionFilter("BED files (*.bed)", "*.bed");
        file_chooser.getExtensionFilters().add(extension_filter);
        List<File> files = file_chooser.showOpenMultipleDialog(getStage());
        BEDCell[] bed_cells = BedFileReader.ReadBedFile(file);
        String current_chromosome = bed_cells[0].getChromosome();
        int min = bed_cells[0].getStart();
        int max = bed_cells[0].getEnd();
        ArrayList<BEDCell> current_chromosome_bed_cells = new ArrayList<BEDCell>();
        ThePlot the_plot = new ThePlot();
        for (BEDCell bed_cell :  bed_cells) {
            if (current_chromosome.equals(bed_cell.getChromosome()) != true) {
                Node row = the_plot.getRow(current_chromosome,file.getName(),min,max,current_chromosome_bed_cells);
                plot_node.getChildren().add(row);
                current_chromosome = bed_cell.getChromosome();
                min = bed_cell.getStart();
                max = bed_cell.getEnd();
                current_chromosome_bed_cells.clear();
            }
            if (bed_cell.getStart() < min) {
                min = bed_cell.getStart();
            }
            if (bed_cell.getEnd() > max) {
                max = bed_cell.getEnd();
            }
            current_chromosome_bed_cells.add(bed_cell);
        }
        Node row = the_plot.getRow(current_chromosome,file.getName(),min,max,current_chromosome_bed_cells);
        plot_node.getChildren().add(row);
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
