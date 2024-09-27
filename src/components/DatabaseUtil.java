package components;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    static ConfigLoader config = new ConfigLoader();
    private static String url = config.getProperty("url");
    private static String userName = config.getProperty("userName");
    private static String passString = config.getProperty("passString");

    public static Connection getConnection() {
        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");
            // loading the driver is not required in java 6 and above
            Connection connect = DriverManager.getConnection(url, userName, passString);
            return connect;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void executeQuery(String queryString) {
        try (
                Connection connect = getConnection();
                Statement st = connect.createStatement();
                ResultSet res = st.executeQuery(queryString);) {

            while (res.next()) {// if there are rows avaialble res.next() will give a true else false
                String table_data = res.getInt(1) + ": " + res.getString(2) + " | " + res.getInt(3);
                System.out.println(table_data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // connection, statement and resultset object needs to be closed explicitly pre
        // java 7.
        // With intoduction of try with resources these are auto closable as block
        // finishes
        // finally {
        // try {
        // if (res != null) resultSet.close();
        // if (statement != null) statement.close();
        // if (connection != null) connection.close();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
    }

    public static void updateQuery(String queryString, String data_one, int data_two) {
        try (
                Connection connect = getConnection();
                PreparedStatement st = connect.prepareStatement(queryString);) {

            st.setString(1, data_one); // adding query value for col1
            st.setInt(2, data_two); // adding query value for col2
            int res = st.executeUpdate();
            System.out.println("Insert result:" + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
