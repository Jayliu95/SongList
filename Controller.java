package app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtArtist;

    @FXML
    private TextField txtAlbum;

    @FXML
    private TextField txtYear;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

public Controller(){ }
public void enable(Controller song){
    if(song.txtName.getText().isEmpty()||song.txtArtist.getText().isEmpty()){
        song.btnAdd.setDisable(true);
        song.btnEdit.setDisable(true);
        song.btnDelete.setDisable(true);
    }
    else{
        song.btnAdd.setDisable(false);
        song.btnEdit.setDisable(false);
        song.btnDelete.setDisable(false);
    }
}

}


