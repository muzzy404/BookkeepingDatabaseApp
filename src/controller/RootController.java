package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class RootController {
    public void handleClose(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void handleHelp(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Bookkeeping data base client application");
        alert.setContentText("This is a JavaFX client application for Bookkeeping Oracle data base. Author Daria Zelenova.");
        alert.show();
    }
}
