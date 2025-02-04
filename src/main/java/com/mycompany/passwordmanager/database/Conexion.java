package com.mycompany.passwordmanager.database;

import org.hibernate.SessionFactory;

import com.mycompany.passwordmanager.utils.PropertiesManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/*
 * Clase que administra la conexion a base de datos con hibernate
 */
public class Conexion {
    // Conexion a la base de datos
    //private EntityManager entityManager;
    // Sesion de hibernate
    //private SessionFactory sessionFactory;
    // Fabrica deentityManagers
    //private EntityManagerFactory entityManagerFactory;
    // Clase que contiene la configuracion
    //private PropertiesManager properties;


    /*
     * Constructor que crea la conexion a la base de datos
     * @param properties Propiedades para la conexion a la base de datos
     */
    /*public Conexion(PropertiesManager properties) {
        this.properties = properties;
        sessionFactory = this.properties.getDataBaseConfiguration().buildSessionFactory();
        entityManagerFactory = sessionFactory.createEntityManager().getEntityManagerFactory();
        entityManager = entityManagerFactory.createEntityManager();
    }*/

    /*
    * Metodo que devuelve la conexion a la base de datos
    */
    /*public EntityManager getEntityManager() {
        return entityManager;
    }*/

    /*
     * Metodo que cierra la conexion a la base de datos
     */
    /*public void close() {
        entityManagerFactory.close();
        System.out.println("-------------------Conexion del entiti manager factory: " + entityManagerFactory.isOpen());
        System.out.println("-------------------Conexion del entiti manager: " + entityManager.isOpen());
        System.out.println("-------------------Conexion del session factory: " + sessionFactory.isOpen());
        entityManager.close();
        System.out.println("-------------------Conexion del entiti manager factory: " + entityManagerFactory.isOpen());
        System.out.println("-------------------Conexion del entiti manager: " + entityManager.isOpen());
        System.out.println("-------------------Conexion del session factory: " + sessionFactory.isOpen());
        sessionFactory.close();
        System.out.println("-------------------Conexion del entiti manager factory: " + entityManagerFactory.isOpen());
        System.out.println("-------------------Conexion del entiti manager: " + entityManager.isOpen());
        System.out.println("-------------------Conexion del session factory: " + sessionFactory.isOpen());
    }*/

    /*
     * Metodo para abrir la conexion
     */
    /*public void open() {
        System.out.println("-------------------Conexion del entiti manager factory: " + entityManagerFactory.isOpen());
        System.out.println("-------------------Conexion del entiti manager: " + entityManager.isOpen());
        System.out.println("-------------------Conexion del session factory: " + sessionFactory.isOpen());
        if(sessionFactory.isClosed()){
            sessionFactory = properties.getDataBaseConfiguration().buildSessionFactory();
            entityManagerFactory = sessionFactory.createEntityManager().getEntityManagerFactory();
            entityManager = entityManagerFactory.createEntityManager();
        }
    }*/
}
