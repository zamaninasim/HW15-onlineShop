package ir.maktab.service;

import ir.maktab.dao.OrderDao;
import ir.maktab.model.Order;
import ir.maktab.model.Product;
import ir.maktab.model.User;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    OrderDao orderDao = new OrderDao();

    public void save(Order order) {
        orderDao.save(order);
    }

    public boolean isThisUserOrderedThisProduct(User user, Product product) {
        List<Order> orders = orderDao.thisUserOrderedThisProduct(user, product);
        int size = orders.size();
        return size != 0;
    }

    public Order orderedThisProduct(User user, Product product) {
        List<Order> orders = orderDao.thisUserOrderedThisProduct(user, product);
        if (orders.size() > 0) {
            return orders.get(0);
        }
        return null;
    }

    public void update(Order order) {
        orderDao.update(order);
    }

    public boolean isUserHaveOrder(User user) {
        List<Order> orders = orderDao.findReservedOrderOfUser(user);
        int size = orders.size();
        return size != 0;
    }

    public List<Order> findReservedOrderOfUser(User user) {
        return orderDao.findReservedOrderOfUser(user);
    }

    public List<Long> calculateFinalPriceOfUserOrders(User user) {
        List<Long> prices = new ArrayList<>();
        List<Order> reservedOrderOfUser = orderDao.findReservedOrderOfUser(user);
        for (Order order : reservedOrderOfUser) {
            Product product = order.getProduct();
            Integer count = order.getCount();
            Long price = product.getPrice() * count;
            prices.add(price);
        }
        return prices;
    }
}
