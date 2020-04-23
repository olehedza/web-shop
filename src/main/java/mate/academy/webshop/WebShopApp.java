package mate.academy.webshop;

import java.util.Arrays;
import java.util.List;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.User;
import mate.academy.webshop.service.OrderService;
import mate.academy.webshop.service.ProductService;
import mate.academy.webshop.service.ShoppingCartService;
import mate.academy.webshop.service.UserService;

public class WebShopApp {
    private static final Injector injector = Injector.getInstance("mate.academy.webshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        UserService userService = (UserService) injector.getInstance(UserService.class);
        ShoppingCartService cartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);

        checkProductDB(productService);
        checkUserDB(userService);

        productService.getAll().forEach(System.out::println);
        userService.getAll().forEach(System.out::println);
    }

    private static void checkProductDB(ProductService productService) {
        List<Product> list = generateProducts();
        productService.create(list.get(0));
        productService.create(list.get(1));
        productService.create(list.get(2));
        productService.create(list.get(3));

        productService.delete(list.get(0).getId());

        list.get(1).setPrice(30.0);
        productService.update(list.get(1));
    }

    private static void checkUserDB(UserService userService) {
        User user1 = new User("name1", "login1", "123");
        User user2 = new User("name2", "login2", "124");

        userService.create(user1);
        userService.create(user2);

        userService.delete(user1.getId());

        user2.setLogin("loginChanged");
        userService.update(user2);
    }

    private static List<Product> generateProducts() {
        Product pt1 = new Product("Cucumber", 15.0);
        Product pt2 = new Product("Cherry", 20.0);
        Product pt3 = new Product("Milk", 35.0);
        Product pt4 = new Product("Cheese", 45.0);
        return Arrays.asList(pt1, pt2, pt3, pt4);
    }
}

