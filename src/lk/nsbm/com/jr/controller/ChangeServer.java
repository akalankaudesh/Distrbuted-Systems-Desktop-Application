package lk.nsbm.com.jr.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import lk.nsbm.com.jr.db.SelectConn;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeServer implements Initializable {
    public RadioButton serverCheck1;
    public RadioButton serverCheck2;
    public RadioButton serverCheck3;
    public Button okButton;

    Stage stage;


    public void okButtonActtion(ActionEvent actionEvent) {
        if (serverCheck2.isSelected())
            SelectConn.changeConn("2");
        else if (serverCheck3.isSelected())
            SelectConn.changeConn("3");

        new Alert(Alert.AlertType.INFORMATION, "Server Change Successful", ButtonType.OK).showAndWait();
        if (stage != null)
            stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void cancelBtnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (SelectConn.con1)
            serverCheck1.setSelected(true);
        else if (SelectConn.con2)
            serverCheck2.setSelected(true);
        else if (SelectConn.con3)
            serverCheck3.setSelected(true);

    }
}
