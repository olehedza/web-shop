package mate.academy.webshop;

import java.util.List;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.service.ProductService;

public class WebShopApp {
    private static final Injector injector = Injector.getInstance("mate.academy.webshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        checkDb(productService);

        List<Product> productList = productService.getAll();
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    private static void checkDb(ProductService productService) {
        Product product1 = new Product("Cucumber", 15.0);
        Product product2 = new Product("Cherry", 20.0);
        Product product3 = new Product("Milk", 35.0);
        Product product4 = new Product("Cheese", 45.0);
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
        productService.create(product4);

        productService.delete(product1.getId());

        product2.setPrice(30.0);
        productService.update(product2);
    }
}

