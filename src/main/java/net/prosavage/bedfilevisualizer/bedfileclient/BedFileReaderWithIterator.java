package net.prosavage.bedfilevisualizer.bedfileclient;

import net.prosavage.bedfilevisualizer.BEDCell;

import java.io.*;

public class BedFileReaderWithIterator {
    private final File file;
    private final BufferedReader buffered_reader;

    public BedFileReaderWithIterator(File file) throws FileNotFoundException {
        this.file = file;
        buffered_reader = new BufferedReader(new FileReader(file));
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("file.txt")))) {

            String line;
            while ((line = reader.readLine()) != null)
                System.out.println(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BEDCell next() throws IOException {
        BEDCell bed_cell = null;
        String line = buffered_reader.readLine();
        if (line != null) {
            String[] tokens = line.split("\t",4);
            bed_cell = new BEDCell(tokens[0],Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]));
        }
        return bed_cell;
    }
}
