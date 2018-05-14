package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final String TITLE = "Bookkeeping application";

    private BorderPane root;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(TITLE);

        initRootLayout();
        showProjectsLayout();
    }

    private void initRootLayout() {
        // load and show root layout
        try {
            root = FXMLLoader.load(getClass().getResource("../view/root_layout.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProjectsLayout() {
        try {
            root.setCenter(FXMLLoader.load(getClass().getResource("../view/projects_layout.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
