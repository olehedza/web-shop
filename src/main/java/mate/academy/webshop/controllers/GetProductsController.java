package mate.academy.webshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.service.ProductService;

public class GetProductsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy.webshop");
    private ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> products = productService.getAll();

        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/products/allProducts.jsp")
                .forward(req, resp);
    }
}
