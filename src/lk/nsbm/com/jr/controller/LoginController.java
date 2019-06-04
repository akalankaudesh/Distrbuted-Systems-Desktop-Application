package lk.nsbm.com.jr.controller;

import com.google.common.hash.Hashing;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.nsbm.com.jr.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;
    public AnchorPane anchorPane;
    private String hashedUserName, hashedPswrd;
    private boolean isAuth = false;


    public void btnLogin_OnAction(ActionEvent actionEvent) {
//        System.out.println(Hashing.sha256().hashString("Admin", StandardCharsets.UTF_8).toString());
//        System.out.println(Hashing.sha256().hashString("1234", StandardCharsets.UTF_8).toString());


        if (validateInputs()) {
//            hashInputs();
          //  checkLogin();

            isAuth = true;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Stage stage = (Stage) txtPassword.getScene().getWindow();
                    stage.close();
                }
            });


        } else {
            new Alert(Alert.AlertType.ERROR, "Please Fill All Input Fields", ButtonType.OK).showAndWait();
        }

    }

    private void checkLogin() {

//        System.out.println(Hashing.sha256().hashString("user", StandardCharsets.UTF_8).toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "select  * from user where user.user_name = ? and  user.user_password = ?";

                try {
                    Connection connection = DBConnection.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, hashedUserName);
                    preparedStatement.setString(2, hashedPswrd);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.first()) {
                        isAuth = true;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Stage stage = (Stage) txtPassword.getScene().getWindow();
                                stage.close();
                            }
                        });

                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new Alert(Alert.AlertType.ERROR, "Invalid User Details", ButtonType.OK).showAndWait();
                            }
                        });


                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }).start();

    }

    private boolean validateInputs() {
        if (txtUsername.getText().equals("") || txtPassword.getText().equals(""))
            return false;
        else {
            return true;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void hashInputs() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                hashedUserName = Hashing.sha256().hashString(txtUsername.getText(), StandardCharsets.UTF_8).toString();
                hashedPswrd = Hashing.sha256().hashString(txtPassword.getText(), StandardCharsets.UTF_8).toString();
            }
        }).start();

    }


    public boolean isUserAuthenticated() {
        return isAuth;
    }

    public void changeServerAction(ActionEvent actionEvent) throws IOException {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/nsbm/com/jr/view/ChangeServer.fxml"));
        root = loader.load();
        ChangeServer  changeServer = (ChangeServer) loader.getController();

//        root = FXMLLoader.load(this.getClass().getResource("/lk/nsbm/com/jr/view/ChangeServer.fxml"));
        if (root != null) {
            Scene subScene = new Scene(root);
            Stage primaryStage = new Stage();
            changeServer.setStage(primaryStage);
            primaryStage.setScene(subScene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();

        }
    }
}
