package gui;

import services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SignupFrame extends JFrame {

    public SignupFrame(){
        setTitle("E-Commerce System");
        setSize(800,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,50));

        // ========== LEFT PANEL ==========
        JPanel loginPanel1 = new JPanel();
        loginPanel1.setPreferredSize(new Dimension(300,300));
        loginPanel1.setBackground(new Color(0,153,102));
        loginPanel1.setLayout(null);

        JLabel welcome = new JLabel("Welcome Back!");
        welcome.setForeground(Color.white);
        welcome.setFont(new Font("arial",Font.BOLD,20));
        welcome.setBounds(75,50,200,30);
        loginPanel1.add(welcome);

        JLabel message = new JLabel("Login with your personal Info");
        message.setForeground(Color.white);
        message.setBounds(70,90,200,30);
        loginPanel1.add(message);

        JButton loginBtn = new JButton("SIGN IN");
        loginBtn.setBounds(85,170,120,35);
        loginPanel1.add(loginBtn);


        JButton adminLoginBtn = new JButton("Admin Login");
        adminLoginBtn.setBounds(85,220,120,35); // Below SIGN IN
        adminLoginBtn.setBackground(Color.LIGHT_GRAY);
        loginPanel1.add(adminLoginBtn);

        // ========== RIGHT PANEL ==========
        JPanel signupPanel1 = new JPanel();
        signupPanel1.setPreferredSize(new Dimension(300,300));
        signupPanel1.setLayout(null);
        signupPanel1.setBackground(Color.white);

        JLabel create = new JLabel("Create your Account");
        create.setFont(new Font("Arial",Font.BOLD,20));
        create.setBounds(55,30,200,30);
        signupPanel1.add(create);

        JTextField usernameField = new JTextField("Enter Username");
        usernameField.setBounds(50,80,200,35);
        usernameField.setForeground(Color.GRAY);
        signupPanel1.add(usernameField);

        JPasswordField passwordField = new JPasswordField("Enter Password");
        passwordField.setBounds(50,130,200,35);
        passwordField.setEchoChar((char) 0);
        passwordField.setForeground(Color.GRAY);
        signupPanel1.add(passwordField);

        // Placeholder behavior
        usernameField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(usernameField.getText().equals("Enter Username")){
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if(usernameField.getText().isEmpty()){
                    usernameField.setText("Enter Username");
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });

        passwordField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(String.valueOf(passwordField.getPassword()).equals("Enter Password")){
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.black);
                }
            }

            public void focusLost(FocusEvent e) {
                if(String.valueOf(passwordField.getPassword()).isEmpty()){
                    passwordField.setText("Enter Password");
                    passwordField.setEchoChar((char)0);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        JButton signupBtn = new JButton("SIGN UP");
        signupBtn.setBounds(100,200,100,30);
        signupPanel1.add(signupBtn);

        // ========== ADD TO FRAME ==========
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,loginPanel1,signupPanel1);
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(0);
        add(splitPane);

        // ========== Action Listeners ==========
        loginBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        signupBtn.addActionListener(e ->{
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if(!username.isEmpty() && !password.isEmpty()){
                UserService service = new UserService();
                if(service.registerUser(username,password)){
                    JOptionPane.showMessageDialog(this,"User Registration successful");
                }else{
                    JOptionPane.showMessageDialog(this,"User already registered");
                }
            }else{
                JOptionPane.showMessageDialog(this,"All fields are required");
            }
        });

        adminLoginBtn.addActionListener(e -> {
            new AdminLoginFrame().setVisible(true);
        });

        mainPanel.add(loginPanel1);
        mainPanel.add(signupPanel1);
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignupFrame().setVisible(true));
    }
}
