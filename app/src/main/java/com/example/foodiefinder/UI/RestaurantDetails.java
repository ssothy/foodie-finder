package com.example.foodiefinder.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodiefinder.Database.RestaurantRepository;
import com.example.foodiefinder.Entities.Restaurant;
import com.example.foodiefinder.R;
import com.example.foodiefinder.ViewModel.RestaurantViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RestaurantDetails extends AppCompatActivity {
    int restaurantID;
    String name;
    String neighborhood;
    String address;
    String phoneNumber;
    String website;
    String category;
    int rating;
    String comment;

    EditText editName;
    EditText editNeighborhood;
    EditText editPostalAddress;
    EditText editPhoneNumber;
    EditText editWebsite;
    EditText editCategory;
    EditText editComment;
    RatingBar ratingBar;

    RestaurantViewModel restaurantViewModel;
    Restaurant restaurant;
    List<Restaurant> allRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurant_details);

        editName = findViewById(R.id.nameEditText);
        editNeighborhood = findViewById(R.id.neighborhoodEditText);
        editPostalAddress = findViewById(R.id.editTextPostalAddress);
        editPhoneNumber = findViewById(R.id.editTextPhone);
        editWebsite = findViewById(R.id.websiteEditText);
        editCategory = findViewById(R.id.categoryEditText);
        editComment = findViewById(R.id.editTextTextMultiLine);
        ratingBar = findViewById(R.id.ratingBar);

        restaurantID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        neighborhood = getIntent().getStringExtra("neighborhood");
        address = getIntent().getStringExtra("address");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        website = getIntent().getStringExtra("website");
        category = getIntent().getStringExtra("category");
        rating = getIntent().getIntExtra("rating", 0);
        comment = getIntent().getStringExtra("comment");

        editName.setText(name);
        editNeighborhood.setText(neighborhood);
        editPhoneNumber.setText(phoneNumber);
        editPostalAddress.setText(address);
        editWebsite.setText(website);
        editCategory.setText(category);
        editComment.setText(comment);
        ratingBar.setRating(rating);

        restaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);

        restaurantViewModel.getRestaurantById(restaurantID).observe(this, new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {
                if (restaurant != null) {
                    RestaurantDetails.this.restaurant = restaurant;
                    ratingBar.setRating(restaurant.getRating());
                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser && restaurant != null) {
                    restaurant.setRating((int) rating);
                    restaurantViewModel.update(restaurant);
                }
            }
        });

        restaurantViewModel.getAllRestaurants().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                allRestaurants = restaurants;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurantdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.restaurantsave) {
            if (validateRestaurantDetails()) {
                if (restaurantID == -1) {
                    // Insert new restaurant
                    if (allRestaurants == null || allRestaurants.isEmpty()) {
                        restaurantID = 1;
                    } else {
                        restaurantID = allRestaurants.get(allRestaurants.size() - 1).getRestaurantID() + 1;
                    }
                    saveRestaurant();
                } else {
                    // Update existing restaurant
                    updateRestaurant();
                }
            }
            return true;
        } else if (item.getItemId() == R.id.restaurantdelete) {
            deleteRestaurant();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateRestaurantDetails() {
        String name = editName.getText().toString();

        if (name.isEmpty()) {
            // Display error message for empty restaurant name
            Toast.makeText(this, "Restaurant name cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveRestaurant() {
        Restaurant restaurant = new Restaurant(
                restaurantID,
                editName.getText().toString(),
                editNeighborhood.getText().toString(),
                editPhoneNumber.getText().toString(),
                editPostalAddress.getText().toString(),
                editWebsite.getText().toString(),
                editCategory.getText().toString(),
                (int) ratingBar.getRating(),
                editComment.getText().toString()
        );
        restaurantViewModel.insert(restaurant);
        finish();
    }

    private void updateRestaurant() {
        Restaurant restaurant = new Restaurant(
                restaurantID,
                editName.getText().toString(),
                editNeighborhood.getText().toString(),
                editPhoneNumber.getText().toString(),
                editPostalAddress.getText().toString(),
                editWebsite.getText().toString(),
                editCategory.getText().toString(),
                (int) ratingBar.getRating(),
                editComment.getText().toString()
        );
        restaurantViewModel.update(restaurant);
        finish();
    }

    private void deleteRestaurant() {
        if (restaurant != null) {
            restaurantViewModel.delete(restaurant);
            finish();
        }
    }
}
