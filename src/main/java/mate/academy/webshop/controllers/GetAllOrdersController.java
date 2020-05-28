package mate.academy.webshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Order;
import mate.academy.webshop.service.OrderService;
import mate.academy.webshop.service.UserService;

public class GetAllOrdersController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final OrderService orderService = (OrderService) INJECTOR
            .getInstance(OrderService.class);
    private final UserService userService = (UserService) INJECTOR
            .getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("user_id");
        List<Order> orders = orderService.getUserOrders(userService.get(userId));

        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/orders/allOrders.jsp")
                .forward(req, resp);
    }
}
