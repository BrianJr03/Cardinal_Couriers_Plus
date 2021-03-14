package edu.bsu.cs222.finalProject.controllers;

import edu.bsu.cs222.finalProject.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.SQLException;

public class WelcomeController {

    Database db = new Database();

    @FXML
    private AnchorPane rootPane;

    public void initialize() throws SQLException, IOException {
        if ( db.getCurrentUsername() != null ) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/ui/deliveryUI.fxml"));
            rootPane.getChildren().setAll(pane);
            db.getConnection().close();
        }
    }

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
