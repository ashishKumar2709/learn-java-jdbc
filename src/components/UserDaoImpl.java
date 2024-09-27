package components;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    static ConfigLoader config = new ConfigLoader();
    private static String url = config.getProperty("url");
    private static String userName = config.getProperty("userName");
    private static String passString = config.getProperty("passString");

    public static Connection getConnection() {
        try {
            Connection connect = DriverManager.getConnection(url, userName, passString);
            return connect;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserById(int user_id) {
        String query = "SELECT * FROM users WHERE user_id=?";
        try (
                Connection connect = getConnection();
                PreparedStatement pst = connect.prepareStatement(query);) {
            pst.setInt(1, user_id);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return new User(res.getInt(1), res.getString(2), res.getString(3), res.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        try (
                Connection connect = getConnection();
                PreparedStatement st = connect.prepareStatement(query);
                ResultSet res = st.executeQuery();) {
            List<User> userList = new ArrayList<>();
            while (res.next()) {

                userList.add(new User(res.getInt(1), res.getString(2), res.getString(3), res.getString(4)));
            }
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        String query = "INSERT INTO users(first_name, last_name, email) VALUES(?, ?, ?)";
        try (
                Connection connect = getConnection();
                PreparedStatement st = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getEmail());
            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet genKey = st.getGeneratedKeys()) {
                    if (genKey.next()) {
                        user.setUserId(genKey.getInt(1));
                        System.out.println(
                                "User Id: " + user.getUserId() + ", " + user.getUserName() + " inserted in db.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        String query = "UPDATE users SET first_name=?, last_name=?, email=? WHERE user_id=?";
        try (
                Connection connect = getConnection();
                PreparedStatement pst = connect.prepareStatement(query);) {
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setString(3, user.getEmail());
            pst.setInt(4, user.getUserId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(
                        "User Id: " + user.getUserId() + ", " + user.getUserName() + " updated in db.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {
        String query = "DELETE FROM users WHERE user_id=?";
        try (
                Connection connect = getConnection();
                PreparedStatement pst = connect.prepareStatement(query);) {
            pst.setInt(1, user.getUserId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(
                        "User Id: " + user.getUserId() + ", " + user.getUserName() + " deleted from db.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
