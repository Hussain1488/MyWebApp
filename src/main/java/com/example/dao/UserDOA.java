package com.example.dao;

import com.example.Entities.UserEntity;
import com.example.base.User;

import javax.lang.model.type.NullType;
import java.sql.SQLException;

public interface UserDOA {

    boolean createUser(UserEntity user) throws SQLException;

    UserEntity getUserById(int id) throws SQLException;

    UserEntity getUserByEmail(String email) throws SQLException;

    UserEntity getUserByUsername(String username) throws SQLException;

    UserEntity updateUser(UserEntity user) throws SQLException;

    boolean deleteUser(UserEntity user) throws SQLException;

    UserEntity getUserByEmailAndPassword(String email, String password) throws SQLException;



}
