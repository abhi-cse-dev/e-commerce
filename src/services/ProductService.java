package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Product;

public class ProductService {
    public void viewProducts() {
        try (Connection conn = DbConnection.getConnection()) {
            String sql = "SELECT * FROM products";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Product Catalog:");
            while (rs.next()) {
                System.out.println(rs.getInt("product_id") + ". " +
                        rs.getString("name") + " - $" + rs.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch products: " + e.getMessage());
        }
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, name, price, stock FROM products";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Handle exceptions appropriately
        }

        return products;
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static Product getProductById(int productId) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        Product product = null;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));

                //  product.setImagePath(rs.getString("image_path")); // optional
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return product;
    }
}



