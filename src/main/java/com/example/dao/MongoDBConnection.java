package com.example.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;

public class MongoDBConnection {
    private static final String URI = "mongodb://localhost:27017";

    public static MongoClient getConnection() {
        return MongoClients.create(URI);
    }

    public static String checkConnectionStatus() {
        try (MongoClient mongoClient = getConnection()) {
            MongoDatabase database = mongoClient.getDatabase("my_app"); // Replace with your database name
            database.runCommand(new org.bson.Document("ping", 1)); // Ping the database
            return "MongoDB Connection: Successful";
        } catch (MongoException e) {
            return "MongoDB Connection: Failed - " + e.getMessage();
        }
    }
}