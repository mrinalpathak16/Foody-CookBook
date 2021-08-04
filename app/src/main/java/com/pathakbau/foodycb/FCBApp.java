package com.pathakbau.foodycb;

import android.app.Application;

import com.pathakbau.foodycb.data.DataRepository;
import com.pathakbau.foodycb.data.FavouritesDatabase;

public class FCBApp extends Application {

    public FavouritesDatabase getDatabase(){
        return FavouritesDatabase.getInstance(this);
    }

    public DataRepository getRepository(){
        return DataRepository.getInstance(getDatabase());
    }
}
