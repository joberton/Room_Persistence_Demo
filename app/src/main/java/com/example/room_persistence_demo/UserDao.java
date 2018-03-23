package com.example.room_persistence_demo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao
{
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("Select * FROM user WHERE userId = :userId")
    User findUser(int userId);

    @Insert
    void insertAll(User... users);

    @Update
    void updateUser(User user);

    @Delete
    void delete(User user);
}
