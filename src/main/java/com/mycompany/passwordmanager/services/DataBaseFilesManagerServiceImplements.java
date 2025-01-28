package com.mycompany.passwordmanager.services;

import com.mycompany.passwordmanager.utils.EncryptAndDecrypt;
import com.mycompany.passwordmanager.utils.GeneralMethods;

/*
 * {@inheritDoc}. Implementacion de la interfaz
 */
public class DataBaseFilesManagerServiceImplements implements DataBaseFilesManagerServiceInterface {

    // Clase que contiene metodos generales, entre ellos el de gestion de archivos de base de datos para encriptar y desencriptar
    private GeneralMethods generalMethods;
    // Clase que contiene los metodos para la desencriptacion y encriptacion de archivos de base de datos
    private EncryptAndDecrypt encryptAndDecrypt;
    // Ruta de la base de datos temporal con la que se trabaja
    private String pathTemporalDataBase;
    // Ruta de la base de datos que importa el usuario (encriptada)
    private String pathPersonalDataBase;

    /*
     * Constructor para el manejo de archivos de base de datos de desencriptado y encriptado
     * @param password Contraseña para encriptar y desencriptar las bases de datos sqlite
     * @param pathTemporalDataBase Ruta de la base de datos temporal con la que se trabaja
     * @param pathPersonalDataBase Ruta de la base de datos que importa el usuario (encriptada)
     */
    public DataBaseFilesManagerServiceImplements(String password, String pathTemporalDataBase, String pathPersonalDataBase) {
        generalMethods = GeneralMethods.getInstance(password);
        encryptAndDecrypt = generalMethods.getEncryptAndDecrypt();
        this.pathTemporalDataBase = pathTemporalDataBase;
        this.pathPersonalDataBase = pathPersonalDataBase;
    }

    /*
    * {@inheritDoc}.
    */
    @Override
    public void temporarilyDecryptDataBase() {
        encryptAndDecrypt.copyEncryptDatabesToDecryptDataBase(pathPersonalDataBase, pathTemporalDataBase);
    }

    /*
    * {@inheritDoc}.
    */
    @Override
    public void saveEncryptedDataBase() {
        encryptAndDecrypt.copyDecryptDataBaseToEncryptDataBase(pathTemporalDataBase, pathPersonalDataBase);
    }

    /*
    * {@inheritDoc}.
    */
    @Override
    public void closeDatabaseFile() {
        generalMethods.getConexion().close();
    }
}
