package services;

import models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private Connection conn;

    public OrderService(Connection conn) {
        this.conn = conn;
    }

    public void saveOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, order_date, status) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getUserId());
            stmt.setString(2, order.getOrderDate());
            stmt.setString(3, order.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getString("order_date"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
