package ir.maktab.dao;

import ir.maktab.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDao extends Dao {

    public void save(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
        session.close();
    }

    public List<User> findByPhoneNumber(String phoneNumber) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("FROM User u WHERE u.phoneNumber=:phoneNumberValue");
        query.setParameter("phoneNumberValue", phoneNumber);
        List<User> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }
}
