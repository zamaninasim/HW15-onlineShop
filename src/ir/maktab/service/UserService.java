package ir.maktab.service;

import ir.maktab.dao.Dao;
import ir.maktab.dao.UserDao;
import ir.maktab.model.User;

import java.util.List;

public class UserService extends Dao {
    UserDao userDao = new UserDao();

    public void save(User user) {
        userDao.save(user);
    }

    public User findByPhoneNumber(String phoneNumber) {
        List<User> users = userDao.findByPhoneNumber(phoneNumber);
        User user = users.get(0);
        return user;
    }

    public Boolean isUserExist(String phoneNumber) {
        List<User> users = userDao.findByPhoneNumber(phoneNumber);
        int size = users.size();
        return size != 0;
    }

}
