package com.pathakbau.foodycb.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.pathakbau.foodycb.model.Meal;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Insert
    public void insert(Meal meal);

    @Delete
    public void delete(Meal meal);

    @Query("DELETE FROM favourites")
    public void deleteAll();

    @Query("SELECT idMeal, strMeal, strMealThumb, strCategory, strArea FROM favourites")
    public LiveData<List<Meal>> getAll();

    @Query("SELECT idMeal FROM favourites WHERE idMeal = :id")
    public LiveData<String> getById(String id);
}
