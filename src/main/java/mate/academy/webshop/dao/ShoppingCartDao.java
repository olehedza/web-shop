package mate.academy.webshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.ShoppingCart;

public interface ShoppingCartDao {

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getByUserId(Long userId);

    List<Product> getAllProducts(ShoppingCart shoppingCart);
}
