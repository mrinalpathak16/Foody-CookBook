package com.pathakbau.foodycb.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pathakbau.foodycb.data.DataRepository;
import com.pathakbau.foodycb.model.Meal;

import org.jetbrains.annotations.NotNull;

public class HomeViewModel extends ViewModel {
    private DataRepository repository;
    private LiveData<Meal> currentMeal;
    private LiveData<String> isFavourite;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private static final String TAG = "HomeViewModel";

    public HomeViewModel(DataRepository repository, @Nullable String mealId) {
        this.repository = repository;
        if (currentMeal == null) {
            currentMeal = repository.getCurrentMeal();
            loadMeal(mealId);
        }
        Log.d(TAG, "HomeViewModel: called");
    }

    public LiveData<Meal> getCurrentMeal() {
        return currentMeal;
    }

    public void addToFavourites() {
        if (currentMeal != null && currentMeal.getValue() != null){
            repository.addToFavourites(currentMeal.getValue());
        }
    }

    public void loadingStart() {
        isLoading.setValue(true);
    }

    public void loadingFinished() {
        isLoading.setValue(false);
    }

    public void loadMeal(String mealId) {
        loadingStart();
        repository.loadMealFromNetwork(mealId);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getFavourite(String idMeal) {
        if (idMeal == null) {
            return isFavourite;
        }
        isFavourite = repository.getFavourite(idMeal);
        return isFavourite;
    }

    public void removeFromFavourites() {
        if (currentMeal.getValue()!=null)
            repository.deleteFavourite(currentMeal.getValue());
    }

    public static class ViewModelFactory implements ViewModelProvider.Factory {
        private final DataRepository repository;
        private final String mealId;

        public ViewModelFactory(DataRepository repository, String mealId) {
            this.repository = repository;
            this.mealId = mealId;
        }

        @NonNull
        @NotNull
        @Override
        public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
            return (T) new HomeViewModel(repository, mealId);
        }
    }
}