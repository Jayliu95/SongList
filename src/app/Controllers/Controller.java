package app.Controllers;

import app.Models.Song;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;


public class Controller {
    /* Form IDs */
    @FXML private TextField title;
    @FXML private TextField artist;
    @FXML private TextField album;
    @FXML private TextField year;

    /* Form buttons */
    @FXML private Button addBtn;
    @FXML private Button editBtn;
    @FXML private Button deleteBtn;

    @FXML private ListView songListView;
    @FXML private Label err_msg;

    private ObservableList<Song> songObservableList;
    private Song selecSong;

    /* State variables */
    private boolean addState = false;
    private boolean editState = false;
    private boolean saveState = false;

    @FXML private void initialize() {
        System.out.println("This should be initialized before anything else happens");
        songObservableList = FXCollections.observableArrayList();
        resetStates();
        changeToAddState();
        // Initialize songList with saved data if it exists.

    }
    @FXML protected void handleAddButtonAction(ActionEvent event) {
        if(editState){
            resetStates();
            changeToAddState();
            return;
        }
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
            err_msg.setText("Song already exists in your playlist.");
            return;
        }
        selecSong = newSong;
        songObservableList.addAll(newSong);
        songListView.setItems(songObservableList);
        songListView.getSelectionModel().select(newSong);
        resetStates();
        changeToEditState();
    }

    @FXML protected void handleEditButtonAction(ActionEvent event){
        if(saveState){
            System.out.println("Item saved");
            resetStates();
            changeToEditState();
            return;
        }
        changeToSaveState();
    }

    @FXML protected void handleDeleteButtonAction(ActionEvent event){
        System.out.println(songListView.getSelectionModel().getSelectedItems());
        songObservableList.remove(selecSong);
        System.out.println(songObservableList);
    }

    /*
     *  Upon selection, change "Add" button to "Edit".
     */
    @FXML protected void handleSongSelectionAction(ActionEvent event){

    }


    // Utility Functions
    private void clearForm(){
        title.clear();
        artist.clear();
        album.clear();
        year.clear();
    }

    private void resetStates(){
        addState = false;
        editState = false;
        saveState = false;
    }

    private void disableForm(boolean setDisable){
        title.setDisable(setDisable);
        artist.setDisable(setDisable);
        album.setDisable(setDisable);
        year.setDisable(setDisable);
    }

    private void changeToAddState(){
        System.out.println("Current State: AddState");
        clearForm();
        addState = true;
        addBtn.setText("Add");
        addBtn.setDisable(false);
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);
        disableForm(false);

    }

    private void changeToEditState(){
        System.out.println("Current State: EditState");
        editState = true;
        addBtn.setText("New");
        editBtn.setText("Edit");
        addBtn.setDisable(false);
        editBtn.setDisable(false);
        deleteBtn.setDisable(false);
        disableForm(true);
    }

    private void changeToSaveState(){
        System.out.println("Current State: SaveState");
        saveState = true;
        //  During this state, users can still:
        //  add new songs, save changes, cancel changes, delete selected songs
        editBtn.setText("Save");
        addBtn.setDisable(false);
        editBtn.setDisable(false);
        deleteBtn.setDisable(false);
        disableForm(false);
    }


}