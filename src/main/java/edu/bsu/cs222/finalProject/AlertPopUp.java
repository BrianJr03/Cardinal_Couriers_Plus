package edu.bsu.cs222.finalProject;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertPopUp {

    public javafx.scene.control.Alert createAlert(AnchorPane rootPane) {
        Stage stage = ( Stage ) rootPane.getScene().getWindow();
        javafx.scene.control.Alert.AlertType warning = javafx.scene.control.Alert.AlertType.CONFIRMATION;
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(warning,"");
        alert.initModality( Modality.APPLICATION_MODAL );
        alert.initOwner( stage );
        alert.getDialogPane().setHeaderText( "WARNING" );
        alert.getDialogPane().setContentText( "This will reset your cart." );
        return alert;
    }
}

