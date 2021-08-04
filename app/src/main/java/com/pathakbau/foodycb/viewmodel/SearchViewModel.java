package com.pathakbau.foodycb.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pathakbau.foodycb.data.DataRepository;
import com.pathakbau.foodycb.model.Meal;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private DataRepository repository;
    private LiveData<List<Meal>> searchResult;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> currentQuery = new MutableLiveData<>("");

    public SearchViewModel(DataRepository repository) {
        this.repository = repository;
        searchResult = repository.getSearchResult();
    }

    public LiveData<List<Meal>> getSearchResult() {
        return searchResult;
    }

    public LiveData<String> getCurrentQuery() {
        return currentQuery;
    }

    public void loadingFinished(){
        isLoading.setValue(false);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void performSearch(String query) {
        currentQuery.setValue(query);
        isLoading.setValue(true);
        repository.performMealSearch(query);
    }

    public static class ViewModelFactory implements ViewModelProvider.Factory {
        private DataRepository repository;

        public ViewModelFactory(DataRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @NotNull
        @Override
        public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
            return (T) new SearchViewModel(repository);
        }
    }
}