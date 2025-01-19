/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.data_base;

import com.mycompany.passwordmanager.App;
import com.mycompany.passwordmanager.entities.Accounts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author Luis Gerardo Marquez
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static Properties properties;

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    
    private static Properties getProperties(){
        if(properties == null){
            // Configuración de propiedades
            properties = new Properties();

            // Cargar el archivo
            try (FileInputStream fileInput = new FileInputStream(System.getProperty("user.dir").replace("\\", "/") + "/src/main/resources/com/mycompany/passwordmanager/config.properties") ) {
                properties.load(fileInput);
            } catch (IOException e) {
                System.err.println("Error al cargar el archivo: " + e.getMessage());
            }

            // Validar si el usuario ya creo su base de datos para guardar sus contraseñas, para abrir una base de datos temporal
            if(properties.getProperty("hibernate.connection.url.personal").isBlank() ){
                properties.setProperty("hibernate.connection.url", properties.getProperty("hibernate.connection.url.temporal") );
            } else {
                properties.setProperty("hibernate.connection.url", properties.getProperty("hibernate.connection.url.personal") );
            }
        }

        return properties;
    }

    public static void updateFilePropertiesAndSaveEncryptDatabase(String value, String password){
        // Se llama a la función getProperties para que se carguen las propiedades si no se han cargado para que no arroje un nullPointerException
        getProperties();
        value = "jdbc:sqlite:".concat(value);
        try {
            properties.setProperty("hibernate.connection.url.personal", value );
            FileOutputStream output = new FileOutputStream(
                System.getProperty("user.dir")
                    .replace("\\", "/") +
                    "/src/main/resources/com/mycompany/passwordmanager/config.properties"
            );
            properties.store(output, null);
            String pathWithinSQLite = properties.getProperty("hibernate.connection.url.temporal").replace("jdbc:sqlite:", "");
            saveAndEncriptFile(
                pathWithinSQLite,
                password
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory(String password) {
        if(sessionFactory == null){
            try {
                // Crear configuración de Hibernate
                Configuration configuration = new Configuration();
                
                if(getProperties().getProperty("hibernate.connection.url.personal").isBlank() ){
                    properties.setProperty("hibernate.connection.url", properties.getProperty("hibernate.connection.url.temporal"));
                    configuration.setProperties(getProperties() );

                    // Agregar clases de entidad
                    configuration.addAnnotatedClass(Accounts.class);

                    // Crear ServiceRegistry
                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .build();

                    // Crear SessionFactory
                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                } else {
                    doCrypto(
                        Cipher.DECRYPT_MODE,
                        password,
                        getProperties().getProperty("hibernate.connection.url.personal").replace("jdbc:sqlite:", ""),
                        getProperties().getProperty("hibernate.connection.url.personal").concat("_decripted").replace("jdbc:sqlite:", "")
                    );
                    properties.setProperty("hibernate.connection.url", getProperties().getProperty("hibernate.connection.url.personal").concat("_decripted") );
                    configuration.setProperties(getProperties() );

                    // Agregar clases de entidad
                    configuration.addAnnotatedClass(Accounts.class);

                    // Crear ServiceRegistry
                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .build();

                    // Crear SessionFactory
                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                }
                

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }

    public static void shutdown(String password) {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
        getProperties();
        String pathDataBaseTemporal = properties.getProperty("hibernate.connection.url.temporal");
        File file = new File(pathDataBaseTemporal.replace("jdbc:sqlite:", ""));
        file.delete();
        if(!properties.getProperty("hibernate.connection.url.personal").isBlank() ){
            file = new File(properties.getProperty("hibernate.connection.url.personal").replace("jdbc:sqlite:", "").concat("_decripted") );
            file.delete();
            /*try {
                doCrypto(
                    Cipher.ENCRYPT_MODE,
                    password,
                    properties
                        .getProperty("hibernate.connection.url.personal")
                        .replace("jdbc:sqlite:", "")
                        .concat("_decripted"),
                    properties.getProperty("hibernate.connection.url.personal")
                        .replace("jdbc:sqlite:", "") );
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }

    private static void doCrypto(int cipherMode, String key, String inputFile, String outputFile) throws Exception {
        // Generar un salt (número aleatorio único para esta clave)
        byte[] salt = generateSalt();
        // Iteraciones y tamaño de la clave
        int iterations = 65536; // Número de iteraciones (cuanto más alto, más seguro pero más lento)
        int keyLength = 256; // Tamaño de la clave en bits


        Key secretKey =  deriveKeyFromPassword(key, salt, iterations, keyLength);  //new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(cipherMode, secretKey);

        try {
            FileInputStream inputStream = new FileInputStream(inputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para generar un salt aleatorio
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes = 128 bits
        random.nextBytes(salt);
        return salt;
    }

    // Método para derivar la clave desde el password
    private static SecretKey deriveKeyFromPassword(String password, byte[] salt, int iterations, int keyLength) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    private static void saveAndEncriptFile(String pathDecripted, String password){
        try {
            Path pathFile = Paths.get(pathDecripted);
            // Leer el archivo original
            byte[] fileBytes = Files.readAllBytes(pathFile);

            // Generar un salt (número aleatorio único para esta clave)
            byte[] salt = generateSalt();
            // Iteraciones y tamaño de la clave
            int iterations = 65536; // Número de iteraciones (cuanto más alto, más seguro pero más lento)
            int keyLength = 256; // Tamaño de la clave en bits


            Key secretKey = deriveKeyFromPassword(password, salt, iterations, keyLength);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Cifrar el contenido del archivo
            byte[] encryptedBytes = cipher.doFinal(fileBytes);

            Path pathFileEncripted = Paths.get(properties.getProperty("hibernate.connection.url.personal").replace("jdbc:sqlite:", "") );
            Files.write(pathFileEncripted, encryptedBytes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}