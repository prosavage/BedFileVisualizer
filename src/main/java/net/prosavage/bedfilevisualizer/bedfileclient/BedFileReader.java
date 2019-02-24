package net.prosavage.bedfilevisualizer.bedfileclient;

import net.prosavage.bedfilevisualizer.BEDCell;

import java.io.*;
import java.util.ArrayList;

public class BedFileReader {

    public BedFileReader() {
    }

    public static BEDCell[] ReadBedFile(File f){
        try{
            ArrayList<BEDCell> result = new ArrayList<>();
            FileInputStream stream = new FileInputStream(f);
            DataInputStream in = new DataInputStream(stream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null)   {
                String[] tokens = line.split("\t",4);
                BEDCell b = new BEDCell(tokens[0],Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]));
                result.add(b);
            }
            in.close();
            return result.toArray(new BEDCell[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
