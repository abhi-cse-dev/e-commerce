package models;

public class Order {
    private int orderId;
    private int userId;
    private String orderDate;
    private String status;

    public Order(int orderId, int userId, String orderDate, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
    }

    public Order(int userId, String orderDate, String status) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getOrderId() { return orderId; }
    public int getUserId() { return userId; }
    public String getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
}
