package mate.academy.webshop.model;

import java.util.List;

public class Order {
    private Long id;
    private List<Product> products;
    private Long userId;

    public Order() {
    }

    public Order(List<Product> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    public Order(Long orderId, List<Product> products, Long userId) {
        this.id = orderId;
        this.products = products;
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Order{" + "id=" + id
                + ", products=" + products
                + ", userId=" + userId + '}';
    }
}
