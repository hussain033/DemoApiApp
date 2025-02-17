package com.example.consoleApp.service;

import com.example.consoleApp.model.Cart;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryClass {

    private static final SessionFactory sessionFactory;

    static {
        Configuration config = new Configuration();

        config.addAnnotatedClass(Cart.class); // telling hibernate to handle this class

        config.configure(); // locate and load the hibernate config file

        sessionFactory = config.buildSessionFactory();

    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
