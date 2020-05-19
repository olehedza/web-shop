package mate.academy.webshop.dao;

import java.util.List;
import mate.academy.webshop.model.Order;
import mate.academy.webshop.model.User;

public interface OrderDao extends GenericDao<Order> {
    List<Order> getUserOrders(User user);
}
