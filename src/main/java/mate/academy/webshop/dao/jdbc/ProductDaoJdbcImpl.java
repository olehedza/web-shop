package mate.academy.webshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.webshop.dao.ProductDao;
import mate.academy.webshop.lib.Dao;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(ProductDaoJdbcImpl.class);

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";
        Product createdProduct = null;

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
            List<Product> list = getAll();
            createdProduct = list.get(list.size() - 1);
        } catch (SQLException e) {
            LOGGER.error("Can't create product", e);
            return null;
        }
        return createdProduct;
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE id = ?";
        Product product;

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            product = getProductFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error(String.format("Can't get product with id: %d!", id), e);
            return Optional.empty();
        }
        return Optional.of(product);
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        List<Product> products;

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            products = getAllProductsFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error("Can't get list of products!", e);
            return new ArrayList<>();
        }
        return products;
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(String.format("Can't update product with id: %d!",
                    product.getId()), e);
            return null;
        }
        return product;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM products WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(String.format("Can't delete product with id: %d!", id));
            return false;
        }
        return true;
    }

    public Product getProductFromResultSet(ResultSet resultSet)
            throws SQLException {
        Product product;

        long productId = resultSet.getLong("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        product = new Product(name, price);
        product.setId(productId);
        return product;
    }

    public List<Product> getAllProductsFromResultSet(ResultSet resultSet)
            throws SQLException {
        List<Product> products = new ArrayList<>();

        while (resultSet.next()) {
            long productId = resultSet.getLong("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            products.add(new Product(productId, name, price));
        }
        return products;
    }
}
