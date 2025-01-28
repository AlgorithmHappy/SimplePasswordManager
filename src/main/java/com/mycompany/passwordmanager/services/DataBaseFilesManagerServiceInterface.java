package com.mycompany.passwordmanager.services;

/*
 * Interfaz que define los metodos que se utilizaran para la gestion de archivos de la base de datos de SQLite
 */
public interface DataBaseFilesManagerServiceInterface {
    /*
     * Metodo que crea una base de datos temporal desencriptada en la ruta especificada
     */
    public void temporarilyDecryptDataBase();

    /*
     * Metodo que reemplaza la base de datos vieja por la nueva encriptada
     */
    public void saveEncryptedDataBase();

    /*
     * Metodo que cierra las conexiones y el archivo de la base de datos
     */
    public void closeDatabaseFile();
}
