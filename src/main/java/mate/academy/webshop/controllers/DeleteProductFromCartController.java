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

public class DeleteProductFromCartController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final ShoppingCartService cartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);
    private final ProductService productService = (ProductService) INJECTOR
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));
        Long userId = (Long) req.getSession().getAttribute("user_id");
        ShoppingCart cart = cartService.getByUserId(userId);
        Product productToRemove = productService.get(productId);
        cartService.deleteProduct(cart, productToRemove);

        resp.sendRedirect(req.getContextPath() + "/users/cart");
    }
}
