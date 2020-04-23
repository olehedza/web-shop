package mate.academy.webshop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.webshop.dao.ShoppingCartDao;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.ShoppingCart;

public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        return null;
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return false;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {

    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Optional.empty();
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return null;
    }
}
