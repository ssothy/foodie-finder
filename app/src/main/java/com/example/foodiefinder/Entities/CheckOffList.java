package com.example.foodiefinder.Entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "checkoff_list",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId",
                        onDelete = CASCADE),
                @ForeignKey(entity = Restaurant.class,
                        parentColumns = "restaurantId",
                        childColumns = "restaurantId",
                        onDelete = CASCADE)
        })
public class CheckOffList {
    @PrimaryKey(autoGenerate = true)
    public int checkOffListId;
    public int userId;
    public int restaurantId;
    public boolean visited;
    public long addedAt;
    public long visitedAt;
}
