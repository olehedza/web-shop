package mate.academy.webshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.webshop.dao.UserDao;
import mate.academy.webshop.lib.Dao;
import mate.academy.webshop.model.Role;
import mate.academy.webshop.model.User;
import mate.academy.webshop.util.ConnectionUtil;

@Dao
public class UserDaoJdbcImpl implements UserDao {

    @Override
    public Optional<User> findByLogin(String login) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            return getUserFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public User create(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO users"
                    + " (username, email, first_name, last_name, password, salt)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            setStatementParams(statement, user).executeUpdate();
            user.setId(statement.getGeneratedKeys().getLong("user_id"));
            createUserRoles(user);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create user", e);
        }
    }

    @Override
    public Optional<User> get(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM users "
                    + "WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            return getUserFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("User with id:%d not found", userId), e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(query);
            return getAllUsersFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all users", e);
        }
    }

    @Override
    public User update(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "UPDATE users SET username = ?, email = ?, "
                    + "first_name = ?, last_name = ?, password = ? "
                    + "WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            setStatementParams(statement, user).setLong(6, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't update user with id:%d", user.getId()), e);
        }
        deleteUserRoles(user.getId());
        createUserRoles(user);
        return user;
    }

    @Override
    public boolean delete(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM users WHERE user_id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't delete user with id:%d", userId), e);
        }
        deleteUserCartProducts(userId);
        deleteUserCart(userId);
        deleteOrderedProducts(userId);
        deleteAllUserOrders(userId);
        deleteUserRoles(userId);
        return true;
    }

    private PreparedStatement setStatementParams(PreparedStatement statement, User user)
            throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setString(5, user.getPassword());
        statement.setBytes(6, user.getSalt());
        return statement;
    }

    public Optional<User> getUserFromResultSet(ResultSet resultSet)
            throws SQLException {
        if (resultSet.next()) {
            Long userId = resultSet.getLong("user_id");
            String userName = resultSet.getString("username");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            byte[] salt = resultSet.getBytes("salt");
            Set<Role> roles = getUserRoles(userId);
            User user = new User(userId, userName, email, firstName,
                    lastName, password, roles);
            user.setSalt(salt);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public List<User> getAllUsersFromResultSet(ResultSet resultSet)
            throws SQLException {
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            users.add(getUserFromResultSet(resultSet)
                    .orElseThrow(RuntimeException::new));
        }
        return users;
    }

    public Set<Role> getUserRoles(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Set<Role> roleSet = new HashSet<>();
            String query = "SELECT roles.role_name FROM users_roles "
                    + "INNER JOIN roles "
                    + "ON roles.role_id = users_roles.role_id "
                    + "WHERE users_roles.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roleSet.add(Role.of(resultSet.getString("role_name")));
            }
            return roleSet;
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't get roles for user id:%d", userId));
        }
    }

    public void createUserRoles(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO users_roles (user_id, role_id) "
                    + "VALUES (?, ?)";
            for (Role userRole : user.getRoles()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, user.getId());
                statement.setLong(2, userRole.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't create role for user id:%d", user.getId()));
        }
    }

    public void deleteUserRoles(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM users_roles WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't delete user roles for user id:%d", userId));
        }
    }

    public void deleteUserCart(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM shopping_carts WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't delete shopping cart for user id:%d", userId));
        }
    }

    public void deleteUserCartProducts(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM shopping_carts_products "
                    + "WHERE cart_id IN (SELECT cart_id FROM shopping_carts "
                    + "WHERE user_id = ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't delete cart products for user id:%d", userId));
        }
    }

    public void deleteAllUserOrders(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM orders WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't delete orders for user id:%d", userId));
        }
    }

    public void deleteOrderedProducts(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM orders_products WHERE order_id = ? "
                    + "IN (SELECT order_id FROM orders "
                    + "WHERE user_id = ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(String
                    .format("Can't delete ordered products for user id:%d", userId));
        }
    }
}
