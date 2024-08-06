package com.example.foodiefinder.UI;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiefinder.Database.RestaurantRepository;
import com.example.foodiefinder.Entities.Restaurant;
import com.example.foodiefinder.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private List<Restaurant> mRestaurants;
    private List<Restaurant> mRestaurantsFull;
    private final Context context;
    private final LayoutInflater mInflater;

    public RestaurantAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final TextView restaurantItemView;
        private final CheckBox checkBox;
        private final RestaurantAdapter adapter;

        public RestaurantViewHolder(@NonNull View itemView, RestaurantAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            restaurantItemView = itemView.findViewById(R.id.textViewName);
            checkBox = itemView.findViewById(R.id.checkBox);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Restaurant current = mRestaurants.get(position);
                Intent intent = new Intent(context, RestaurantDetails.class);
                intent.putExtra("id", current.getRestaurantID());
                intent.putExtra("name", current.getName());
                intent.putExtra("category", current.getCategory());
                intent.putExtra("neighborhood", current.getNeighborhood());
                intent.putExtra("address", current.getAddress());
                intent.putExtra("phoneNumber", current.getPhoneNumber());
                intent.putExtra("website", current.getWebsite());
                intent.putExtra("rating", current.getRating());
                intent.putExtra("comments", current.getComment());
                intent.putExtra("isChecked", current.isChecked());
                intent.putExtra("dateVisited", current.getDateVisited());
                context.startActivity(intent);
            });

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {Restaurant restaurant = mRestaurants.get(position);
                    restaurant.setChecked(isChecked);

                    // Update the restaurant in the repository on a background thread
                    new Thread(() -> {
                        RestaurantRepository repository = new RestaurantRepository((Application) itemView.getContext().getApplicationContext());
                        repository.update(restaurant);

                        // Update the adapter on the main thread
                        itemView.post(() -> {
                            adapter.notifyItemChanged(position);
                        });
                    }).start();
                }
            });
        }
    }

    @NonNull
    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.RestaurantViewHolder holder, int position) {
        if (mRestaurants != null) {
            Restaurant current = mRestaurants.get(position);
            String name = current.getName();
            holder.restaurantItemView.setText(name);
            holder.checkBox.setChecked(current.isChecked()); // Set checkbox state based on isChecked field
        } else {
            holder.restaurantItemView.setText("No restaurant name");
        }
    }

    @Override
    public int getItemCount() {
        if (mRestaurants != null) {
            return mRestaurants.size();
        } else return 0;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
        this.mRestaurantsFull = new ArrayList<>(restaurants);
        notifyDataSetChanged();
    }


    public void filter(String text) {
        List<Restaurant> filteredList = new ArrayList<>();
        if (text.isEmpty()) {
            filteredList.addAll(mRestaurantsFull);
        } else {
            String filterPattern = text.toLowerCase().trim();
            for (Restaurant restaurant : mRestaurantsFull) {
                if (restaurant.getName().toLowerCase().contains(filterPattern)
                        || restaurant.getCategory().toLowerCase().contains(filterPattern)
                        || restaurant.getNeighborhood().toLowerCase().contains(filterPattern)) {
                    filteredList.add(restaurant);
                }
            }
        }
        mRestaurants.clear();
        mRestaurants.addAll(filteredList);
        notifyDataSetChanged();
    }
}

