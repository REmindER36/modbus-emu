package org.reminder.edu.modbusslave;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        //ApplicationManager app = new ApplicationManager();
        //app.startModbusListener();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("ModBus Slave");
        primaryStage.setScene(scene);
        
        primaryStage.show();        
    }

}
