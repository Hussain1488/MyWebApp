package com.example.services;

import com.example.Entities.TransactionEntity;
import com.example.dao.TransactionDao;
import java.sql.SQLException;

public class TransactionService {

    private final TransactionDao transactionDao;

    public TransactionService() throws SQLException {
        this.transactionDao = new TransactionDao();
    }

    public boolean addTransaction(TransactionEntity transaction) throws SQLException {
        return transactionDao.addTransaction(transaction);
    }

    public double getTotalPaidAmount(int orderId) throws SQLException {
        return transactionDao.getTotalPaidAmount(orderId);
    }
}