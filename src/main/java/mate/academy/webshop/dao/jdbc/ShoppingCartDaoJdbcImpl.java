package mate.academy.webshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.webshop.dao.ShoppingCartDao;
import mate.academy.webshop.lib.Dao;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.util.ConnectionUtil;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO shopping_carts (user_id) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, cart.getUserId());
            statement.executeUpdate();
            cart.setId(statement.getGeneratedKeys().getLong(1));
            putCartProducts(cart);
            return cart;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create shopping cart", e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts WHERE cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart cart = getCartFromResultSet(resultSet);
                cart.setProducts(getCartProducts(id));
                return Optional.of(cart);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Can't get cart id:%d", id), e);
        }
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart cart = getCartFromResultSet(resultSet);
                cart.setProducts(getCartProducts(cart.getId()));
                return Optional.of(cart);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Can't get cart by user id:%d", userId), e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            List<ShoppingCart> cartList = new ArrayList<>();
            String query = "SELECT * FROM shopping_carts";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart cart = getCartFromResultSet(resultSet);
                cart.setProducts(getCartProducts(cart.getId()));
                cartList.add(cart);
            }
            return cartList;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get carts", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "UPDATE shopping_carts "
                    + "SET user_id = ?"
                    + " WHERE cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cart.getUserId());
            statement.setLong(2, cart.getId());
            statement.executeUpdate();
            deleteCart(cart.getId());
            putCartProducts(cart);
            return cart;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update cart", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        deleteCart(id);
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM shopping_carts WHERE cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Can't delete cart with id:%d", id), e);
        }
    }

    public void deleteCart(Long cartId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM shopping_carts_products "
                    + "WHERE cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete cart", e);
        }
    }

    public List<Product> getCartProducts(Long cartId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT products.* FROM shopping_carts_products "
                    + "JOIN products USING (product_id) WHERE cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            return getProducts(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get cart products", e);
        }
    }

    public List<Product> getProducts(ResultSet resultSet)
            throws SQLException {
        List<Product> productList = new ArrayList<>();

        while (resultSet.next()) {
            Long productId = resultSet.getLong("product_id");
            String name = resultSet.getString("name");
            Double price = resultSet.getDouble("price");
            productList.add(new Product(productId, name, price));
        }
        return productList;
    }

    public ShoppingCart getCartFromResultSet(ResultSet resultSet)
            throws SQLException {
        Long cartId = resultSet.getLong("cart_id");
        Long userId = resultSet.getLong("user_id");
        return new ShoppingCart(cartId, userId);
    }

    public void putCartProducts(ShoppingCart cart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO shopping_carts_products "
                    + "(cart_id, product_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : cart.getProducts()) {
                statement.setLong(1, cart.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't put products to cart with id:%d", cart.getId()), e);
        }
    }
}
