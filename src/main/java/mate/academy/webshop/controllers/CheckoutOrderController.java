package mate.academy.webshop.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.service.OrderService;
import mate.academy.webshop.service.ShoppingCartService;

public class CheckoutOrderController extends HttpServlet {
    private static final long USER_ID = 1L;
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final OrderService orderService = (OrderService) INJECTOR
            .getInstance(OrderService.class);
    private final ShoppingCartService cartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        ShoppingCart cart = cartService.getByUserId(USER_ID);
        orderService.completeOrder(cartService.getAllProducts(cart), cart.getId());
        cartService.clear(cart);

        resp.sendRedirect("/users/orders/all");
    }
}
