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
    private static Long shoppingCartId = 0L;
    private static Long userId = 0L;
    private static Long orderId = 0L;

    public static void addProduct(Product product) {
        product.setId(++productId);
        products.add(product);
    }

    public static void addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setId(++shoppingCartId);
        shoppingCarts.add(shoppingCart);
    }

    public static void addUser(User user) {
        user.setId(++userId);
        users.add(user);
    }

    public static void addOrder(Order order) {
        order.setId(++orderId);
        orders.add(order);
    }
}
