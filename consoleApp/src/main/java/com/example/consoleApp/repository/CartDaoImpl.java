package com.example.consoleApp.repository;

import com.example.consoleApp.model.Cart;
import com.example.consoleApp.model.CartId;
import com.example.consoleApp.service.SessionFactoryClass;
import jakarta.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    @Override
    public void save(Cart cart) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = SessionFactoryClass.getSession();
            transaction = session.beginTransaction();

            session.persist(cart);

            transaction.commit();

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        } finally {
            if(transaction != null) transaction.rollback();
            if(session != null) session.close();
        }
    }

    @Override
    public void update(Cart cart) {
        try {
            Session session = SessionFactoryClass.getSession();
            Transaction transaction = session.beginTransaction();

            session.merge(cart);

            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Long userId, Long itemId) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = SessionFactoryClass.getSession();
            transaction = session.beginTransaction();

            Cart cart = get(userId, itemId);

            session.remove(cart);

            transaction.commit();

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        } finally {

            if(transaction != null) transaction.rollback();
            if(session != null) session.close();
        }
    }

    @Override
    public Cart get(Long userId, Long itemId) {

        CartId cartId = null;
        Session session = null;
        Cart cart = null;
        try {
            cartId =  new CartId(userId, itemId);

            session = SessionFactoryClass.getSession();

            cart = session.get(Cart.class, cartId);

            return cart;

        } catch (Exception e) {

            throw new RuntimeException(e);

        } finally {

            session.close();
        }




    }

    @Override
    public List<Cart> listAll(Long userId) {

        try (Session session = SessionFactoryClass.getSession()) {

            String query = "select c from Cart c where c.userId = :id";
            Query sqlQuery = session.createQuery(query, Cart.class);
            sqlQuery.setParameter("id", userId);

            return (List<Cart>) sqlQuery.getResultList();
        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }
}
