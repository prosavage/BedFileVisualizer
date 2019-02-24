package net.prosavage.bedfilevisualizer.bedfileclient;

import net.prosavage.bedfilevisualizer.BEDCell;

import java.io.*;
import java.util.ArrayList;

public class BedFileReaderWithIterator {
    private final File file;
    private final BufferedReader buffered_reader;
    private BEDCell held_bed_cell = null;
    public ArrayList<BEDCell> chromosome_bed_cells; //holding data here for whatever reason

    public BedFileReaderWithIterator(File file) throws FileNotFoundException {
        this.file = file;
        buffered_reader = new BufferedReader(new FileReader(file));
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null)
//                System.out.println(line);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public BEDCell next() throws IOException {
        BEDCell bed_cell = held_bed_cell;
        if (held_bed_cell == null) { // If a bed cell is held, then return that first before getting new bed cell
            String line = buffered_reader.readLine();
            if (line != null) {
                String[] tokens = line.split("\t", 4);
                bed_cell = new BEDCell(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            }
        }
        held_bed_cell = null;
        return bed_cell;
    }

    public String getName() {
        return file.getName();
    }

    public void holdBEDCell(BEDCell bed_cell) {
        held_bed_cell = bed_cell; // bed_cell is held
    }
}
