package com.example.foodiefinder.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodiefinder.DAO.CheckOffListDao;
import com.example.foodiefinder.DAO.RestaurantDao;
import com.example.foodiefinder.DAO.UserDao;
import com.example.foodiefinder.Entities.CheckOffList;
import com.example.foodiefinder.Entities.Restaurant;
import com.example.foodiefinder.Entities.User;

@Database(entities = {User.class, Restaurant.class, CheckOffList.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RestaurantDao restaurantDao();
    public abstract UserDao userDao();
    public abstract CheckOffListDao checkOffListDao();

    public static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "foodie_finder_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
