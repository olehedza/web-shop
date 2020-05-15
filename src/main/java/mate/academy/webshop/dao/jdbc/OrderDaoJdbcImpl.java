package mate.academy.webshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.webshop.dao.OrderDao;
import mate.academy.webshop.lib.Dao;
import mate.academy.webshop.model.Order;
import mate.academy.webshop.model.Product;
import mate.academy.webshop.model.User;
import mate.academy.webshop.util.ConnectionUtil;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            order.setId(resultSet.getLong("order_id"));
            putOrderProducts(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create new order", e);
        }
    }

    @Override
    public Optional<Order> get(Long orderId) {
        String query = "SELECT * FROM orders WHERE order_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            return getOrderFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't get order with id:%d", orderId), e);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            return getAllOrdersFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("Can't get list of orders", e);
        }
    }

    @Override
    public Order update(Order order) {
        String query = "UPDATE orders SET user_id = ? "
                + "WHERE order_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
            deleteOrderProducts(order.getId());
            putOrderProducts(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't update for order id:%d", order.getId()), e);
        }
    }

    @Override
    public boolean delete(Long orderId) {
        String query = "DELETE FROM orders WHERE order_id = ?";

        deleteOrderProducts(orderId);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't delete order with id:%d", orderId), e);
        }
    }

    @Override
    public List<Order> getUserOrders(User user) {
        String query = "SELECT * FROM orders WHERE user_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getId());
            return getAllOrdersFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't get list of orders for user id:%d", user.getId()), e);
        }
    }

    public void putOrderProducts(Order order) {
        String query = "INSERT INTO orders_products "
                + "(order_id, product_id) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Product product : order.getProducts()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, order.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't put products for order id:%d", order.getId()), e);
        }
    }

    public Optional<Order> getOrderFromResultSet(ResultSet resultSet)
            throws SQLException {
        if (resultSet.next()) {
            Long userId = resultSet.getLong("user_id");
            Long orderId = resultSet.getLong("order_id");
            return Optional.of(new Order(orderId, getOrderProducts(orderId), userId));
        }
        return Optional.empty();
    }

    public List<Order> getAllOrdersFromResultSet(ResultSet resultSet)
            throws SQLException {
        List<Order> orderList = new ArrayList<>();

        while (resultSet.next()) {
            orderList.add(getOrderFromResultSet(resultSet)
                    .orElseThrow(RuntimeException::new));
        }
        return orderList;
    }

    public List<Product> getOrderProducts(Long orderId) {
        String query = "SELECT p.product_id, p.name, p.price "
                + "FROM orders_products AS op JOIN products AS p "
                + "ON op.product_id = p.product_id "
                + "WHERE op.order_id = ?";
        List<Product> productList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Product product = new Product(id, name, price);
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't get product list for order id:%d", orderId), e);
        }
    }

    private void deleteOrderProducts(Long orderId) {
        String query = "DELETE FROM orders_products WHERE order_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't remove products for order id:%d", orderId), e);
        }
    }
}
