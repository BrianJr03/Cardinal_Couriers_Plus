package edu.bsu.cs222.finalProject.controllers;

import edu.bsu.cs222.finalProject.Cart;
import edu.bsu.cs222.finalProject.SendReceipt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.mail.MessagingException;
import java.io.IOException;

import static edu.bsu.cs222.finalProject.Main.displayPromptFor3secs;
import static edu.bsu.cs222.finalProject.SendReceipt.*;

public class PostPurchaseController {

    @FXML
    private Label pleaseEnterContactInfo_Label;
    @FXML
    private Label pleaseSelectContactInfo_Label;
    @FXML
    private ComboBox<String> carrierComboBox;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private CheckBox emailCheckBox;
    @FXML
    private CheckBox textCheckBox;
    @FXML
    private TextField emailAddress;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Label receiptSent;

    public final Cart cart = new Cart(FXCollections.observableArrayList());
    public final ObservableList<String> carrierOptions = FXCollections.observableArrayList();

    public void initialize()
    { addCarriersToDropdown(); }

    public void addCarriersToDropdown() {
        carrierOptions.add("AT&T");
        carrierOptions.add("Sprint");
        carrierOptions.add("T-Mobile");
        carrierOptions.add("Verizon");
        carrierComboBox.setItems( carrierOptions );
        receiptSent.setVisible( false );
        pleaseEnterContactInfo_Label.setVisible( false );
        pleaseSelectContactInfo_Label.setVisible( false );
    }

    public void sendReceipt() throws MessagingException, IOException {
        if ( phoneAndEmailBoxes_NotSelected() )
        { displayPromptFor3secs( pleaseSelectContactInfo_Label ); }
        else { emailCheckBox_isSelected(); phoneCheckBox_isSelected(); }
    }
    
    private void emailCheckBox_isSelected() throws IOException, MessagingException {
        if ( emailCheckBox.isSelected() ) {
            if ( isValidEmail(emailAddress.getText()) != null )
            { verifyEmailInfo(); }
            else if ( !isPhoneInfo_Filled() )
            { displayPromptFor3secs( pleaseEnterContactInfo_Label ); }
        }
    }
    
    private void phoneCheckBox_isSelected() throws IOException, MessagingException {
        if ( textCheckBox.isSelected() && carrierComboBox.getValue() != null ) {
            if ( isValidPhoneNumber(phoneNumber.getText()) != null )
            { verifyPhoneInfo(); }
            else if ( !isEmailInfo_Filled() )
            { displayPromptFor3secs( pleaseEnterContactInfo_Label ); }
        }
        else displayPromptFor3secs( pleaseEnterContactInfo_Label );
    }

    private void verifyEmailInfo() throws IOException, MessagingException {
        if ( isEmailInfo_Filled() )
        { sendReceiptAsEmail(SendReceipt.isValidEmail(
                emailAddress.getText()), cart );
            displayPromptFor3secs(receiptSent);}
        else displayPromptFor3secs( pleaseEnterContactInfo_Label );
    }

    private void verifyPhoneInfo() throws IOException, MessagingException {
        if( isPhoneInfo_Filled() )
        { sendReceiptAsTextMSG(SendReceipt.isValidPhoneNumber(
                phoneNumber.getText()), cart, carrierComboBox.getValue());
            displayPromptFor3secs(receiptSent);}
        else displayPromptFor3secs( pleaseEnterContactInfo_Label );
    }

    private boolean isPhoneInfo_Filled()
    { return textCheckBox.isSelected() && !phoneNumber.getText().isEmpty(); }

    private boolean isEmailInfo_Filled()
    { return emailCheckBox.isSelected() && !emailAddress.getText().isEmpty(); }

    private boolean phoneAndEmailBoxes_NotSelected()
    { return !emailCheckBox.isSelected() && !textCheckBox.isSelected(); }

    public void closeProgram()
    { System.exit(0); }

    public void launchMainUI() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/mainUI.fxml"));
        Parent root = loader.load();
        rootPane.getChildren().setAll(root);
    }
}