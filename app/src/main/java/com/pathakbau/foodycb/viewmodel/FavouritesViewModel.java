package com.pathakbau.foodycb.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pathakbau.foodycb.data.DataRepository;
import com.pathakbau.foodycb.data.FavouritesDatabase;
import com.pathakbau.foodycb.model.Meal;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FavouritesViewModel extends ViewModel {
    private DataRepository repository;
    private LiveData<List<Meal>> favourites;
    private static final String TAG = "FavouritesViewModel";

    public FavouritesViewModel(DataRepository repository) {
        this.repository = repository;
        favourites = repository.getFavourites();
    }

    public LiveData<List<Meal>> getFavourites() {
        return favourites;
    }

    public void deleteAllFavourites() {
        repository.deleteAllFavourites();
    }

    public void deleteFavourite(Meal meal) {
        repository.deleteFavourite(meal);
    }

    public static class ViewModelFactory implements ViewModelProvider.Factory {
        private final DataRepository repository;

        public ViewModelFactory(DataRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @NotNull
        @Override
        public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
            return (T) new FavouritesViewModel(repository);
        }
    }
}