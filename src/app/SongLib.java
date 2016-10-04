package app;

import app.Controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class SongLib extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SongLib.class.getResource("view.fxml"));
        SplitPane splitPane = (SplitPane) loader.load();
        Scene scene = new Scene(splitPane);
        Controller controller = loader.getController();
        controller.write(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
