package com.example.foodiefinder.ViewModel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodiefinder.Database.RestaurantRepository;
import com.example.foodiefinder.Entities.Restaurant;

import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {
    private RestaurantRepository repository;
    private LiveData<List<Restaurant>> allRestaurants;

    public RestaurantViewModel(Application application) {
        super(application);
        repository = new RestaurantRepository(application);
        allRestaurants = (LiveData<List<Restaurant>>) repository.getAllRestaurants();
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        return allRestaurants;
    }

    public void insert(Restaurant restaurant) {
        repository.insert(restaurant);
    }

    public void update(Restaurant restaurant) {
        repository.update(restaurant);
    }

    public void delete(Restaurant restaurant) {
        repository.delete(restaurant);
    }

    public LiveData<Restaurant> getRestaurantById(int id) {
        return repository.getRestaurantById(id);
    }
}