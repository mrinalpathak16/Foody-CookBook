package com.pathakbau.foodycb.data;

import com.pathakbau.foodycb.model.MealResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMeals {

    @GET("random.php")
    Call<MealResp> getRandomMeal();

    @GET("lookup.php")
    Call<MealResp> getMealById(@Query("i") String i);

    @GET("search.php")
    Call<MealResp> searchMeals(@Query("s") String s);

}
