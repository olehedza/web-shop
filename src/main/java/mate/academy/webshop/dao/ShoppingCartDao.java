package mate.academy.webshop.dao;

import mate.academy.webshop.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartDao extends GenericDao<ShoppingCart> {
    Optional<ShoppingCart> getByUserId(Long userId);
}
