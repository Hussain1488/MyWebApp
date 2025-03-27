package com.example.services;

import com.example.Entities.OrderItemEntity;
import com.example.dao.OrderItemDao;
import java.sql.SQLException;
import java.util.List;

public class OrderItemService {

    private final OrderItemDao orderItemDao;

//    Order Items Service contructore
    public OrderItemService() throws SQLException {
        this.orderItemDao = new OrderItemDao();
    }

//    Method for adding items to order
    public boolean addItemToOrder(OrderItemEntity item) throws SQLException {
        return orderItemDao.addOrderItem(item);
    }
}