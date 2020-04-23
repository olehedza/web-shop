package mate.academy.webshop.service.impl;

import java.util.List;
import mate.academy.webshop.model.Order;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.User;
import mate.academy.webshop.service.OrderService;

public class OrderServiceImpl implements OrderService {
    @Override
    public Order completeOrder(List<Product> products, User user) {
        return null;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return null;
    }

    @Override
    public Order get(Long id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
