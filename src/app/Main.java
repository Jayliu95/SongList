package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/view.fxml"));
        Scene scene = new Scene(root, 650, 650);
        primaryStage.setTitle("Song List");

        final Accordion accordion = new Accordion ();
        String [] acc  = {"Hi", "I", "am", "cool"};
        /*
        TitledPane gridTitlePane = new TitledPane();
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("First Name: "), 0, 0);
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("Last Name: "), 0, 1);
        grid.add(new TextField(), 1, 1);
        grid.add(new Label("Email: "), 0, 2);
        grid.add(new TextField(), 1, 2);
        gridTitlePane.setText("Grid");
        gridTitlePane.setContent(grid);


        Create an accordion on the right hand side

        for (int i = 0; i < acc.length; i++) {

            images[i] = new
                    Image(getClass().getResourceAsStream(imageNames[i] + ".jpg"));
            pics[i] = new ImageView(images[i]);
            tps[i] = new TitledPane(imageNames[i],pics[i]);
        }
        accordion.getPanes().addAll(tps);
        accordion.setExpandedPane(tps[0]);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(accordion);
        */

        primaryStage.setScene(scene);
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
