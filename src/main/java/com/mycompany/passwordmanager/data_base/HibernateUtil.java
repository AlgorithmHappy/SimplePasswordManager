/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.data_base;

import com.mycompany.passwordmanager.entities.Accounts;
import java.util.Properties;
import org.hibernate.Session;
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

    static {
        try {
            // Crear configuración de Hibernate
            Configuration configuration = new Configuration();

            // Configuración de propiedades
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "org.sqlite.JDBC");
            settings.put(Environment.URL, "jdbc:sqlite:database.accounts");
            settings.put(Environment.DIALECT, "org.hibernate.community.dialect.SQLiteDialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.FORMAT_SQL, "true");
            settings.put(Environment.HBM2DDL_AUTO, "update"); // Auto-creación de tablas

            configuration.setProperties(settings);

            // Agregar clases de entidad
            configuration.addAnnotatedClass(Accounts.class);

            // Crear ServiceRegistry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            // Crear SessionFactory
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}