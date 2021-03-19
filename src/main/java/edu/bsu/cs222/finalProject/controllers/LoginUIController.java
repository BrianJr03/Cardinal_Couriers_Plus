package edu.bsu.cs222.finalProject.controllers;

import edu.bsu.cs222.finalProject.Database;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import static edu.bsu.cs222.finalProject.LoginLogic.isValidPassword;
import static edu.bsu.cs222.finalProject.LoginLogic.isValidUserName;
import static edu.bsu.cs222.finalProject.Main.displayPromptFor3secs;

public class LoginUIController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private CheckBox checkBox;
    @FXML
    private ImageView passwordVisibility;
    @FXML
    private Label unMaskedPassword;
    @FXML
    public Label invalidUserInfo_Prompt;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private TextField usernameInput;


    Database db = new Database();

    public void initialize()
    { passwordVisibility.setImage(isNotVisible_PNG); }

    final File isVisiblePNG_File = new File("src/main/resources/pngs/isVisible.png");
    final Image isVisible_PNG = new Image(isVisiblePNG_File.toURI().toString());
    final File isNotVisiblePNG_File = new File("src/main/resources/pngs/isNotVisible.png");
    final Image isNotVisible_PNG = new Image(isNotVisiblePNG_File.toURI().toString());

    public void login() throws IOException, SQLException {
            db.logInCurrentUser( usernameInput.getText(), passwordInput.getText() );
            launchDeliveryUI();
    }

    public void launchDeliveryUI() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/ui/deliveryUI.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML //has duplicate in signup
    private void goBackToWelcome() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/ui/welcomeUI.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    public void showUnMaskedPassword() {
        if (checkBox.isSelected()) {
            passwordVisibility.setImage(isVisible_PNG);
            unMaskedPassword.setText(passwordInput.getText());
            unMaskedPassword.setVisible(true); }
        else { passwordVisibility.setImage(isNotVisible_PNG); unMaskedPassword.setVisible(false); }
    }

    public void verifyUserInfo() throws IOException, SQLException {
        if ( isValidUserName(usernameInput.getText())
                        && isValidPassword(usernameInput.getText(), passwordInput.getText())) {
            if ( db.isValid_UserCredentials( usernameInput.getText(), passwordInput.getText() ))
             { login(); }
            else { displayInvalidUserInfo_Prompt(); }
        }
        else displayInvalidUserInfo_Prompt();
    }

    public void displayInvalidUserInfo_Prompt()
    { displayPromptFor3secs(invalidUserInfo_Prompt); }

    @FXML
    private void launchForgotPassword() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/ui/forgotUI.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}