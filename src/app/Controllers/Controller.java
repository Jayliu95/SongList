package app.Controllers;

import app.Models.Song;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class Controller {
    /* Form IDs */
    @FXML private TextField title;
    @FXML private TextField artist;
    @FXML private TextField album;
    @FXML private TextField year;
    @FXML private ListView songListView;
    @FXML private Label err_msg;

    private ObservableList<Song> songObservableList;

    @FXML
    private void initialize() {
        System.out.println("This should be initialized before anything else happens");
        songObservableList = FXCollections.observableArrayList();
        Bindings.bindContent(songObservableList, songListView.getSelectionModel().getSelectedItems());
        // Initialize songList with saved data if it exists.

    }
    @FXML protected void handleAddButtonAction(ActionEvent event) {
        if (title.getText().isEmpty() || artist.getText().isEmpty()){
            err_msg.setText("Missing title and/or artist.");
            return;
        }

        //  Validate year as integer.
        int yearValidate = 0;
        if(!year.getText().isEmpty()) {
            try {
                yearValidate = Integer.parseInt(year.getText());
            } catch (NumberFormatException error) {
                err_msg.setText("Year must be an integer.");
                return;
            }
        }
        Song newSong = new Song(title.getText(), artist.getText(), album.getText(), yearValidate);
        if (songObservableList.contains(newSong)) {
            clearForm();
            err_msg.setText("Song already exists in your playlist.");
            return;
        }
        songObservableList.addAll(newSong);
        System.out.println(newSong);

    }

    @FXML protected void handleSongSelectionAction(ActionEvent event){

    }


    // Utility Functions
    private void clearForm(){
        title.clear();
        artist.clear();
        album.clear();
        year.clear();
    }
}