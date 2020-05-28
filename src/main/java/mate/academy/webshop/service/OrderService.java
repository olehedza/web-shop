package mate.academy.webshop.service;

import java.util.List;
import mate.academy.webshop.model.Order;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.User;

public interface OrderService {
    Order completeOrder(List<Product> products, Long userId);

    List<Order> getUserOrders(User user);

    Order get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
