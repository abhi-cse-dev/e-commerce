package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int userId;
    public static String username;
    private String password;


    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public int getUserID(){
        return userId;

}

    public void setUserID(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //Order history method declaration
    private List<Order> orderHistory = new ArrayList<>();

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addOrder(Order order) {
        orderHistory.add(order);
    }

}
