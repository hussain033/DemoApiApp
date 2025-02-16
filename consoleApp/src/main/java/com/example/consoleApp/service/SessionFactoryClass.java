package com.example.consoleApp.service;

import com.example.consoleApp.model.Cart;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
@Service
public class SessionFactoryClass {

    private static final SessionFactory sessionFactory;

    static {
        Configuration config = new Configuration();

        config.addAnnotatedClass(Cart.class);

        config.configure();

        sessionFactory = config.buildSessionFactory();
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

}
