package com.example.services;

import com.example.Entities.OrderItemEntity;
import com.example.dao.OrderItemDao;
import java.sql.SQLException;
import java.util.List;

public class OrderItemService {

    private final OrderItemDao orderItemDao;

    public OrderItemService() throws SQLException {
        this.orderItemDao = new OrderItemDao();
    }

    public boolean addOrderItem(OrderItemEntity orderItem) throws SQLException {
        return orderItemDao.addOrderItem(orderItem);
    }

    public List<OrderItemEntity> getOrderItemsByOrderId(int orderId) throws SQLException {
        return orderItemDao.getOrderItemsByOrderId(orderId);
    }

    public boolean addItemToOrder(OrderItemEntity item) throws SQLException {
        return orderItemDao.addOrderItem(item);
    }
}