package gui;

import models.User;
import services.DbConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DashboardFrame extends JFrame {
    private JPanel contentPanel;
    Connection conn = DbConnection.getConnection();
    public DashboardFrame(User currentUser) throws SQLException {
        setTitle("User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome " + User.username);
        JButton logoutButton = new JButton("Logout");
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);


        // Navigation Panel
        JPanel navPanel = new JPanel();
        JButton catalogButton = new JButton("Product Catalog");
        JButton cartButton = new JButton("Cart");
        JButton ordersButton = new JButton("Order History");
        navPanel.add(catalogButton);
        navPanel.add(cartButton);
        navPanel.add(ordersButton);


//        JButton viewProductsBtn = new JButton("Product Catalog");
//        viewProductsBtn.addActionListener(e -> {
//            ProductCatalogFrame catalogFrame = new ProductCatalogFrame();
//            catalogFrame.setVisible(true);
//        });


        // Content Panel with CardLayout
        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new ProductCatalogPanel(currentUser), "Catalog");
        contentPanel.add(new CartPanel(currentUser), "Cart");
        contentPanel.add(new OrderHistoryPanel(currentUser,conn), "Orders");

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(navPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);


//        JPanel leftPanel = new JPanel();
//        leftPanel.add(viewProductsBtn);

        // Action Listeners
        catalogButton.addActionListener(e -> switchView("Catalog"));
        cartButton.addActionListener(e -> switchView("Cart"));
        ordersButton.addActionListener(e -> switchView("Orders"));
        logoutButton.addActionListener(e -> logout());

        setVisible(true);
    }

    private void switchView(String viewName) {
        CardLayout cl = (CardLayout) (contentPanel.getLayout());
        cl.show(contentPanel, viewName);
    }

    private void logout() {
        // Implement logout functionality
        dispose();
        new LoginFrame();
    }


}
