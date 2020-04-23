package mate.academy.webshop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.webshop.dao.OrderDao;
import mate.academy.webshop.model.Order;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.User;

public class OrderDaoImpl implements OrderDao {
    @Override
    public Order completeOrder(List<Product> products, User user) {
        return null;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return null;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Optional.empty();
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
