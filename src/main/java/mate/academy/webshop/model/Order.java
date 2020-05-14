package mate.academy.webshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private Long id;
    private List<Product> products;
    private Long userId;

    public Order() {
    }

    public Order(User user) {
        products = new ArrayList<>();
        this.userId = user.getId();
    }

    public Order(List<Product> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    public Order(Order order) {
        id = order.getId();
        products = order.products.stream()
                .map(Product::new)
                .collect(Collectors.toList());
        userId = order.userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
                + ", userId=" + userId + '}';
    }
}
