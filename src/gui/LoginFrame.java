package gui;

import models.User;
import services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login Form");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setUndecorated(true); // To match custom style

      //  JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


        JPanel header = new JPanel();
        header.setBackground(new Color(60, 87, 155, 255));
        header.setBounds(0, 0, 400, 40);
        header.setLayout(null);

        JLabel title = new JLabel("Login Form");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        title.setBounds(10, 5, 200, 30);
        header.add(title);

        JLabel minimize = new JLabel("-");
        minimize.setFont(new Font("Arial", Font.BOLD, 18));
        minimize.setForeground(Color.WHITE);
        minimize.setBounds(340, 5, 20, 30);
        minimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minimize.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setState(JFrame.ICONIFIED);
            }
        });
        header.add(minimize);

        JLabel close = new JLabel("X");
        close.setFont(new Font("Arial", Font.BOLD, 18));
        close.setForeground(Color.WHITE);
        close.setBounds(365, 5, 20, 30);
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new SignupFrame().setVisible(true);
            }
        });
        header.add(close);

       add(header);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.white);
        userLabel.setBounds(50, 60, 100, 30);
       add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 60, 200, 30);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.white);
        passLabel.setBounds(50, 100, 100, 30);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 30);
        add(passwordField);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(255, 51, 51));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBounds(80, 150, 100, 30);
        cancelButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });
       add(cancelButton);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(200, 150, 100, 30);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());



            User user = UserService.loginUser(username, password);
            if (user != null) {
                loginButton.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        DashboardFrame dashboard = null;
                        try {
                            dashboard = new DashboardFrame(user);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        dashboard.setVisible(true);
                    }
                });

//                JOptionPane.showMessageDialog(this, "Login successful!");
//                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
      add(loginButton);

        JLabel registerLabel = new JLabel("<HTML><U>click here to create a new account</U></HTML>");
        registerLabel.setForeground(Color.WHITE);
        registerLabel.setBounds(100, 190, 250, 30);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new SignupFrame().setVisible(true);
                dispose();
                //JOptionPane.showMessageDialog(null, "Redirecting to registration...");
            }
        });
        add(registerLabel);

        getContentPane().setBackground(new Color(31, 31, 31, 255));
        setVisible(true);
    }



}

