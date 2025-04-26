package gui;

import services.ProductService;
import models.Product;

import javax.swing.*;
import java.awt.*;

public class AddProductFrame extends JFrame {
    private JTextField nameField, priceField, stockField;

    public AddProductFrame() {
        setTitle("Add New Product");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        // Top panel for title and logout button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Add Product");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(255, 69, 0)); // Orange-Red
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                // If you have a LoginFrame, you can open it here:
                // new LoginFrame().setVisible(true);
            }
        });

        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(logoutButton, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setPreferredSize(new Dimension(350, 150));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        priceField = new JTextField(15);
        formPanel.add(priceField, gbc);

        JLabel stockLabel = new JLabel("Stock Quantity:");
        stockLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(stockLabel, gbc);

        gbc.gridx = 1;
        stockField = new JTextField(15);
        formPanel.add(stockField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton addButton = new JButton("Add Product");
        addButton.setBackground(new Color(60, 179, 113)); // Green
        addButton.setForeground(Color.WHITE);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 20, 60)); // Red
        cancelButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Action Listeners
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Product name cannot be empty.");
                    return;
                }

                Product product = new Product(0, name, price, stock);
                ProductService productDAO = new ProductService();
                boolean success = productDAO.addProduct(product);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Product added successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add product.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format.");
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }
}
