package com.pathakbau.foodycb.data;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pathakbau.foodycb.model.Meal;
import com.pathakbau.foodycb.model.MealResp;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private static final String TAG = "DataRepository";

    private GetMeals getMeals;
    private MutableLiveData<Meal> currentMeal = new MutableLiveData<>();
    private MutableLiveData<List<Meal>> searchResult = new MutableLiveData<>();
    private static DataRepository sInstance;
    private FavouriteDao favouriteDao;

    public DataRepository(FavouriteDao favouriteDao) {
        this.favouriteDao = favouriteDao;
        getMeals = RetrofitClient.getInstance().create(GetMeals.class);
    }

    public static DataRepository getInstance(FavouritesDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database.getDao());
                }
            }
        }
        return sInstance;
    }

    public LiveData<Meal> getCurrentMeal() { return currentMeal; }

    public LiveData<List<Meal>> getSearchResult() {
        return searchResult;
    }

    public void performMealSearch(String s){
        Call<MealResp> call = getMeals.searchMeals(s);
        call.enqueue(new Callback<MealResp>() {
            @Override
            public void onResponse(Call<MealResp> call, Response<MealResp> response) {
                if (!response.isSuccessful()) {
                    String err = "";
                    try {
                        err = response.errorBody().string();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "onResponse: error!" + err);
                    //TODO: show error
                    return;
                }
                MealResp resp = response.body();
                if (resp != null) {
                    searchResult.postValue(resp.getMeals());
                }
                else {
                    Log.e(TAG, "onResponse: null!");
                    //TODO: show error
                }
            }

            @Override
            public void onFailure(Call<MealResp> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                //TODO: show error
            }
        });
    }

    public void loadMealFromNetwork(@Nullable String mealId){
        Call<MealResp> call;
        Log.d(TAG, "loadMealFromNetwork: mealId" + mealId);
        if (mealId==null) {
            Log.d(TAG, "loadMealFromNetwork: random");
            call = getMeals.getRandomMeal();
        }
        else {
            Log.d(TAG, "loadMealFromNetwork: unrandom");
            call = getMeals.getMealById(mealId);
        }
        call.enqueue(new Callback<MealResp>() {
            @Override
            public void onResponse(Call<MealResp> call, Response<MealResp> response) {
                if(!response.isSuccessful()){
                    String err = "";
                    try {
                        err = response.errorBody().string();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "onResponse: error!" + err);
                    //TODO: show error
                    return;
                }
                MealResp resp = response.body();
                if (resp!=null && resp.getMeals()!=null && resp.getMeals().size()>0){
                    Meal meal = resp.getMeals().get(0);
                    currentMeal.postValue(meal);
                }
                else {
                    Log.e(TAG, "onResponse: null!");
                    //TODO: show error
                }
            }

            @Override
            public void onFailure(Call<MealResp> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                //TODO: show error
            }
        });
    }

    public void addToFavourites(Meal meal){
        Executors.newSingleThreadExecutor().execute(() -> favouriteDao.insert(meal));
    }

    public LiveData<List<Meal>> getFavourites(){
        return favouriteDao.getAll();
    }

    public void deleteAllFavourites() {
        Executors.newSingleThreadExecutor().execute(() -> favouriteDao.deleteAll());
    }

    public void deleteFavourite(Meal meal) {
        Executors.newSingleThreadExecutor().execute(() -> favouriteDao.delete(meal));
    }

    public LiveData<String> getFavourite(String idMeal) {
        return favouriteDao.getById(idMeal);
    }
}
