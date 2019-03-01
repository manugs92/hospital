import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import strings.Strings;

import java.io.*;


public class MainFx extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml\\sample.fxml"));
        primaryStage.setTitle(Strings.NAME_OF_THE_APP);
        primaryStage.setScene(new Scene(root, 900, 675));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
