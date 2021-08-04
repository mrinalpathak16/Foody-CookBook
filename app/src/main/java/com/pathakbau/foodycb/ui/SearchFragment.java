package com.pathakbau.foodycb.ui;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pathakbau.foodycb.FCBApp;
import com.pathakbau.foodycb.MsgUtils;
import com.pathakbau.foodycb.R;
import com.pathakbau.foodycb.databinding.SearchFragmentBinding;
import com.pathakbau.foodycb.viewmodel.SearchViewModel;

import org.jetbrains.annotations.NotNull;

public class SearchFragment extends Fragment implements MealsRecyclerAdapter.OnMealItemClickListener {
    private static final String TAG = "SearchFragment";

    private SearchFragmentBinding binding;
    private SearchViewModel mViewModel;
    NavController navController;
    MsgUtils msgUtils;
    private MealsRecyclerAdapter adapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SearchFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this, new SearchViewModel.ViewModelFactory(
                ((FCBApp)getActivity().getApplication()).getRepository())).get(SearchViewModel.class);

        binding.searchView.setSubmitButtonEnabled(true);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length()>2){
                    mViewModel.performSearch(query);
                }
                else {
                    msgUtils.showToast("Minimum 3 characters required for search!");
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        msgUtils = new MsgUtils(getContext());

        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext()));
        adapter = new MealsRecyclerAdapter(this, "Enter a Search Query");
        binding.searchRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.homeFragment, R.id.searchFragment, R.id.favouritesFragment).build();
        Toolbar toolbar = binding.toolbar;
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        mViewModel.getSearchResult().observe(getViewLifecycleOwner(), meals -> {
            adapter.setEmptyMsg("No Results Found!");
            if (adapter.getCurrentList().size() == 0 && (meals == null || meals.size() == 0))
                adapter.notifyDataSetChanged();
            else
                adapter.submitList(meals);
            mViewModel.loadingFinished();
        });
        mViewModel.getIsLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) msgUtils.showProgressDialog();
            else msgUtils.dismissProgressDialog();
        });
        mViewModel.getCurrentQuery().observe(getViewLifecycleOwner(), s -> binding.searchView.setQuery(s, false));
    }

    @Override
    public void onMealItemClickListener(String mealId) {
        SearchFragmentDirections.ActionSearchFragmentToHomeFragment action =
                SearchFragmentDirections.actionSearchFragmentToHomeFragment(mealId);
        navController.navigate(action);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        } else {
            return AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        }
    }
}