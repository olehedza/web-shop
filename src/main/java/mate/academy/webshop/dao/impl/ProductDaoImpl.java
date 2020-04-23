package mate.academy.webshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mate.academy.webshop.dao.ProductDao;
import mate.academy.webshop.db.Storage;
import mate.academy.webshop.model.Product;

public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products.stream()
                .map(Product::new)
                .collect(Collectors.toList());
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.products.size())
                .filter(i -> product.getId()
                        .equals(Storage.products.get(i).getId()))
                .forEach(i -> Storage.products.set(i, product));
        return product;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.products.removeIf(p -> p.getId().equals(id));
    }
}
