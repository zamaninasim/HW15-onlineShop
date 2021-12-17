package ir.maktab.dao;

import ir.maktab.model.Order;
import ir.maktab.model.Product;
import ir.maktab.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDao extends Dao {

    public void save(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
        session.close();
    }

    public List<Order> thisUserOrderedThisProduct(User user, Product product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Order> query = session.createQuery("FROM orders o WHERE o.user=:userValue AND o.product=:productValue");
        query.setParameter("userValue", user);
        query.setParameter("productValue", product);
        List<Order> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    public void update(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(order);
        transaction.commit();
        session.close();
    }

    public List<Order> findReservedOrderOfUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Order> query = session.createQuery("FROM orders o WHERE o.user=:userValue and orderStatus='RESERVED'");
        query.setParameter("userValue", user);
        List<Order> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }
}

