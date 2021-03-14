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
import static edu.bsu.cs222.finalProject.SendReceipt.sendReceiptAsEmail;
import static edu.bsu.cs222.finalProject.SendReceipt.sendReceiptAsTextMSG;

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

    public void sendReceipt() throws MessagingException, IOException { //bruh. clean up this mess
        if (!emailCheckBox.isSelected() && !textCheckBox.isSelected())
        { displayPromptFor3secs( pleaseSelectContactInfo_Label ); }
        else {
            if (emailCheckBox.isSelected() && !emailAddress.getText().isEmpty())
            { sendReceiptAsEmail(SendReceipt.isValidEmail(
                    emailAddress.getText()), cart );
            displayPromptFor3secs(receiptSent);}
            else displayPromptFor3secs( pleaseEnterContactInfo_Label );
            if(textCheckBox.isSelected() && !phoneNumber.getText().isEmpty())
            { sendReceiptAsTextMSG(SendReceipt.isValidPhoneNumber(
                    phoneNumber.getText()), cart, carrierComboBox.getValue());
            displayPromptFor3secs(receiptSent);}
            else displayPromptFor3secs( pleaseEnterContactInfo_Label );
        }
    }

    public void closeProgram()
    { System.exit(0); }

    public void launchMainUI() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/mainUI.fxml"));
        Parent root = loader.load();
        rootPane.getChildren().setAll(root);
    }
}