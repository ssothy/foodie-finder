package com.example.foodiefinder.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodiefinder.Entities.Restaurant;

import java.util.List;

@Dao
public interface RestaurantDao {
    @Insert
    void insert(Restaurant restaurant);

    @Update
    void update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("SELECT * FROM restaurants WHERE restaurantId = :restaurantId")
    LiveData<Restaurant> getRestaurantById(int restaurantId);

    @Query("SELECT * FROM restaurants")
    LiveData<List<Restaurant>> getAllRestaurants();
}
