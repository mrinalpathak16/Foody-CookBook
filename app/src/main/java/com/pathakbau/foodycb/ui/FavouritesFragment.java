package com.pathakbau.foodycb.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.pathakbau.foodycb.FCBApp;
import com.pathakbau.foodycb.MsgUtils;
import com.pathakbau.foodycb.R;
import com.pathakbau.foodycb.databinding.FavouritesFragmentBinding;
import com.pathakbau.foodycb.model.Meal;
import com.pathakbau.foodycb.viewmodel.FavouritesViewModel;

import org.jetbrains.annotations.NotNull;

public class FavouritesFragment extends Fragment implements MealsRecyclerAdapter.OnMealItemClickListener {

    private FavouritesFragmentBinding binding;
    private FavouritesViewModel mViewModel;
    private NavController navController;
    private MealsRecyclerAdapter adapter;
    private MsgUtils msgUtils;

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FavouritesFragmentBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this, new FavouritesViewModel.ViewModelFactory(
                ((FCBApp)getActivity().getApplication()).getRepository()))
                .get(FavouritesViewModel.class);

        adapter = new MealsRecyclerAdapter(this, "No Favourite Items!");
        binding.favouritesRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext()));
        binding.favouritesRecyclerView.setAdapter(adapter);

        msgUtils = new MsgUtils(getContext());

        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId()==R.id.deleteAll){
                msgUtils.showConfirmationDialog("Are you sure you wish to clear your favourites list?",
                        (dialog, which) -> mViewModel.deleteAllFavourites(),
                        dialog -> {});
                return true;
            }
            return false;
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Meal meal = adapter.getMealAt(viewHolder.getAdapterPosition());
                msgUtils.showConfirmationDialog("Are you sure you wish to remove " + meal.getStrMeal() + " from your favourites?",
                        (dialog, which) -> mViewModel.deleteFavourite(meal),
                        dialog -> adapter.notifyItemChanged(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(binding.favouritesRecyclerView);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.homeFragment, R.id.searchFragment, R.id.favouritesFragment).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        mViewModel.getFavourites().observe(getViewLifecycleOwner(), meals -> {
            adapter.submitList(meals);
        });
    }

    @Override
    public void onMealItemClickListener(String mealId) {
        FavouritesFragmentDirections.ActionFavouritesFragmentToHomeFragment action =
                FavouritesFragmentDirections.actionFavouritesFragmentToHomeFragment(mealId);
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