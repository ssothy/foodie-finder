package com.example.foodiefinder.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foodiefinder.DAO.RestaurantDao;
import com.example.foodiefinder.Database.AppDatabase;
import com.example.foodiefinder.Entities.Restaurant;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RestaurantRepository {
    private final RestaurantDao restaurantDao;
    private final LiveData<List<Restaurant>> allRestaurants;
    private final ExecutorService executorService;

    public RestaurantRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        restaurantDao = db.restaurantDao();
        allRestaurants = restaurantDao.getAllRestaurants();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        return allRestaurants;
    }

    public void insert(Restaurant restaurant) {
        executorService.execute(() -> restaurantDao.insert(restaurant));
    }

    public void update(Restaurant restaurant) {
        executorService.execute(() -> restaurantDao.update(restaurant));
    }

    public void delete(Restaurant restaurant) {
        executorService.execute(() -> restaurantDao.delete(restaurant));
    }

    public LiveData<Restaurant> getRestaurantById(int id) {
        return restaurantDao.getRestaurantById(id);
    }
}