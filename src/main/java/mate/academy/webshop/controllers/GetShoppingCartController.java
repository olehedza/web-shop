package mate.academy.webshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.service.ShoppingCartService;

public class GetShoppingCartController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("user_id");
        ShoppingCart cart = shoppingCartService.getByUserId(userId);
        req.setAttribute("products", cart.getProducts());
        req.setAttribute("cartId", cart.getId());
        req.getRequestDispatcher("/WEB-INF/views/carts/allCartProducts.jsp")
                .forward(req, resp);
    }
}
