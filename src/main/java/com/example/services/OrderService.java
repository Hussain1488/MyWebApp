package com.example.services;

import com.example.Entities.OrderEntity;
import com.example.dao.OrderDao;
import com.example.dao.TransactionDao;
import java.sql.SQLException;
import java.util.List;

public class OrderService {

    private final OrderDao orderDao;
    private final TransactionDao transactionDao;

    public OrderService() throws SQLException {
        this.orderDao = new OrderDao();
        this.transactionDao = new TransactionDao();
    }

    public boolean createOrder(OrderEntity order) throws SQLException {
        // Check if the user has any unpaid orders
        if (hasUnpaidOrders(order.getUserId())) {
            System.out.println("You have an unpaid order. Please pay for it before creating a new order.");
            return false;
        }
        return orderDao.createOrder(order);
    }

    public boolean updateOrder(OrderEntity order) throws SQLException {
        // Only allow updates if the order is not delivered
        if (order.getStatus().equals("DELIVERED")) {
            System.out.println("Cannot update a delivered order.");
            return false;
        }
        return orderDao.updateOrder(order);
    }

    public boolean deleteOrder(int orderId) throws SQLException {
        // Only allow deletion if the order is not delivered
        OrderEntity order = orderDao.findOrderById(orderId);
        if (order != null && order.getStatus().equals("DELIVERED")) {
            System.out.println("Cannot delete a delivered order.");
            return false;
        }
        return orderDao.deleteOrder(orderId);
    }

    public boolean hasUnpaidOrders(int userId) throws SQLException {
        List<OrderEntity> orders = orderDao.getOrdersByUserId(userId);
        for (OrderEntity order : orders) {
            double totalPaid = transactionDao.getTotalPaidAmount(order.getOrderId());
            if (totalPaid < order.getTotalAmount()) {
                return true; // Unpaid order found
            }
        }
        return false; // No unpaid orders
    }

    public List<OrderEntity> getOrdersByUserId(int userId) throws SQLException {
        return orderDao.getOrdersByUserId(userId);
    }

    public OrderEntity findOrderById(int orderId) throws SQLException {
        return orderDao.findOrderById(orderId);
    }
}