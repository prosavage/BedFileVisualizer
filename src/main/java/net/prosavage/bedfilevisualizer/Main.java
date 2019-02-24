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
		Stage stage = new Stage();
		Parent roo2 = FXMLLoader.load(getClass().getResource("/manhattanplot.fxml"));
		stage.setTitle("Manhattan Plot");
		stage.setScene(new Scene(roo2));
		stage.show();

	}


	public static void main(String[] args) {
		launch(args);

	}



}