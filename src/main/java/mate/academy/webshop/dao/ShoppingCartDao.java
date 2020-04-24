package mate.academy.webshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.webshop.model.ShoppingCart;

public interface ShoppingCartDao {

    ShoppingCart create(ShoppingCart cart);

    Optional<ShoppingCart> get(Long id);

    List<ShoppingCart> getAll();

    ShoppingCart update(ShoppingCart cart);

    boolean delete(Long id);
}
