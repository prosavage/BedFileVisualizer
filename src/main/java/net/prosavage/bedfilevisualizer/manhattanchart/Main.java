package net.prosavage.bedfilevisualizer.manhattanchart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	//public int[index][section length = min-max][count of occurrence ++] result;
	//file reader??
	//public int minSectionNum;
	//public int maxSectionNum;
	//public int sectionLength;

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/manhattanplot.fxml"));
		primaryStage.setTitle("Manhattan Plot");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

	}


	public static void main(String[] args) {
		launch(args);
	}



}