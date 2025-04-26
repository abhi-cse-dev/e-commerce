package gui;

import services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLoginFrame() {
        setTitle("Admin Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Admin Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(33, 33, 99));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Username Label & Field
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField();
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password Label & Field
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(51, 102, 255));
        loginBtn.setForeground(Color.white);
        loginBtn.setFocusPainted(false);
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(loginBtn, gbc);

        // Cancel Button
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(200, 50, 50));
        cancelBtn.setForeground(Color.white);
        cancelBtn.setFocusPainted(false);
        gbc.gridx = 1;
        panel.add(cancelBtn, gbc);

        add(panel);

        // Action listeners
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("admin") && password.equals("admin123")) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new AddProductFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelBtn.addActionListener(e -> dispose());
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new AdminLoginFrame().setVisible(true));
//    }
}
