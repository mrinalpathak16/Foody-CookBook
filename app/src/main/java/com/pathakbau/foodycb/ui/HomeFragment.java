package com.pathakbau.foodycb.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pathakbau.foodycb.FCBApp;
import com.pathakbau.foodycb.MsgUtils;
import com.pathakbau.foodycb.R;
import com.pathakbau.foodycb.databinding.HomeFragmentBinding;
import com.pathakbau.foodycb.model.Meal;
import com.pathakbau.foodycb.viewmodel.HomeViewModel;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private MsgUtils msgUtils;
    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);

        String mealId = HomeFragmentArgs.fromBundle(getArguments()).getMealId();
        if (mealId!=null && mealId.equals("null")) mealId = null;
        mViewModel = new ViewModelProvider(this, new HomeViewModel.ViewModelFactory(
                ((FCBApp)getActivity().getApplication()).getRepository(), mealId))
                .get(HomeViewModel.class);

        binding.homeDisappearingLayout.setVisibility(View.INVISIBLE);
        binding.toolbar.getMenu().getItem(2).setVisible(false);
        binding.toolbar.setOnMenuItemClickListener(this::onToolbarMenuItemClick);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.homeFragment, R.id.searchFragment, R.id.favouritesFragment).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        msgUtils = new MsgUtils(getContext());
        mViewModel.getCurrentMeal().observe(getViewLifecycleOwner(), this::onCurrentMealChanged);
        mViewModel.getIsLoading().observe(getViewLifecycleOwner(), this::onLoadingStatusChanged);
    }

    private void setViews(Meal meal) {
        Glide.with(HomeFragment.this)
                .load(meal.getStrMealThumb())
                .addListener(glideRequestListener)
                .centerCrop()
                .placeholder(R.drawable.iron)
                .into(binding.img);
        binding.mealName.setText(meal.getStrMeal());
        binding.mealTags.setText(meal.getStrTags());
        binding.mealArea.setText("Area: " + meal.getStrArea());
        binding.mealCategory.setText("Category: " + meal.getStrCategory());
        if (meal.getStrDrinkAlternate()==null || meal.getStrDrinkAlternate().equals(""))
            binding.drinkAlternate.setText("Best Served With: N/A");
        else
            binding.drinkAlternate.setText("Best Served With: " + meal.getStrDrinkAlternate());
        String ingredients = meal.getStrIngredient1() + " - " + meal.getStrMeasure1();
        if (meal.getStrIngredient2()!=null && !meal.getStrIngredient2().equals(""))
            ingredients += "\n" + meal.getStrIngredient2() + " - " + meal.getStrMeasure2();
        if (meal.getStrIngredient3()!=null && !meal.getStrIngredient3().equals(""))
            ingredients += "\n" + meal.getStrIngredient3() + " - " + meal.getStrMeasure3();
        if (meal.getStrIngredient4()!=null && !meal.getStrIngredient4().equals(""))
            ingredients += "\n" + meal.getStrIngredient4() + " - " + meal.getStrMeasure4();
        if (meal.getStrIngredient5()!=null && !meal.getStrIngredient5().equals(""))
            ingredients += "\n" + meal.getStrIngredient5() + " - " + meal.getStrMeasure5();
        if (meal.getStrIngredient6()!=null && !meal.getStrIngredient6().equals(""))
            ingredients += "\n" + meal.getStrIngredient6() + " - " + meal.getStrMeasure6();
        if (meal.getStrIngredient7()!=null && !meal.getStrIngredient7().equals(""))
            ingredients += "\n" + meal.getStrIngredient7() + " - " + meal.getStrMeasure7();
        if (meal.getStrIngredient8()!=null && !meal.getStrIngredient8().equals(""))
            ingredients += "\n" + meal.getStrIngredient8() + " - " + meal.getStrMeasure8();
        if (meal.getStrIngredient9()!=null && !meal.getStrIngredient9().equals(""))
            ingredients += "\n" + meal.getStrIngredient9() + " - " + meal.getStrMeasure9();
        if (meal.getStrIngredient10()!=null && !meal.getStrIngredient10().equals(""))
            ingredients += "\n" + meal.getStrIngredient10() + " - " + meal.getStrMeasure10();
        if (meal.getStrIngredient11()!=null && !meal.getStrIngredient11().equals(""))
            ingredients += "\n" + meal.getStrIngredient11() + " - " + meal.getStrMeasure11();
        if (meal.getStrIngredient12()!=null && !meal.getStrIngredient12().equals(""))
            ingredients += "\n" + meal.getStrIngredient12() + " - " + meal.getStrMeasure12();
        if (meal.getStrIngredient13()!=null && !meal.getStrIngredient13().equals(""))
            ingredients += "\n" + meal.getStrIngredient13() + " - " + meal.getStrMeasure13();
        if (meal.getStrIngredient14()!=null && !meal.getStrIngredient14().equals(""))
            ingredients += "\n" + meal.getStrIngredient14() + " - " + meal.getStrMeasure14();
        if (meal.getStrIngredient15()!=null && !meal.getStrIngredient15().equals(""))
            ingredients += "\n" + meal.getStrIngredient15() + " - " + meal.getStrMeasure15();
        if (meal.getStrIngredient16()!=null && !meal.getStrIngredient16().equals(""))
            ingredients += "\n" + meal.getStrIngredient16() + " - " + meal.getStrMeasure16();
        if (meal.getStrIngredient17()!=null && !meal.getStrIngredient17().equals(""))
            ingredients += "\n" + meal.getStrIngredient17() + " - " + meal.getStrMeasure17();
        if (meal.getStrIngredient18()!=null && !meal.getStrIngredient18().equals(""))
            ingredients += "\n" + meal.getStrIngredient18() + " - " + meal.getStrMeasure18();
        if (meal.getStrIngredient19()!=null && !meal.getStrIngredient19().equals(""))
            ingredients += "\n" + meal.getStrIngredient19() + " - " + meal.getStrMeasure19();
        if (meal.getStrIngredient20()!=null && !meal.getStrIngredient20().equals(""))
            ingredients += "\n" + meal.getStrIngredient20() + " - " + meal.getStrMeasure20();
        binding.mealIngredients.setText(ingredients);
        binding.mealInstructions.setText(meal.getStrInstructions());
        if (meal.getStrYoutube()==null || meal.getStrYoutube().equals(""))
            binding.youtube.setEnabled(false);
        else {
            binding.youtube.setEnabled(true);
            binding.youtube.setOnClickListener(v -> {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(meal.getStrYoutube()));
                startActivity(i);
            });
        }
        if (meal.getStrSource()==null || meal.getStrSource().equals(""))
            binding.youtube.setEnabled(false);
        else {
            binding.source.setEnabled(true);
            binding.source.setOnClickListener(v -> {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(meal.getStrSource()));
                startActivity(i);
            });
        }
    }

    RequestListener<Drawable> glideRequestListener = new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            mViewModel.loadingFinished();
            return false;
        }
    };

    private boolean onToolbarMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.add_to_favourites) {
            mViewModel.addToFavourites();
            return true;
        } else if (item.getItemId() == R.id.remove_from_favourites) {
            mViewModel.removeFromFavourites();
            return true;
        } else if (item.getItemId() == R.id.new_random_meal) {
            mViewModel.getFavourite(null).removeObservers(getViewLifecycleOwner());
            mViewModel.loadMeal(null);
            binding.homeDisappearingLayout.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }

    private void onCurrentMealChanged(Meal meal) {
        mViewModel.getFavourite(meal.getIdMeal()).observe(HomeFragment.this.getViewLifecycleOwner(),
                s -> {
                    if (s == null) {
                        binding.toolbar.getMenu().getItem(2).setVisible(false);
                        binding.toolbar.getMenu().getItem(1).setVisible(true);
                    } else {
                        binding.toolbar.getMenu().getItem(2).setVisible(true);
                        binding.toolbar.getMenu().getItem(1).setVisible(false);
                    }
                });
        setViews(meal);
    }

    private void onLoadingStatusChanged(Boolean aBoolean) {
        if (aBoolean)
            msgUtils.showProgressDialog();
        else {
            msgUtils.dismissProgressDialog();
            binding.homeDisappearingLayout.setVisibility(View.VISIBLE);
        }
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