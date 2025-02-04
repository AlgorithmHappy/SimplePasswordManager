package com.mycompany.passwordmanager.utils;

import com.mycompany.passwordmanager.database.Conexion;
import com.mycompany.passwordmanager.utils.constants.Constants;

/*
 * Clase que contiene todo lo relacionado a las propiedades, encriptacion y conexion a la base de datos, es un singleton y fachada a la vez,
 * obviamente tambien tiene metodos generales
 */
public class GeneralMethods {
    // Metodo de la misma clase para hacer un singleton
    private static GeneralMethods instance;
    // Clase que gestiona las propiedades
    private PropertiesManager propertiesManager;
    // Clase que encripta y desencripta los archivos
    private EncryptAndDecrypt encryptAndDecrypt;
    // Clase que gestiona la conexion a la base de datos
    //private Conexion conexion;

    /*
     * Constructor vacio y privado porque solo habra una instancia de este metodo
     * @param password Contraseña para encriptar y desencriptar
     */
    private GeneralMethods(String password) { 
        if(propertiesManager == null)
            propertiesManager = new PropertiesManager();
        encryptAndDecrypt = new EncryptAndDecrypt(password);
        //conexion = new Conexion(propertiesManager);
    }

    /*
     * Constructor que inicializa las propiedades sin tener que desencriptar la base de datos para que no pida como argumento el password
     */
    private GeneralMethods(){
        propertiesManager = new PropertiesManager();
    }

    /*
     * Metodo que devuelve la instancia de la clase
     * @param password Contraseña para encriptar y desencriptar
     */
    public static GeneralMethods getInstance(String password) {
        if (instance == null) {
            instance = new GeneralMethods(password);
        } 
        return instance;
    }

    /*
     * Metodo que devuelve la instancia de la clase (sin password para que inicialice solo las propiedades)
     * @param password Contraseña para encriptar y desencriptar
     */
    public static GeneralMethods getInstance() {
        if (instance == null) {
            instance = new GeneralMethods();
        }
        return instance;
    }

    /*
     * Metodo getter que devuelve la clase que gestiona las propiedades
     * @return propertiesManager Clase que gestiona las propiedades
     */
    public PropertiesManager getPropertiesManager() {
        return propertiesManager;
    }

    /*
     * Metodo getter que devuelve la clase que encripta y desencripta las bases de datos SQLite
     * @return encryptAndDecrypt Clase que encripta y desencripta las bases de datos SQLite
     */
    public EncryptAndDecrypt getEncryptAndDecrypt() {
        return encryptAndDecrypt;
    }

    /*
     * Metodo getter que devuelve la clase que gestiona la conexion a la base de datos
     * @return conexion Clase que gestiona la conexion a la base de datos
     */
    /*public Conexion getConexion() {
        if(conexion == null){
            conexion = new Conexion(propertiesManager);
        }
        return conexion;
    }*/

    public void deleteDatabaseTemporalFile(){
        String rootPath = System
            .getProperty(Constants.USER_DIRECTORY)
            .replace(Constants.BACK_SLASH, Constants.SLASH)
            .concat(Constants.SLASH);
        encryptAndDecrypt.delateTemporalDatabase(rootPath + propertiesManager.getProperties().getProperty(Constants.PROPERTY_URL_HIBERNATE_TEMPORAL));
    }

    /*
     * Elimina el objeto Conexion
     */
    /*public void closeConexion(){
        conexion.close();
    }*/

    /*
     * Vuelve a abrir la conexion
     */
    /*public void openConexion(){
        conexion.open();
    }*/
}
