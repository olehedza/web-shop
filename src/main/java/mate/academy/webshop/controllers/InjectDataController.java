package mate.academy.webshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.dao.ShoppingCartDao;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.model.User;
import mate.academy.webshop.service.ShoppingCartService;
import mate.academy.webshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private ShoppingCartService cartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);
    private ShoppingCartDao cartDao = (ShoppingCartDao) INJECTOR.getInstance(ShoppingCartDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user1 = new User("olehedza", "olehedza@gmail.com",
                "Oleksii", "Lehedza", "1q2w3e");
        User user2 = new User("xdoex", "xdoesx@gmail.com",
                "Jhon", "Doe", "w3e4r5");
        userService.create(user1);
        userService.create(user2);
        ShoppingCart cart1 = new ShoppingCart(user1);
        ShoppingCart cart2 = new ShoppingCart(user2);
        cartDao.create(cart1);
        cartDao.create(cart2);

        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}
