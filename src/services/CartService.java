package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Cart;
import models.CartItem;

public class CartService {


    public static boolean addToCart(int userId, int productId, int quantity) {
        String selectQuery = "SELECT quantity FROM cart WHERE user_id = ? AND product_id = ?";
        String insertQuery = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateQuery = "UPDATE cart SET quantity = quantity + ? WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, productId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Update existing item
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, quantity);
                    updateStmt.setInt(2, userId);
                    updateStmt.setInt(3, productId);
                    return updateStmt.executeUpdate() > 0;
                }
            } else {
                // Insert new item
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setInt(2, productId);
                    insertStmt.setInt(3, quantity);
                    return insertStmt.executeUpdate() > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean removeFromCart(int userId, int productId) {
        String query = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Cart> getCartItems(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        String query = "SELECT * FROM cart WHERE user_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cart item = new Cart();
                item.setUserId(rs.getInt("user_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                cartItems.add(item);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cartItems;
    }

    public static boolean clearCart(int userId) {
        String query = "DELETE FROM cart WHERE user_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public  List<CartItem> getCartItemsByUserId(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT c.product_id, c.quantity, p.name, p.price FROM cart c " +
                "JOIN products p ON c.product_id = p.product_id WHERE c.user_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setProductName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                cartItems.add(item);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());        }

        return cartItems;
    }
}


