package com.mycompany.passwordmanager.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.cfg.Configuration;

import com.mycompany.passwordmanager.entities.Accounts;
import com.mycompany.passwordmanager.utils.constants.Constants;

/*
 * Esta clase va a gestionar todo lo relacionado con el fichero de propiedades "config.properties"
 */
public class PropertiesManager {

    // Archivo de propiedades
    private Properties properties;

    /*
     * Constructor que crea el objeto que carga el archivo de propiedades y el de configuraciones para la base de datos
     */
    public PropertiesManager() {
        properties = new Properties();
        try {
            FileInputStream fileInput = new FileInputStream(
                System
                .getProperty(Constants.USER_DIRECTORY)
                .replace(Constants.BACK_SLASH, Constants.SLASH) +
                Constants.CONFIG_PATH
            );
            properties.load(fileInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Getter que devuelve las propiedades del archivo de propiedades
     */
    public Properties getProperties() {
        return properties;
    }

    /*
     * Getter que devuelve la configuracion de la base de datos
     */
    public Configuration getDataBaseConfiguration(){
        Configuration dataBaseConfiguration = new Configuration();
        dataBaseConfiguration.setProperties(properties);
        dataBaseConfiguration.addAnnotatedClass(Accounts.class);
        return dataBaseConfiguration;
    }

    /*
     * Cambia el archivo de propiedades para que la aplicacion se conecte a la base de datos personal o temporal y guarda los cambios
     * @param urlMode Constante de la clase Constants para apuntar a la base de datos que se desea
     */
    public void setUrlDataBaseConfiguration(String urlMode){
        switch (urlMode) {
            case Constants.DATA_BASE_PERSONAL_URL:
                properties.setProperty(Constants.PROPERTY_URL_HIBERNATE, properties.getProperty(Constants.DATA_BASE_PERSONAL_URL));
                break;
            case Constants.DATA_BASE_TEMPORAL_URL:
                properties.setProperty(Constants.PROPERTY_URL_HIBERNATE, properties.getProperty(Constants.DATA_BASE_TEMPORAL_URL));
                break;
            default:
                break;
        }
        try {
            String aux = getClass().getResource(Constants.PATH_PROPERTIES).getFile();
            FileOutputStream output = new FileOutputStream(aux);
            properties.store(output, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Guarda el archivo de propiedades sobrescribiendo el valor de la clave que se le pasa
     * @param propertiKey Clave del archivo de propiedades
     * @param propertiValue Valor de la clave del archivo de propiedades
     */
    public void savePropertiesFile(String propertiKey, String propertiValue) {
        try {
            properties.setProperty(propertiKey, propertiValue);
            String aux = System.getProperty(Constants.PATH_USER)
                .replace(Constants.BACK_SLASH, Constants.SLASH) +
                Constants.PATH_PROPERTIES;
            FileOutputStream output = new FileOutputStream(aux);
            properties.store(output, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
