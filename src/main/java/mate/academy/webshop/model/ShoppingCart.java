package mate.academy.webshop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    public ShoppingCart(User user) {
        this.products = new ArrayList<>();
        this.userId = user.getId();
    }

    public ShoppingCart(Long cartId, Long userId) {
        this.id = cartId;
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
        return "ShoppingCart{" + "id=" + id
                + ", products=" + products
                + ", userId=" + userId + '}';
    }
}
