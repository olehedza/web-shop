package mate.academy.webshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.webshop.dao.OrderDao;
import mate.academy.webshop.db.Storage;
import mate.academy.webshop.lib.Inject;
import mate.academy.webshop.lib.Service;
import mate.academy.webshop.model.Order;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.User;
import mate.academy.webshop.service.OrderService;
import mate.academy.webshop.service.ShoppingCartService;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService cartService;

    @Override
    public Order completeOrder(List<Product> products, Long userId) {
        List<Product> productList = products.stream()
                .map(Product::new)
                .collect(Collectors.toList());
        Order order = new Order(productList, userId);
        cartService.getByUserId(userId)
                .getProducts().clear();
        return orderDao.create(order);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return Storage.orders.stream()
                .filter(o -> o.getUserId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
