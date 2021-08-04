package com.pathakbau.foodycb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResp {

    @SerializedName("meals")
    @Expose
    private List<Meal> meals = null;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

}
