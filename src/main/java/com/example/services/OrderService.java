package com.example.services;

import com.example.Entities.OrderEntity;
import com.example.Entities.OrderItemEntity;
import com.example.dao.OrderDao;
import com.example.dao.OrderItemDao;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;

    public OrderService() throws SQLException {
        this.orderDao = new OrderDao();
        this.orderItemDao = new OrderItemDao();
    }

    public int createNewOrder(int userId) throws SQLException {
        OrderEntity existingOrder = orderDao.findUnpaidOrderByUserId(userId);
        if (existingOrder != null) {
            return existingOrder.getOrderId();
        }

        OrderEntity newOrder = new OrderEntity();
        newOrder.setUserId(userId);
        newOrder.setStatus("PENDING");
        newOrder.setTotalAmount(0);
        newOrder.setPaidAmount(0);
        return orderDao.createOrder(newOrder);
    }

    public boolean addProductToOrder(int orderId, int productId, int quantity, double price) throws SQLException {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrderId(orderId);
        orderItem.setProductId(productId);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(price);
        return orderItemDao.addOrderItem(orderItem);
    }

    public boolean updateOrderItem(int orderItemId, int quantity, double price) throws SQLException {
        OrderItemEntity item = new OrderItemEntity();
        item.setOrderItemId(orderItemId);
        item.setQuantity(quantity);
        item.setPrice(price);
        return orderItemDao.updateOrderItem(item);
    }

    public boolean deleteOrder(int orderId) throws SQLException {
        OrderEntity order = orderDao.findOrderById(orderId);
        if (order == null || order.getStatus().equals("DELIVERED")) {
            return false;
        }
        return orderDao.deleteOrder(orderId);
    }

    public boolean payOrder(int orderId, double paymentAmount) throws SQLException {
        OrderEntity order = orderDao.findOrderById(orderId);
        if (order == null || order.getStatus().equals("DELIVERED")) {
            return false;
        }

        double newPaidAmount = order.getPaidAmount() + paymentAmount;
        if (newPaidAmount >= order.getTotalAmount()) {
            order.setStatus("PAID");
        }
        order.setPaidAmount(newPaidAmount);
        return orderDao.updateOrder(order);
    }
}
