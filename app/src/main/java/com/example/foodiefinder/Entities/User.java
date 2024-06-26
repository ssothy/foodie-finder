package com.example.foodiefinder.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userID;
    @ColumnInfo(name = "username")
    public String username;
    public String email;
    public String password;
    public long createdAt;
    public long updatedAt;
}
