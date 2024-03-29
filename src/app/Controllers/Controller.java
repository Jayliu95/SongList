package app.Controllers;

import app.Models.Song;
import app.SongLib;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


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

    @FXML private ListView<Song> songListView;
    @FXML private Label info_msg;

    private ObservableList<Song> songObservableList;
    private Song selectSong;

    /* State variables */
    private boolean addState = false;
    private boolean editState = false;
    private boolean saveState = false;

    private static ObservableList<Song> read(Path file) {
        try {
            InputStream in = Files.newInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(in);
            List<Song> list = (List<Song>) ois.readObject() ;

            return FXCollections.observableList(list);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList();
    }

    @FXML private void initialize() {
        System.out.println("This should be initialized before anything else happens");
        // Initialize songList with saved data if it exists.
        File yourFile = new File("Objectsavefile.ser");
        try {
            yourFile.createNewFile(); // if file already exists will do nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path path = Paths.get(yourFile.getPath());
        songObservableList = read(path);
        songListView.setItems(songObservableList.sorted());
        songListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                info_msg.setText("");
                selectSong = songObservableList.get(songListView.getSelectionModel().getSelectedIndex());
                showSongOnForm(selectSong);
                changeToEditState();
            }
        });
        if(songObservableList.size() > 0){
            System.out.println("Auto Selecting the first item! " + songObservableList.get(0));
            selectSong = songObservableList.get(0);
            songListView.getSelectionModel().selectFirst();
            showSongOnForm(selectSong);
            changeToEditState();
            return;
        }
        resetStates();
        changeToAddState();

    }
    @FXML protected void handleAddButtonAction(ActionEvent event) {
        info_msg.setText("");
        if(editState){
            resetStates();
            changeToAddState();
            return;
        }
        if(saveState){
            if(!handleUnsavedChanges()) {
                return;
            }else{
                changeToAddState();
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Confirmation");
        alert.setHeaderText("Add Confirmation");
        alert.setContentText("Are you sure you want to add the song to playlist?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.CANCEL) {
            return;
        }

        if (title.getText().isEmpty() || artist.getText().isEmpty()){
            setInfoMsg("Missing title and/or artist.", "red");
            return;
        }

        //  Validate year as integer.
        int yearValidate = 0;
        if(!year.getText().isEmpty()) {
            try {
                yearValidate = Integer.parseInt(year.getText());
            } catch (NumberFormatException error) {
                setInfoMsg("Year must be an integer.", "red");
                return;
            }
        }

        Song newSong = new Song(title.getText(), artist.getText(), album.getText(), yearValidate);
        if (songObservableList.contains(newSong)){
            handleSongExistence();
            return;
        }

        // Adding the songs to observable list and list view.
        selectSong = newSong;
        songObservableList.addAll(newSong);
        songListView.getSelectionModel().select(newSong);
        setInfoMsg("Song added successfully!", "green");
        resetStates();
        changeToEditState();
    }

    @FXML protected void handleEditButtonAction(ActionEvent event){
        info_msg.setText("");
        if(saveState){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Confirmation");
            alert.setHeaderText("Save Confirmation");
            alert.setContentText("Are you sure you want to change the song info?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CANCEL) {
                return;
            }
            if (title.getText().isEmpty() || artist.getText().isEmpty()){
                setInfoMsg("Missing title and/or artist.", "red");
                return;
            }
            int yearValidate = 0;
            if(!year.getText().isEmpty()) {
                try {
                    yearValidate = Integer.parseInt(year.getText());
                } catch (NumberFormatException error) {
                    setInfoMsg("Year must be an integer.", "red");
                    return;
                }
            }
            /* Update does not reflect on FXML view. Resorting to delete and add method */

            Song updatedSong = new Song(title.getText(), artist.getText(), album.getText(), yearValidate);
            songObservableList.removeAll(selectSong);
            if (songObservableList.contains(updatedSong)){
                songObservableList.addAll(selectSong);
                songListView.getSelectionModel().select(selectSong);
                handleSongExistence();
                return;
            }
            selectSong = updatedSong;
            songObservableList.addAll(updatedSong);
            songListView.getSelectionModel().select(selectSong);
            setInfoMsg("Save successfully!", "green");
            resetStates();
            changeToEditState();
            return;
        }
        changeToSaveState();
    }

    @FXML protected void handleDeleteButtonAction(ActionEvent event){
        System.out.println(songListView.getSelectionModel().getSelectedItems());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete the song?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.CANCEL) {
            return;
        }
        songObservableList.remove(selectSong);
        if(songObservableList.size() > 0){
            selectSong = songObservableList.get(0);
            songListView.getSelectionModel().selectFirst();
            showSongOnForm(selectSong);
            changeToEditState();
        }else{
            changeToAddState();
        }
        setInfoMsg("Delete successfully!", "green");
        System.out.println(songObservableList);
    }

    // Utility Functions

    private void handleSongExistence() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText("Your changes were not updated!");
        error.setContentText("There already is a song with the same title and artist in the playlist!");
        error.showAndWait();
        if (error.getResult() == ButtonType.OK) {
            return;
        }
    }

    private boolean handleUnsavedChanges() {
        Alert error = new Alert(Alert.AlertType.CONFIRMATION);
        error.setTitle("Information");
        error.setHeaderText("You have unsaved changes!");
        error.setContentText("Click the CANCEL button to continue editing.");
        error.showAndWait();
        if (error.getResult() == ButtonType.CANCEL) {
            return false;
        }else{ return true; }
    }

    private void setInfoMsg(String msg, String color){
        if (msg.isEmpty()){
            info_msg.setText("");
            return;
        }
        info_msg.setText(msg);
        info_msg.setStyle("-fx-text-fill: " + color + ";");
    }

    private void clearForm(){
        title.clear();
        artist.clear();
        album.clear();
        year.clear();
    }

    private void disableForm(boolean setDisable){
        title.setDisable(setDisable);
        artist.setDisable(setDisable);
        album.setDisable(setDisable);
        year.setDisable(setDisable);
    }

    private void showSongOnForm(Song song){
        clearForm();
        title.setText(song.getTitle());
        artist.setText(song.getArtist());
        album.setText(song.getAlbum());
        year.setText(String.valueOf(song.getYear()));
    }

    private void resetStates(){
        addState = false;
        editState = false;
        saveState = false;
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
        resetStates();
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
        resetStates();
        saveState = true;
        //  During this state, users can still:
        //  add new songs, save changes, cancel changes, delete selected songs
        editBtn.setText("Save");
        addBtn.setDisable(false);
        editBtn.setDisable(false);
        deleteBtn.setDisable(false);
        disableForm(false);
    }



    public void write(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    // write object to file
                    FileOutputStream fos = new FileOutputStream("Objectsavefile.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(new ArrayList<Song>(songObservableList));
                    oos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}