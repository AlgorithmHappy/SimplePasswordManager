package com.mycompany.passwordmanager.database;

import org.hibernate.SessionFactory;

import com.mycompany.passwordmanager.utils.PropertiesManager;

import jakarta.persistence.EntityManager;

/*
 * Clase que administra la conexion a base de datos con hibernate
 */
public class Conexion {
    // Conexion a la base de datos
    private EntityManager entityManager;
    // Sesion de hibernate
    private SessionFactory sessionFactory;

    /*
     * Constructor que crea la conexion a la base de datos
     * @param properties Propiedades para la conexion a la base de datos
     */
    public Conexion(PropertiesManager properties) {
        sessionFactory = properties.getDataBaseConfiguration().buildSessionFactory();
        entityManager = sessionFactory.createEntityManager();
    }

    /*
    * Metodo que devuelve la conexion a la base de datos
    */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /*
     * Metodo que cierra la conexion a la base de datos
     */
    public void close() {
        entityManager.close();
        sessionFactory.close();
    }
}
