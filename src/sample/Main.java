package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //  This will be a test to quickly get used to using JavaFX
        Button btn = new Button("Click Me!");
        //  Connect to code when clicked.
        btn.setOnAction(e -> btn_click());
        //  Make a frame
        StackPane frame = new StackPane();
        //  add the button to the frame
        frame.getChildren().add(btn);
        //  Create the scene for the frame
        Scene scene = new Scene(frame, 200, 50);
        //  Put the scene on the stage and show it
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello World!");
        primaryStage.show();
    }
    //  A quick action that occurs when button is clicked.
    public void btn_click(){
        System.out.println("You clicked on theeee button!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
