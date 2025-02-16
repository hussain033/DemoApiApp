package com.example.consoleApp.repository;

import com.example.consoleApp.model.Cart;
import com.example.consoleApp.model.CartId;
import com.example.consoleApp.service.SessionFactoryClass;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartDaoImpl implements CartDao {

    Session session;

    @Override
    public void add(Cart cart) {
        session = SessionFactoryClass.getSession();

        Transaction transaction = session.beginTransaction();

        session.persist(cart);

        transaction.commit();

        session.close();
    }

    @Override
    public void update(Cart cart) {
        session = SessionFactoryClass.getSession();

        Transaction transaction = session.beginTransaction();

        session.merge(cart);

        transaction.commit();

        session.close();
    }

    @Override
    public void remove(CartId cartId) {
        session = SessionFactoryClass.getSession();

        Transaction transaction = session.beginTransaction();

        Cart cart = session.get(Cart.class, cartId);

        session.remove(cart);

        transaction.commit();

        session.close();
    }

    @Override
    public List<Cart> list(Long userId) {
        session = SessionFactoryClass.getSession();


        session.close();
        return List.of();
    }

    @Override
    public Optional<Cart> get(CartId cartId) {
        session = SessionFactoryClass.getSession();

        Cart cart = session.get(Cart.class, cartId);

        session.close();

        return Optional.of(cart);
    }
}
