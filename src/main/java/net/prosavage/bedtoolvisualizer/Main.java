package net.prosavage.bedfilevisualizer;

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
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        //Now I have all the power!!!
        //Muhahahahahahahaha
        //-Sumin :)
        // Dab, I fixed my project!!!
    }


    public static void main(String[] args) {
        launch(args);
    }


}
