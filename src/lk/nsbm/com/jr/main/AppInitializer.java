/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.nsbm.com.jr.main;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.nsbm.com.jr.controller.LoginController;

import java.io.IOException;

/**
 * @author ranjith-suranga
 */
public class AppInitializer extends Application {

    public static void navigateToHome(Node node, Stage mainStage) throws IOException {

        Parent root = FXMLLoader.load(AppInitializer.class.getResource("/lk/nsbm/com/jr/view/MainForm.fxml"));
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        TranslateTransition tt1 = new TranslateTransition(Duration.millis(300), root.lookup("AnchorPane"));
        tt1.setToX(0);
        tt1.setFromX(-mainScene.getWidth());
        tt1.play();

        mainStage.centerOnScreen();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {


        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/lk/nsbm/com/jr/view/LoginForm.fxml"));

        Parent loginRoot = loader.load();
        LoginController loginController = (LoginController) loader.getController();
        Scene loginScene = new Scene(loginRoot);
        Stage loginStage = new Stage();
        loginStage.setScene(loginScene);
        loginStage.setTitle("IJSE FX POS - In Memory DB");
        loginStage.setScene(loginScene);
        loginStage.setResizable(false);
        loginStage.showAndWait();
//        System.out.println(loginController.isUserAuthenticated());
        if (loginController.isUserAuthenticated()) {
            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/nsbm/com/jr/view/MainForm.fxml"));

            Scene mainScene = new Scene(root);

            primaryStage.setTitle("POS-SYSTEM-DISTRIBUTED");
            primaryStage.setScene(mainScene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }


    }

}
