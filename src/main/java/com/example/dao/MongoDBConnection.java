package com.example.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoDBConnection {
    public static MongoClient getConnection() {
        String uri = "mongodb://localhost:27017";
        return MongoClients.create(uri);
    }
}