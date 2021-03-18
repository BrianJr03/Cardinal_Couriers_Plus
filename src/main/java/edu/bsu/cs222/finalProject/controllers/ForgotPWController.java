package edu.bsu.cs222.finalProject.controllers;

import edu.bsu.cs222.finalProject.Database;
import javafx.fxml.FXML;
import static edu.bsu.cs222.finalProject.Main.displayPromptFor3secs;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.SQLException;

public class ForgotPWController {

    @FXML
    private Label noAcct_Label;
    @FXML
    private Label plsEnterValidUser_Label;
    @FXML
    private TextField username_Textfield;
    @FXML
    private Label password_Label;
    @FXML
    private Label userPassword_Label;
    @FXML
    private AnchorPane rootPane;

    Database db = new Database();

    public void initialize() {
        noAcct_Label.setVisible( false );
        password_Label.setVisible( false );
        userPassword_Label.setVisible( false );
        plsEnterValidUser_Label.setVisible( false );
    }

    @FXML
    private void launchLogin() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/ui/loginUI.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    private void showPassword(String currentPassword) {
        displayPromptFor3secs( password_Label );
        userPassword_Label.setText( currentPassword );
        displayPromptFor3secs( userPassword_Label );
    }

    private void showNoAcct()
    { displayPromptFor3secs( noAcct_Label ); }

    @FXML
    private void retrievePassword() throws SQLException {
        String username = username_Textfield.getText();
        String currentPassword = db.getCurrentPassword( username );
        if ( currentPassword != null )
        { showPassword( currentPassword ); }
        else showNoAcct();
    }
}