package com.pathakbau.foodycb.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pathakbau.foodycb.model.Meal;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class FavouritesDatabase extends RoomDatabase {
    public static FavouritesDatabase instance;

    public abstract FavouriteDao getDao();

    public static synchronized FavouritesDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavouritesDatabase.class, "my_room_databse")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
