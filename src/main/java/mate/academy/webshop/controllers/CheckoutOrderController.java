package mate.academy.webshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.dao.ShoppingCartDao;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Order;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.service.OrderService;
import mate.academy.webshop.service.ShoppingCartService;

public class CheckoutOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final OrderService orderService = (OrderService) INJECTOR
            .getInstance(OrderService.class);
    private final ShoppingCartService cartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);
    private final ShoppingCartDao cartDao = (ShoppingCartDao) INJECTOR
            .getInstance(ShoppingCartDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long cartId = Long.parseLong(req.getParameter("cartId"));
        ShoppingCart cart = cartDao.get(cartId).get();
        List<Product> products = cartService.getAllProducts(cart);
        Order newOrder = orderService.completeOrder(products, cart.getUser());
        cartService.clear(cart);

        resp.sendRedirect("/users/orders/all");
    }
}
