package net.prosavage.bedfilevisualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {
	//public int[index][section length = min-max][count of occurrence ++] result;
	//file reader??
	//public int minSectionNum;
	//public int maxSectionNum;
	//public int sectionLength;

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root, 777, 777));
		primaryStage.show();

	}


	public static void main(String[] args) {
		launch(args);
	}


    public static BEDCell[] ReadBedFile(File f){
    	try{
    		ArrayList<BEDCell> result = new ArrayList<BEDCell>();
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
    	return (BEDCell[]) result.toArray();
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
	}
}