package com.example.foodiefinder.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodiefinder.Entities.CheckOffList;

import java.util.List;

@Dao
public interface CheckOffListDao {
    @Insert
    void insert(CheckOffList checkOffList);

    @Update
    void update(CheckOffList checkOffList);

    @Delete
    void delete(CheckOffList checkOffList);

    @Query("SELECT * FROM checkoff_list WHERE userId = :userId")
    LiveData<List<CheckOffList>> getCheckOffListByUserId(int userId);
}
