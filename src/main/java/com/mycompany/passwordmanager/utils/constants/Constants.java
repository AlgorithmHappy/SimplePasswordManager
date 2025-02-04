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
    public static final String MAIN_WINDOW = "/com/mycompany/passwordmanager/MainWindow.fxml";
    public static final String LOGIN_WINDOW = "Login.fxml";
    public static final String TITLE_WINDOW_LOGIN = "Ingresar";
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

    public static final String PATH_PROPERTIES = "/src/main/resources/com/mycompany/passwordmanager/config.properties";
    public static final String PATH_USER = "user.dir";

    public static final String DATA_BASE_PERSONAL_URL = "personal_url";
    public static final String DATA_BASE_TEMPORAL_URL = "temporal_url";
    public static final String DATA_BASE_DYNAMIC_PERSISTENCE = "dynamic-persistence-unit";
    public static final String DATA_BASE_QUERY_ALL_ACCOUNTS = "FROM Accounts";
    public static final String PROPERTY_URL_HIBERNATE = "hibernate.connection.url";
    public static final String PROPERTY_URL_HIBERNATE_PERSONAL = "hibernate.connection.url.personal.";
    public static final String PROPERTY_URL_HIBERNATE_TEMPORAL = "hibernate.connection.url.temporal";
    public static final String ENCRIPT_SALT = "12345678";
    public static final String ENCRIPT_HASH_METHOD = "PBKDF2WithHmacSHA256";
    public static final Integer ENCRIPT_ITERATIONS = 65536;
    public static final Integer ENCRIPT_KEY_LENGTH = 256;
    public static final String ENCRIPT_ENCODE = "AES";
    public static final String USER_DIRECTORY = "user.dir";
    public static final String CONFIG_PATH = "/src/main/resources/com/mycompany/passwordmanager/config.properties";
    public static final String BACK_SLASH ="\\";
    public static final String SLASH = "/";

    public static final Integer CERO = 0;
    public static final Integer ONE = 1;

    public static final String GLOBAL_ABRIR = "Abrir...";
    public static final String GLOBAL_POINT = "•";
}
