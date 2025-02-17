package com.example.consoleApp.repository;

import com.example.consoleApp.model.Cart;
import com.example.consoleApp.model.CartId;
import com.example.consoleApp.service.SessionFactoryClass;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    ItemRepository itemRepository;

    @Override
    public void save(Cart cart) {
        try {
            Session session = SessionFactoryClass.getSession();
            Transaction transaction = session.beginTransaction();

            session.persist(cart);

            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Long userId, Long itemId) {

        try {
            Session session3 = SessionFactoryClass.getSession();
            Transaction transaction1 = session3.beginTransaction();

            Cart cart = get(userId, itemId);

            session3.remove(cart);

            transaction1.commit();
            session3.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart get(Long userId, Long itemId) {

        CartId cartId = new CartId(userId, itemId);

        Session session = SessionFactoryClass.getSession();

        Cart cart = session.get(Cart.class, cartId);

        session.close();

        return cart;
    }

    @Override
    public List<Cart> listAll(Long userId) {
        return List.of();
    }
}
