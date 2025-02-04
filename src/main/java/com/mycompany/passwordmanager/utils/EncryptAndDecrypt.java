package com.mycompany.passwordmanager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.mycompany.passwordmanager.utils.constants.Constants;

/*
 * Clase que encripta y desencripta los archivos creados de SQLite
 */
public class EncryptAndDecrypt {

    // Password ya encriptado para poder trabajar con los archivos y desencriptarlos y encriptarlos
    private SecretKey secretKey;

    /*
     * El constructor si o si necesita la contraseña para encriptar y desencriptar los archivos
     * @param password Contraseña para encriptar y desencriptar
     */
    public EncryptAndDecrypt(String password) {
        this.secretKey = generateKeyFromPassword(password);
    }

    /*
     * Metodo que copia el archivo encriptado del path del primer argumento y lo desecripta en el path del segundo argumento
     * @param pathEncryptDataBase Path del archivo encriptado
     * @param pathDecryptDataBase Path del archivo desencriptado
     */
    public void copyEncryptDatabesToDecryptDataBase(String pathEncryptDataBase, String pathDecryptDataBase) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            FileInputStream fis = new FileInputStream(pathEncryptDataBase);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            FileOutputStream fos = new FileOutputStream(pathDecryptDataBase);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Metodo que copia el archivo en claro del path del primer argumento y lo encripta en el path del segundo argumento
     * @param pathEncryptDataBase Path del archivo encriptado
     * @param pathDecryptDataBase Path del archivo desencriptado
     */
    public void copyDecryptDataBaseToEncryptDataBase(String pathDecryptDataBase, String pathEncryptDataBase) {
        try{
            Cipher cipher = Cipher.getInstance(Constants.ENCRIPT_ENCODE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            FileInputStream fis = new FileInputStream(pathDecryptDataBase);
            FileOutputStream fos = new FileOutputStream(pathEncryptDataBase);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }      
    }

    /*
     * Metodo que genera una clave secreta a partir de la contraseña
     */
    private SecretKey generateKeyFromPassword(String password) {
        try {
            byte[] saltBytes = Constants.ENCRIPT_SALT.getBytes();
            SecretKeyFactory factory;
            factory = SecretKeyFactory.getInstance(Constants.ENCRIPT_HASH_METHOD);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, Constants.ENCRIPT_ITERATIONS, Constants.ENCRIPT_KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            return new SecretKeySpec(tmp.getEncoded(), Constants.ENCRIPT_ENCODE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Metodo que elimina la base de datos temporal
     * @param pathTemporalDatabase Path de la base de datos temporal
     */
    public void delateTemporalDatabase(String pathTemporalDatabase){
        // Ruta del archivo a eliminar
        Path path = Paths.get(pathTemporalDatabase);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(pathTemporalDatabase);
    }
}
