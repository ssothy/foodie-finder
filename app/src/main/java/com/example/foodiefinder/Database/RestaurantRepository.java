package com.example.foodiefinder.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foodiefinder.DAO.RestaurantDao;
import com.example.foodiefinder.Database.AppDatabase;
import com.example.foodiefinder.Entities.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RestaurantRepository {
    private final RestaurantDao restaurantDao;

    private List<Restaurant> mAllRestaurants;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public RestaurantRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        restaurantDao = db.restaurantDao();
    }

    public List<Restaurant> getAllRestaurants() {
        databaseExecutor.execute(() -> {
            mAllRestaurants = restaurantDao.getAllRestaurants();
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        return mAllRestaurants;
    }

    public void insert(Restaurant restaurant) {
        databaseExecutor.execute(()-> {
            restaurantDao.insert(restaurant);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Restaurant restaurant) {
        databaseExecutor.execute(()-> {
            restaurantDao.update(restaurant);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Restaurant restaurant) {
        databaseExecutor.execute(()-> {
            restaurantDao.delete(restaurant);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}