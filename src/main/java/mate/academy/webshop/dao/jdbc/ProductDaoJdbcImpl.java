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
        String getLastIdQuery = "SELECT * FROM products ORDER BY product_id DESC LIMIT 1";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
            return getProductFromResultSet(statement.executeQuery(getLastIdQuery))
                    .orElseThrow(RuntimeException::new);
        } catch (SQLException e) {
            LOGGER.error("Can't create product", e);
            throw new RuntimeException("Can't create product");
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return getProductFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error(String.format("Can't get product with id: %d!", id), e);
            return Optional.empty();
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            return getAllProductsFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error("Can't get list of products!", e);
            return new ArrayList<>();
        }
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? WHERE product_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            LOGGER.error(String.format("Can't update product with id: %d!",
                    product.getId()), e);
            throw new RuntimeException("Can't update product");
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM products WHERE product_id = ?";

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

    public Optional<Product> getProductFromResultSet(ResultSet resultSet)
            throws SQLException {
        Long productId = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Double price = resultSet.getDouble("price");
        return Optional.of(new Product(productId, name, price));
    }

    public List<Product> getAllProductsFromResultSet(ResultSet resultSet)
            throws SQLException {
        List<Product> products = new ArrayList<>();

        while (resultSet.next()) {
            products.add(getProductFromResultSet(resultSet)
                    .orElseThrow(RuntimeException::new));
        }
        return products;
    }
}
