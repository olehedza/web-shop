package mate.academy.webshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.service.ProductService;

public class AddProductToListController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final ProductService productService = (ProductService) INJECTOR
            .getInstance(ProductService.class);

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
            resp.sendRedirect(req.getContextPath() + "/products/all");
        } else {
            req.setAttribute("message", "Enter valid data!");
            req.getRequestDispatcher("/WEB-INF/views/products/addProduct.jsp")
                    .forward(req, resp);
        }
    }
}
