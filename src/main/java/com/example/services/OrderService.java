package com.example.services;

import com.example.Entities.OrderEntity;
import com.example.Entities.OrderItemEntity;
import com.example.Entities.TransactionEntity;
import com.example.dao.OrderDao;
import com.example.dao.OrderItemDao;
import com.example.dao.TransactionDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private final OrderDao orderDao;
    private final TransactionDao transactionDao;
    private OrderItemDao orderItem;

    public OrderService(OrderItemDao orderItem) throws SQLException {
        this.orderItem = orderItem;
        this.orderDao = new OrderDao();
        this.transactionDao = new TransactionDao();
    }

    public OrderService() throws SQLException {
        this.orderDao = new OrderDao();
        this.transactionDao = new TransactionDao();
    }


    // Check if the user has any unpaid orders
    public boolean hasUnpaidOrders(int userId) throws SQLException {
        List<OrderEntity> unpaidOrders = orderDao.getUnpaidOrders(userId);
        return !unpaidOrders.isEmpty();
    }

    // Get all orders for a specific user
    public List<OrderEntity> getOrdersByUserId(int userId) throws SQLException {
        return orderDao.getOrdersByUserId(userId);
    }

    // Get an order by its ID
    public OrderEntity getOrderById(int orderId) throws SQLException {
        return orderDao.getOrderById(orderId);
    }

    // Add a payment for an order
    public boolean addPayment(int orderId, double amount) throws SQLException {
        return transactionDao.addTransaction(new TransactionEntity(orderId, amount));
    }

    // Calculate the outstanding balance for an order
    public double getOutstandingBalance(int orderId) throws SQLException {
        double totalAmount = orderDao.getOrderById(orderId).getTotalAmount();
        double paidAmount = transactionDao.getTotalPaid(orderId);
        System.out.println("Total amount" + totalAmount + ". Paid amount " + paidAmount);
        return totalAmount - paidAmount;
    }

    // Create a new order
    public boolean createOrder(OrderEntity order) throws SQLException {
        return orderDao.createOrder(order);
    }

    // Update an existing order
    public boolean updateOrder(OrderEntity order) throws SQLException {
        return orderDao.updateOrder(order);
    }

    // Delete an order
    public boolean deleteOrder(int orderId) throws SQLException {
        return orderDao.deleteOrder(orderId);
    }
//    Method for getting all orders.
    public void getAllOrders(Scanner sc) throws SQLException {
        int limit = 5;
        int offset = 0;
        boolean exit = false;
        while (!exit) {
            List<OrderEntity> orders = orderDao.getAllOrders(limit, offset);
            for (OrderEntity order : orders) {
                System.out.println("ID: "+order.getOrderId()+" | Amount: "+order.getTotalAmount()+ " | Paid Amount: "+order.getPaidAmount()+" | Status: "+order.getStatus()+" ");
            }
            System.out.println("(n) --> for next page\n(p) --> for previous page\n (e) --> for exit page)");
            String page = sc.next();
            if ("n".equals(page)) {
                offset++;
            }else if ("p".equals(page)) {
                offset--;
            }else if ("e".equals(page)) {
                exit = true;
            }else{
                System.out.println("Invalid page");
            }
        }
        return;
    }

//    Getting filtered orders
    public void getFilteredOrders(String filter, Scanner sc) throws SQLException {
        int limit = 5;
        int offset = 0;
        boolean exit = false;
        while (!exit) {
            List<OrderEntity> orders = orderDao.getFilteredOrders(limit, offset, filter);
            for (OrderEntity order : orders) {
                System.out.println("ID: "+order.getOrderId()+" | Amount: "+order.getTotalAmount()+ " | Paid Amount: "+order.getPaidAmount()+" | Status: "+order.getStatus()+" ");
            }
            System.out.println("(n) --> for next page\n(p) --> for previous page\n (e) --> for exit page)");
            String page = sc.next();
            if ("n".equals(page)) {
                offset++;
            }else if ("p".equals(page)) {
                offset--;
            }else if ("e".equals(page)) {
                exit = true;
            }else{
                System.out.println("Invalid page");
            }
        }
        return;
    }

}