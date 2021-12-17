package ir.maktab.service;

import ir.maktab.dao.ProductDao;
import ir.maktab.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductDao productDao = new ProductDao();

    public void save(Product product) throws SQLException {
        productDao.save(product);
    }

    public List<Product> findAllProduct() {
        return productDao.findAllProduct();
    }

    public Product findById(Integer id) {
        return productDao.findById(id);
    }

    public void update(Product product) {
        productDao.update(product);
    }
}
