package com.pathakbau.foodycb.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pathakbau.foodycb.R;
import com.pathakbau.foodycb.databinding.ItemListViewBinding;
import com.pathakbau.foodycb.model.Meal;

import org.jetbrains.annotations.NotNull;

public class MealsRecyclerAdapter extends
        ListAdapter<Meal, RecyclerView.ViewHolder> {
    private final int EMPTY_VIEW_TYPE = 0;
    private final int NON_EMPTY_VIEW_TYPE = 1;

    private OnMealItemClickListener listener;
    private String emptyMsg;

    protected MealsRecyclerAdapter(OnMealItemClickListener listener, String emptyMsg) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.emptyMsg = emptyMsg;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("recycler", "onCreateViewHolder: ");
        if (viewType == NON_EMPTY_VIEW_TYPE) {
            return new NonEmptyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_view, parent, false));
        }
        else {
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.empty_recycler_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof NonEmptyViewHolder) {
            Meal curItem = getItem(position);
            NonEmptyViewHolder holder = (NonEmptyViewHolder) viewHolder;
            Glide.with(holder.binding.mealImage)
                    .load(curItem.getStrMealThumb())
                    .centerCrop()
                    .placeholder(R.drawable.iron)
                    .into(holder.binding.mealImage);
            Glide.with(holder.binding.mealImage)
                    .load(curItem.getStrMealThumb())
                    .centerCrop()
                    .into(holder.binding.backImg);
            holder.binding.mealName.setText(curItem.getStrMeal());
            holder.binding.mealCategory.setText(curItem.getStrCategory());
            holder.binding.mealArea.setText(curItem.getStrArea());
            holder.binding.itemListCard.setOnClickListener(v ->
                    listener.onMealItemClickListener(curItem.getIdMeal()));
        }
        else {
            ((EmptyViewHolder)viewHolder).emptyMsg.setText(emptyMsg);
        }
    }

    @Override
    public int getItemCount() {
        if (getCurrentList().size()==0) return 1;
        else return getCurrentList().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getCurrentList().size()==0) return EMPTY_VIEW_TYPE;
        else return NON_EMPTY_VIEW_TYPE;
    }

    public Meal getMealAt(int position) {
        return getItem(position);
    }

    public void setEmptyMsg(String emptyMsg) {
        this.emptyMsg = emptyMsg;
    }

    private static final DiffUtil.ItemCallback<Meal> DIFF_CALLBACK = new DiffUtil.ItemCallback<Meal>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Meal oldItem, @NonNull @NotNull Meal newItem) {
            return oldItem.getIdMeal().equals(newItem.getIdMeal());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Meal oldItem, @NonNull @NotNull Meal newItem) {
            return oldItem.getIdMeal().equals(newItem.getIdMeal()) &&
                    oldItem.getStrMeal().equals(newItem.getStrMeal()) &&
                    oldItem.getStrMealThumb().equals(newItem.getStrMealThumb()) &&
                    oldItem.getStrCategory().equals(newItem.getStrCategory()) &&
                    oldItem.getStrArea().equals(newItem.getStrArea());
        }
    };

    public static class NonEmptyViewHolder extends RecyclerView.ViewHolder {
        public ItemListViewBinding binding;
        public NonEmptyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding = ItemListViewBinding.bind(itemView);
        }
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView emptyMsg;

        public EmptyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            emptyMsg = itemView.findViewById(R.id.emptyMsg);
        }
    }

    public interface OnMealItemClickListener {
        public void onMealItemClickListener(String mealId);
    }
}
