package gui;

import models.Product;
import models.User;
import services.ProductService;
import services.CartService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductCatalogPanel extends JPanel {

    private JTable productTable;
    private DefaultTableModel tableModel;
    private User currentUser;

    public ProductCatalogPanel(User user) {
        this.currentUser = user;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Product Catalog", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Product ID", "Name", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        JButton addToCartButton = new JButton("Add Selected to Cart");
        add(addToCartButton, BorderLayout.SOUTH);

        addToCartButton.addActionListener(e -> addToCart());

        loadProducts();
    }

    private void loadProducts() {
        tableModel.setRowCount(0);
        List<Product> products = ProductService.getAllProducts();

        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getProductId(),
                    product.getName(),
                    product.getPrice()
            });
        }
    }

    private void addToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product.");
            return;
        }

        int productId = (int) tableModel.getValueAt(selectedRow, 0);
        boolean added = CartService.addToCart(currentUser.getUserID(), productId, 1); // quantity: 1

        if (added) {
            JOptionPane.showMessageDialog(this, "Product added to cart.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add product to cart.");
        }
    }
}













//package gui;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.util.List;
//import services.ProductService;
//import models.Product;
//
//
//
//public class ProductCatalogFrame extends JPanel {
//    private JTable productTable;
//    private DefaultTableModel tableModel;
//
//    public ProductCatalogFrame() {
//        setLayout(new BorderLayout());
//
//        // Table columns
//        String[] columns = {"Product Id", "Name", "Price", "Stock"};
//        tableModel = new DefaultTableModel(columns, 0);
//        productTable = new JTable(tableModel);
//        JScrollPane scrollPane = new JScrollPane(productTable);
//
//        // Load products
//        loadProducts();
//
//        // Add components
//        add(scrollPane, BorderLayout.CENTER);
//    }
//
//    private void loadProducts() {
//        ProductService productDAO = new ProductService();
//        List<Product> products = productDAO.getAllProducts();
//
//        for (Product product : products) {
//            Object[] row = {
//                    product.getProductId(),
//                    product.getName(),
//                    product.getPrice(),
//                    product.getStock()
//            };
//            tableModel.addRow(row);
//        }
//    }







