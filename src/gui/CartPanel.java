package gui;

import models.CartItem;
import models.Product;
import models.User;
import models.Order;
import services.CartService;
import services.ProductService;
import services.OrderService;
import services.DbConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class CartPanel extends JPanel {
    private JLabel totalLabel;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JButton removeButton, checkoutButton, refreshButton;
    private final User currentUser;

    public CartPanel(User user) {
        this.currentUser = user;

        setLayout(new BorderLayout());

        initComponents();
        loadCartItems();
    }

    private void initComponents() {
        String[] columnNames = {"Product ID", "Name", "Price", "Quantity", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        removeButton = new JButton("Remove Selected");
        checkoutButton = new JButton("Checkout");
        refreshButton = new JButton("Refresh");

        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(totalLabel, BorderLayout.NORTH);

        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        removeButton.addActionListener(e -> removeSelectedItem());
        checkoutButton.addActionListener(e -> checkout());
        refreshButton.addActionListener(e -> loadCartItems());
    }

    private void loadCartItems() {
        tableModel.setRowCount(0);
        double totalAmount = 0.0;

        CartService cartService = new CartService();
        List<CartItem> cartItems = cartService.getCartItemsByUserId(currentUser.getUserID());

        for (CartItem item : cartItems) {
            Product product = ProductService.getProductById(item.getProductId());
            if (product != null) {
                double itemTotal = product.getPrice() * item.getQuantity();
                totalAmount += itemTotal;

                tableModel.addRow(new Object[]{
                        product.getProductId(),
                        product.getName(),
                        product.getPrice(),
                        item.getQuantity(),
                        itemTotal
                });
            }
        }

        totalLabel.setText(String.format("Total: $%.2f", totalAmount));
    }

    private void removeSelectedItem() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
            return;
        }

        int productId = (int) tableModel.getValueAt(selectedRow, 0);
        boolean removed = CartService.removeFromCart(currentUser.getUserID(), productId);

        if (removed) {
            JOptionPane.showMessageDialog(this, "Item removed from cart.");
            loadCartItems();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove item.");
        }
    }

    private void checkout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to proceed to checkout?",
                "Confirm Checkout", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DbConnection.getConnection();
                OrderService orderService = new OrderService(conn);
                String date = LocalDate.now().toString();

                Order order = new Order(currentUser.getUserID(), date, "Placed");
                orderService.saveOrder(order); // Save order in database

                boolean success = CartService.clearCart(currentUser.getUserID());

                if (success) {
                    JOptionPane.showMessageDialog(this, "Checkout complete! Order has been placed.");
                    loadCartItems();
                } else {
                    JOptionPane.showMessageDialog(this, "Checkout failed.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred during checkout.");
            }
        }
    }
}
