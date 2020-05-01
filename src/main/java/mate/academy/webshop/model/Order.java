package mate.academy.webshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private Long id;
    private List<Product> products;
    private User user;

    public Order() {
    }

    public Order(User user) {
        products = new ArrayList<>();
        this.user = user;
    }

    public Order(List<Product> products, User user) {
        this.products = products;
        this.user = user;
    }

    public Order(Order order) {
        id = order.getId();
        products = order.products.stream()
                .map(Product::new)
                .collect(Collectors.toList());
        user = new User(order.getUser());
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

    public double getCommonPrice() {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id
                + ", products=" + products
                + ", user=" + user + '}';
    }
}
