package com.mycompany.passwordmanager;

import com.mycompany.passwordmanager.data_base.HibernateUtil;
import com.mycompany.passwordmanager.utils.constants.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(Constants.MAIN_WINDOW));
        stage.setScene(scene);

        stage.setOnCloseRequest((WindowEvent event) -> {
            // Llama al método de cierre de HibernateUtil
            HibernateUtil.shutdown("password"); // Reemplaza "your-password" con la contraseña real
            System.exit(0);
        });

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}