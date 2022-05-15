package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RenderGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResource("../Resources/mouseCursorIcon.png").toExternalForm()));
        stage.setWidth(620);
        stage.setHeight(350);
        stage.setResizable(false);
        stage.setTitle("Customizable Auto Clicker");


        Parent root = FXMLLoader.load(getClass().getResource("GUIFormat.fxml"));

        primaryStage.setTitle("Customizable Auto Clicker");
        Scene usedScene = new Scene(root, 600, 300);

        stage.setScene(usedScene);
        usedScene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
