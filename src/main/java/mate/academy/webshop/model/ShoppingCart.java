package mate.academy.webshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private User user;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    public ShoppingCart(User user) {
        this.products = new ArrayList<>();
        this.user = user;
    }

    public ShoppingCart(List<Product> products, User user) {
        this.products = products;
        this.user = user;
    }

    public ShoppingCart(ShoppingCart shoppingCart) {
        id = shoppingCart.getId();
        products = shoppingCart.getProducts().stream()
                .map(Product::new)
                .collect(Collectors.toList());
        user = new User(shoppingCart.user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" + "id=" + id
                + ", products=" + products
                + ", user=" + user + '}';
    }
}
