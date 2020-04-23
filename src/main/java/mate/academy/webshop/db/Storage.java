package mate.academy.webshop.db;

import java.util.ArrayList;
import java.util.List;
import mate.academy.webshop.model.Order;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.model.User;

public class Storage {
    public static final List<Order> orders = new ArrayList<>();
    public static final List<Product> products = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    private static Long productId = 0L;

    public static void addProduct(Product product) {
        product.setId(++productId);
        products.add(product);
    }
}
