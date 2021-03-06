package mate.academy.webshop.controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.lib.Injector;
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
            throws IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));
        Long userId = (Long) req.getSession().getAttribute("user_id");
        cartService.deleteProduct(cartService.getByUserId(userId),
                productService.get(productId));

        resp.sendRedirect(req.getContextPath() + "/users/cart");
    }
}
