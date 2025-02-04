package com.mycompany.passwordmanager.services;

import java.util.List;

/*
 * Interfaz que define los metodos que se utilizaran para el archivo de propiedades
 */
public interface PropertiesServiceInterface {
    public Boolean isPersonalDatabaseEmpty();
    public String setPersonalDatabasePath(String pathPeronalDatabase);
    public String getPersonalDatabasePath();
    public String getTemporalDatabasePath();
    public List<String> getPersonalDatabases();
}
