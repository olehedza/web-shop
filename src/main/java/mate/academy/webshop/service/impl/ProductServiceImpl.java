package mate.academy.webshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.webshop.dao.ProductDao;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.service.ProductService;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;

    @Override
    public Product create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Product get(Long id) {
        return productDao.get(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    public boolean delete(Long id) {
        return productDao.delete(id);
    }
}

