package com.example.foodiefinder.UI;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiefinder.Entities.Restaurant;
import com.example.foodiefinder.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private List<Restaurant> mRestaurants;
    private final SparseBooleanArray selectedItems = new SparseBooleanArray();
    private final Context context;
    private final LayoutInflater mInflater;

    public RestaurantAdapter (Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final TextView restaurantItemView;
        private final CheckBox checkBox;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantItemView = itemView.findViewById(R.id.textViewName);
            checkBox = itemView.findViewById(R.id.checkBox);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Restaurant current = mRestaurants.get(position);
                    Intent intent = new Intent(context, RestaurantDetails.class);
                    intent.putExtra("id", current.getRestaurantID());
                    intent.putExtra("name", current.getName());
                    intent.putExtra("address", current.getAddress());
                    intent.putExtra("neighborhood", current.getNeighborhood());
                    intent.putExtra("phoneNumber", current.getPhoneNumber());
                    intent.putExtra("website", current.getWebsite());
                    intent.putExtra("category", current.getCategory());
                    intent.putExtra("rating", current.getRating());
                    intent.putExtra("comments", current.getComment());
                    context.startActivity(intent);
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        selectedItems.put(mRestaurants.get(position).getRestaurantID(), isChecked);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.RestaurantViewHolder holder, int position) {
        if (mRestaurants != null) {
            Restaurant current = mRestaurants.get(position);
            holder.restaurantItemView.setText(current.getName());
            holder.checkBox.setOnCheckedChangeListener(null);
            holder.checkBox.setChecked(selectedItems.get(current.getRestaurantID(), false));
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    selectedItems.put(current.getRestaurantID(), isChecked);
                }
            });
        } else {
            holder.restaurantItemView.setText("No restaurant name");
        }
    }


    @Override
    public int getItemCount() {
        if (mRestaurants != null) {
            return mRestaurants.size();
        }
        else return 0;
    }

    public void setmRestaurants(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedItems() {
        return selectedItems;
    }
}