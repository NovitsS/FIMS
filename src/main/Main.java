package main;

import Item.UserItem;
import data.LocalData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {
    public static UserItem user=null;
    public static Stage stage;
    public static String admin="novit";
    public static String aPassword="admin";

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                LocalData.initData();
            }
        });
        thread.start();

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void replaceSceneContent(String fxml, final int width, final int height ) throws IOException {
        FXMLLoader loader =new FXMLLoader();
        InputStream in= Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        final BorderPane page=(BorderPane) loader.load(in);
        in.close();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Scene scene=new Scene(page,width,height);
                stage.setScene(scene);
            }
        });
    }
    public static void replaceSceneContentA(String fxml, final int width, final int height ) throws IOException {
        FXMLLoader loader =new FXMLLoader();
        InputStream in= Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        final AnchorPane page=(AnchorPane) loader.load(in);
        in.close();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Scene scene=new Scene(page,width,height);
                stage.setScene(scene);
            }
        });
    }

    public static void closeStage(){
        stage.close();
    }
}
