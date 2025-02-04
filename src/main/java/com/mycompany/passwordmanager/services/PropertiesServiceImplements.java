package com.mycompany.passwordmanager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mycompany.passwordmanager.utils.GeneralMethods;
import com.mycompany.passwordmanager.utils.constants.Constants;

/*
 * Clase que implementa los metodos de la interfaz PropertiesServiceInterface que se utilizaran para realizar operaciones con el archivo de propiedades
 */
public class PropertiesServiceImplements implements PropertiesServiceInterface {

    // Clase que contiene metodos generales, entre ellas el archivo de propiedades
    private GeneralMethods generalMethods;
    // Propiedades del archivo de propiedades
    private Properties properties;

    /*
     * Constructor que inicializa la clase GeneralMethods, para que inmediatamente despues inicialize las propiedades del archivo de propiedades (con password)
     */
    public PropertiesServiceImplements(String password) {
        generalMethods = GeneralMethods.getInstance(password);
        properties = generalMethods.getPropertiesManager().getProperties();
    }

    /*
     * Constructor que inicializa la clase GeneralMethods, para que inmediatamente despues inicialize las propiedades del archivo de propiedades (sin password)
     */
    public PropertiesServiceImplements() {
        generalMethods = GeneralMethods.getInstance();
        properties = generalMethods.getPropertiesManager().getProperties();
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Boolean isPersonalDatabaseEmpty() {
        String hibernatePersonalUrl = "";
        hibernatePersonalUrl = generalMethods.getPropertiesManager().getProperties().getProperty(Constants.PROPERTY_URL_HIBERNATE_PERSONAL + Constants.CERO);
        return hibernatePersonalUrl.isBlank();
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public String getPersonalDatabasePath() {
        return properties.getProperty(Constants.PROPERTY_URL_HIBERNATE_PERSONAL + Constants.CERO);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public String getTemporalDatabasePath() {
        return properties.getProperty(Constants.PROPERTY_URL_HIBERNATE_TEMPORAL);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public String setPersonalDatabasePath(String pathPeronalDatabase) {
        generalMethods.getPropertiesManager().savePropertiesFile(Constants.PROPERTY_URL_HIBERNATE_PERSONAL, pathPeronalDatabase);
        return pathPeronalDatabase;
    }

    /*
     * Obtiene todos los paths de las base de datos que el usuario ha creado
     * @return Lista de directorios o paths de las bases de datos que ha creado el usuario que estan en el archivo .properties
     */
    @Override
    public List<String> getPersonalDatabases() {
        List<String> paths = new ArrayList<>();
        Integer iterator = Constants.CERO;
        while (generalMethods.getPropertiesManager().getProperties().containsKey(Constants.PROPERTY_URL_HIBERNATE_PERSONAL + iterator) ) {
            paths.add(generalMethods.getPropertiesManager().getProperties().getProperty(Constants.PROPERTY_URL_HIBERNATE_PERSONAL + iterator) );
            iterator++;
        }
        return paths;
    }
}
