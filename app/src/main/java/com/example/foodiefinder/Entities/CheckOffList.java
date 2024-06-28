package com.example.foodiefinder.Entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "checkoff_list",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "userID",
                        childColumns = "userID",
                        onDelete = CASCADE),
                @ForeignKey(entity = Restaurant.class,
                        parentColumns = "restaurantID",
                        childColumns = "restaurantID",
                        onDelete = CASCADE)
        })
public class CheckOffList {
    @PrimaryKey(autoGenerate = true)
    public int checkOffListID;
    public int userID;
    public int restaurantID;
    public boolean visited;
    public long addedAt;
    public long visitedAt;
}
