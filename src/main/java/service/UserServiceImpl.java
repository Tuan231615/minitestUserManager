package service;

import model.Province;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService{
    private final String jdbcURL = "jdbc:mysql://localhost:3306/userManager?useSSL=false";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "161220";
    private static final String INSERT_USERS_SQL = "INSERT INTO users (name, email, provinceId) VALUES (?, ?, ?);";
    private static final String SELECT_ALL_USER = "select * from user";
    private final String SELECT_BY_ID_PROVINCE = "select * from usermanager.province where id = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public UserServiceImpl(){};
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER)) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int idd = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int p_id = rs.getInt("id_p");
                Province province = selectProvince(p_id);
                users.add(new User(idd, name, email, province));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    public Province selectProvince(int id) {
        Province province = null;
        Connection connection = getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_PROVINCE);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    province = new Province(id, name);
                }
                return province;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
