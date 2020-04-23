package mate.academy.webshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mate.academy.webshop.dao.ShoppingCartDao;
import mate.academy.webshop.db.Storage;
import mate.academy.webshop.model.ShoppingCart;

public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        Storage.addShoppingCart(cart);
        return cart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Storage.carts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.carts.stream()
                .map(ShoppingCart::new)
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        IntStream.range(0, Storage.carts.size())
                .filter(i -> cart.getId()
                        .equals(Storage.carts.get(i).getId()))
                .forEach(i -> Storage.carts.set(i, cart));
        return cart;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.carts.removeIf(cart -> cart.getId().equals(id));
    }
}
