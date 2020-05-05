package mate.academy.webshop.controllers;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.dao.ShoppingCartDao;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Role;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.model.User;
import mate.academy.webshop.service.ShoppingCartService;
import mate.academy.webshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final ShoppingCartService cartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);
    private final ShoppingCartDao cartDao = (ShoppingCartDao) INJECTOR
            .getInstance(ShoppingCartDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user1 = new User("mockdude", "dude@gmail.com",
                "Jhon", "Doe", "1234");
        user1.setRoles(Set.of(Role.of("USER")));
        User admin = new User("admin", "admin@gmail.com",
                "Admin", "Admin", "1234");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(user1);
        userService.create(admin);
        ShoppingCart cart1 = new ShoppingCart(user1);
        cartDao.create(cart1);

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
