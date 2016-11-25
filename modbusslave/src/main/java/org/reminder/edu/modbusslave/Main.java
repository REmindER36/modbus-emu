package org.reminder.edu.modbusslave;

import org.reminder.edu.controller.SlaveController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        System.setProperty("net.wimpi.modbus.debug", "true");

        // ApplicationManager app = new ApplicationManager();
        // app.startModbusListener();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader
                .load(getClass().getResource("/fxml/Slave.fxml").openStream());
        SlaveController controller = (SlaveController) fxmlLoader
                .getController();

        ApplicationManager app = new ApplicationManager();
        controller.setApplicationManager(app);
        // Scene scene = new Scene(root, 800, 600);
        Scene scene = new Scene(root);

        primaryStage.setTitle("ModBus Slave");
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
