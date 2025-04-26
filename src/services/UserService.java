package services;

import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {


        public boolean registerUser(String username, String password) {
            try (Connection conn = DbConnection.getConnection()) {
                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("Registration failed: " + e.getMessage());
                return false;
            }
        }

//        public boolean loginUser(String username, String password) {
//            try (Connection conn = DbConnection.getConnection()) {
//                String sql = "SELECT user_id FROM users WHERE username = ? AND password = ?";
//                PreparedStatement stmt = conn.prepareStatement(sql);
//                stmt.setString(1, username);
//                stmt.setString(2, password);
//                ResultSet rs = stmt.executeQuery();
//               return rs.next();
//            } catch (SQLException e) {
//                System.out.println("Login failed: " + e.getMessage());
//            }
//            return false;
//
//        }



    public static User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password")); // Optional
                return user;
            }

        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
        }
        return null;
    }
}


