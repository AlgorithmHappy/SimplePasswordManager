package com.mycompany.passwordmanager.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.passwordmanager.services.PropertiesServiceImplements;
import com.mycompany.passwordmanager.services.PropertiesServiceInterface;
import com.mycompany.passwordmanager.utils.GeneralMethods;
import com.mycompany.passwordmanager.utils.constants.Constants;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/*
 * Clase controladora que gestiona la pantalla de login
 * @author Luis Gerardo Marquez
 */
public class LoginWindowController implements Initializable {

    /*
     * Paths de los archivos de base de datos de sqlite que el usuario va generando
     */
    @FXML
    private ComboBox<String> cmboBoxDirectories;

    /*
     * Password que el usario ingresa para abrir el archivo de base de sqlite
     */
    @FXML
    private TextField txtFieldPassword;

    /*
     * Boton para abrir la ventana principal del softwar y mostrar todas la cuentas que tiene
     */
    @FXML
    private Button btnLogin;

    /*
     * Crear una base de datos nueva donde el usario puede guardar sus cuentas
     */
    @FXML
    private Button btnNew;

    /*
     * Variable que contendra el path del archivo de base de datos SQLITE que se quiere abrir
     */
    private String directory;

    /*
     * Variable que contendra el password tecleado por el usuario
     */
    private String password;

    /*
     * Servicio para interactuar con el archivo de propiedades
     */
    private PropertiesServiceInterface propertiesService;    

    /*
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        propertiesService = new PropertiesServiceImplements();
        if(propertiesService.isPersonalDatabaseEmpty())
            cmboBoxDirectories = new ComboBox<>();
        else{
            cmboBoxDirectories.getItems().setAll(propertiesService.getPersonalDatabases() );
        }
        cmboBoxDirectories.getItems().add(Constants.GLOBAL_ABRIR);

        password = new String();

    }

    /*
     * Este metodo se ejecuta al presionar el boton de "Ingresar" en la interfaz de usuario
     */
    @FXML
    private void btnLoginOnAction(){
        directory = cmboBoxDirectories.getSelectionModel().getSelectedItem();
        password = txtFieldPassword.getText();
        Window window = btnLogin.getScene().getWindow();
        Stage stage = (Stage) window;
        stage.hide();

        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.MAIN_WINDOW) );
            Scene scene = new Scene(root);
            stage.setScene(scene);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setOnCloseRequest((WindowEvent event) -> {
                // Llama al método de cierre de HibernateUtil
                GeneralMethods generalMethods = GeneralMethods.getInstance(password);
                generalMethods.deleteDatabaseTemporalFile();
                System.exit(Constants.CERO);
            });
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Este metodo se ejecuta al presionar el boton de "Nuevo" en la interfaz de usuario
     */
    @FXML
    private void btnNewOnAction(){
        Window window = btnLogin.getScene().getWindow();
        Stage stage = (Stage) window;
        stage.hide();

        try {
            URL aux = getClass().getResource(Constants.MAIN_WINDOW);
            Parent root = FXMLLoader.load(getClass().getResource(Constants.MAIN_WINDOW) );
            Scene scene = new Scene(root);
            stage.setScene(scene);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setOnCloseRequest((WindowEvent event) -> {
                // Llama al método de cierre de HibernateUtil
                GeneralMethods generalMethods = GeneralMethods.getInstance("password");
                generalMethods.deleteDatabaseTemporalFile();
                System.exit(Constants.CERO);
            });
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void txtFieldPasswordOnKeyReleased(){
        Integer length = txtFieldPassword.getText().length();
        String hidePassword = Constants.GLOBAL_POINT.repeat(length);
        Integer lastIndex = (txtFieldPassword.getText().length() - Constants.ONE) < 0 ? 0 : txtFieldPassword.getText().length() - Constants.ONE;
        if(lastIndex < Constants.CERO){
            password = Constants.EMPTY;
        } else {
            password += txtFieldPassword.getText().toCharArray()[(txtFieldPassword.getText().length() - Constants.ONE)];
        }
        txtFieldPassword.setText(hidePassword);
        System.out.println(password);
    }
}
