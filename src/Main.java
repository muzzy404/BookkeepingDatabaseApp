import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final String TITLE = "Bookkeeping application";

    private BorderPane root;
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


    public static void main(String[] args) {
        launch(args);
    }
}
