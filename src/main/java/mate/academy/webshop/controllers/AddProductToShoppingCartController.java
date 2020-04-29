package mate.academy.webshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.service.ProductService;
import mate.academy.webshop.service.ShoppingCartService;

public class AddProductToShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final ProductService productService = (ProductService) INJECTOR
            .getInstance(ProductService.class);
    private final ShoppingCartService cartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));
        Product product = productService.get(productId);
        ShoppingCart cart = cartService.getByUserId(USER_ID);
        cartService.addProduct(cart, product);
        resp.sendRedirect(req.getContextPath() + "/users/cart");
    }
}
