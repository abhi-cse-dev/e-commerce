package gui;

import models.Order;
import models.User;
import services.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class OrderHistoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private OrderService orderService;
    private User currentUser;

    public OrderHistoryPanel(User currentUser, Connection conn) {
        this.currentUser = currentUser;
        this.orderService = new OrderService(conn);

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Order History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Order ID", "Date", "Status"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadOrderData());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadOrderData();
    }

    private void loadOrderData() {
        tableModel.setRowCount(0);
        List<Order> orders = orderService.getOrdersByUserId(currentUser.getUserID());

        for (Order order : orders) {
            tableModel.addRow(new Object[]{
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getStatus()
            });
        }
    }
}
