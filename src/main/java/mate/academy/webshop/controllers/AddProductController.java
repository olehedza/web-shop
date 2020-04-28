package mate.academy.webshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.dao.ShoppingCartDao;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.model.User;
import mate.academy.webshop.service.ProductService;
import mate.academy.webshop.service.ShoppingCartService;
import mate.academy.webshop.service.UserService;

public class AddProductController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("mate.academy.webshop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);
    private final ShoppingCartService cartService = (ShoppingCartService) injector
            .getInstance(ShoppingCartService.class);
    private final ShoppingCartDao cartDao = (ShoppingCartDao) injector
            .getInstance(ShoppingCartDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/products/addProduct.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String title = req.getParameter("title");
        String price = req.getParameter("price");

        if (!title.matches("^ *$")
                && price.matches("^[0-9]+\\.?[0-9]*$")) {
            Product product = new Product(title, Double.valueOf(price));
            productService.create(product);

            User user;
            if (userService.get(USER_ID) != null) {
                user = userService.get(USER_ID);
            } else {
                user = new User();
                userService.create(user);
            }

            ShoppingCart cart;
            if (cartService.getByUserId(USER_ID) != null) {
                cart = cartService.getByUserId(USER_ID);
                cart.setUser(user);
                cartDao.create(cart);
            } else {
                cart = cartDao.create(new ShoppingCart(user));
            }
            cartService.addProduct(cart, product);
            resp.sendRedirect(req.getContextPath() + "/products/all");
        } else {
            req.setAttribute("message", "Enter valid data!");
            req.getRequestDispatcher("/WEB-INF/views/products/addProduct.jsp")
                    .forward(req, resp);
        }

    }
}
