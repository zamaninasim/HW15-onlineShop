package ir.maktab.dao;

import ir.maktab.model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDao extends Dao {

    public void save(Product product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(product);
        transaction.commit();
        session.close();
    }

    public List<Product> findAllProduct() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Product> query = session.createQuery("FROM Product ");
        List<Product> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    public Product findById(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Product product = session.get(Product.class, id);
        transaction.commit();
        session.close();
        return product;
    }

    public void update(Product product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(product);
        transaction.commit();
        session.close();
    }
}
