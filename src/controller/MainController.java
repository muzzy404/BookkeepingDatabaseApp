package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class MainController extends Application {

    private static final String TITLE = "Bookkeeping application";

    private static BorderPane root;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(TITLE);

        try {
            initRootLayout();
            showProjectsLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() throws IOException {
        // load and show root layout
        root = FXMLLoader.load(getClass().getResource("/view/root_layout.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void showProjectsLayout() throws IOException {
        root.setCenter(FXMLLoader.load(getClass().getResource("/view/projects_layout.fxml")));
    }

    public void handleClose(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void handleHelp(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Bookkeeping data base client application");
        alert.setContentText("This is a JavaFX client application for Bookkeeping Oracle data base. Author Daria Zelenova.");
        alert.show();
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH); // important!!!
        launch(args);
    }
}
