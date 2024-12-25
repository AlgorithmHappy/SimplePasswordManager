/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.utils.constants;

/**
 *
 * @author Luis Gerardo Marquez
 */
public class Constants {
    
    // Ventanas fxml's
    public static final String MAIN_WINDOW = "MainWindow";
    public static final String DIALOG_INSERT_WINDOW = "/com/mycompany/passwordmanager/DialogInsertWindow.fxml";
    public static final String DIALOG_UPDATE_WINDOW = "/com/mycompany/passwordmanager/DialogUpdateWindow.fxml";
    
    // Titulos de ventanas
    public static final String TITLE_DIALOG_INSERT_WINDOW = "Insertar cuenta";
    public static final String TITLE_DIALOG_UPDATE_WINDOW = "Actualizar cuenta";

    // Propiedades de widgets
    public static final Double PREF_SIZE_HIDE = 0.0;
    
    // Constantes generales
    public static final String EMPTY = "";
    
    // Expresiones regulares
    // Expresión regular para validar el correo electrónico, si esta expresion es cierta el correo es correcto
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    // Si esta expresion es cierta el string solo contiene numeros
    public static final String REGEX_NUMERIC = "^\\d+$";
}
