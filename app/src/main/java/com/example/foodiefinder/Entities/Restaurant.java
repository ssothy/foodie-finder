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


    public Restaurant(int restaurantID, String name, String address, String neighborhood, String phoneNumber, String website, String category, int rating, String comment) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.address = address;
        this.neighborhood = neighborhood;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.category = category;
        this.rating = rating;
        this.comment = comment;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
