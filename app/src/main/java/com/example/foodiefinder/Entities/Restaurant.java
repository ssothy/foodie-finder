package com.example.foodiefinder.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurants")
public class Restaurant {
    @PrimaryKey(autoGenerate = true)
    public int restaurantID;
    public String name;
    public String address;
    public String neighborhood;
    public String phoneNumber;
    public String website;
    public String category;
    public int rating;
    public String comment;
    public long createdAt;
    public long updatedAt;
}
