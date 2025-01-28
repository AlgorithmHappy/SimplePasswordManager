package com.mycompany.passwordmanager.services;

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
     * Constructor que inicializa la clase GeneralMethods, para que inmediatamente despues inicialize las propiedades del archivo de propiedades
     */
    public PropertiesServiceImplements(String password) {
        generalMethods = GeneralMethods.getInstance(password);
        properties = generalMethods.getPropertiesManager().getProperties();
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Boolean isPersonalDatabaseEmpty() {
        String hibernatePersonalUrl = "";
        hibernatePersonalUrl = generalMethods.getPropertiesManager().getProperties().getProperty(Constants.PROPERTY_URL_HIBERNATE_PERSONAL);
        return hibernatePersonalUrl.isBlank();
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public String getPersonalDatabasePath() {
        return properties.getProperty(Constants.PROPERTY_URL_HIBERNATE_PERSONAL);
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
}
