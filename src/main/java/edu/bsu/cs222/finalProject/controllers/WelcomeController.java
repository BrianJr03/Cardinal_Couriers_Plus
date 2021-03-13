package edu.bsu.cs222.finalProject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class WelcomeController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void loadLogin() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/ui/loginUI.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void loadSignUp() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/ui/signupUI.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
