package com.example.foodiefinder.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodiefinder.Database.RestaurantRepository;
import com.example.foodiefinder.Entities.Restaurant;
import com.example.foodiefinder.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RestaurantDetails extends AppCompatActivity {
    int restaurantID;
    EditText editName;
    EditText editNeighborhood;
    EditText editPostalAddress;
    EditText editPhoneNumber;
    EditText editWebsite;
    EditText editCategory;
    EditText editComment;
    RatingBar ratingBar;
    EditText editDate;
    RestaurantRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        repository = new RestaurantRepository(getApplication());

        editName = findViewById(R.id.nameEditText);
        editCategory = findViewById(R.id.categoryEditText);
        editNeighborhood = findViewById(R.id.neighborhoodEditText);
        editPostalAddress = findViewById(R.id.editTextPostalAddress);
        editPhoneNumber = findViewById(R.id.editTextPhone);
        editWebsite = findViewById(R.id.websiteEditText);
        editComment = findViewById(R.id.editTextTextMultiLine);
        ratingBar = findViewById(R.id.ratingBar);
        editDate = findViewById(R.id.dateVisitedEditText);


        restaurantID = getIntent().getIntExtra("id", -1);
        if (restaurantID != -1) {
            String name = getIntent().getStringExtra("name");
            String category = getIntent().getStringExtra("category");
            String neighborhood = getIntent().getStringExtra("neighborhood");
            String phoneNumber = getIntent().getStringExtra("phoneNumber");
            String website = getIntent().getStringExtra("website");
            String address = getIntent().getStringExtra("address");
            int rating = getIntent().getIntExtra("rating", 0);
            String comment = getIntent().getStringExtra("comments");
            Boolean isChecked = getIntent().getBooleanExtra("isChecked", false);
            String dateVisited = getIntent().getStringExtra("dateVisited");

            editName.setText(name);
            editNeighborhood.setText(neighborhood);
            editPhoneNumber.setText(phoneNumber);
            editPostalAddress.setText(address);
            editWebsite.setText(website);
            editCategory.setText(category);
            editComment.setText(comment);
            ratingBar.setRating(rating);
            editDate.setText(dateVisited);

            editDate.setOnClickListener(v -> {
                // Show DatePickerDialog for start date
                showDatePickerDialog(editDate, () -> {
                    // Callback for when a valid start date is selected (optional)
                });
            });
        }
        Log.d("RestaurantDetails", "Category: " + editCategory.getText().toString());
        Log.d("RestaurantDetails", "Address: " + editPostalAddress.getText().toString());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurantdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.restaurantsave) {
            if (validateRestaurantDetails()) {
                if (restaurantID == -1) {
                    // Insert new vacation
                    Restaurant restaurant;
                    if (repository.getAllRestaurants().size() == 0) restaurantID = 1;
                    else
                        restaurantID = repository.getAllRestaurants().get(repository.getAllRestaurants().size() - 1).getRestaurantID() + 1;
                    saveRestaurant();
                } else {
                    // Update existing vacation
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
        String dateString = editDate.getText().toString();
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        if (name.isEmpty()) {
            // Display error message for empty vacation name
            Toast.makeText(this, "Restaurant name cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!dateString.isEmpty()) {
            try {
                Date date = sdf.parse(dateString);
            } catch (ParseException e) {
                Toast.makeText(this, "Invalid date format. Use MM/dd/yyyy.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void deleteRestaurant() {
        Restaurant restaurant = new Restaurant(
                restaurantID,
                editName.getText().toString(),
                editCategory.getText().toString(),
                editNeighborhood.getText().toString(),
                editPhoneNumber.getText().toString(),
                editWebsite.getText().toString(),
                editPostalAddress.getText().toString(),
                (int) ratingBar.getRating(),
                editComment.getText().toString(),
                false,
                editDate.getText().toString()
        );
        repository.delete(restaurant);
        finish();
    }

    private void updateRestaurant() {
        Restaurant restaurant = new Restaurant(
                restaurantID,
                editName.getText().toString(),
                editCategory.getText().toString(),
                editNeighborhood.getText().toString(),
                editPhoneNumber.getText().toString(),
                editWebsite.getText().toString(),
                editPostalAddress.getText().toString(),
                (int) ratingBar.getRating(),
                editComment.getText().toString(),
                getRestaurantCheckedState(restaurantID),
                editDate.getText().toString()
        );
        repository.update(restaurant);
        Log.d("SaveRestaurant", "Category: " + restaurant.getCategory());
        Log.d("SaveRestaurant", "Address: " + restaurant.getAddress());
        finish();
    }

    private boolean getRestaurantCheckedState(int restaurantID) {
        Restaurant restaurant = repository.getRestaurantById(restaurantID);
        return restaurant != null && restaurant.isChecked();
    }

    private void saveRestaurant() {
        Restaurant restaurant = new Restaurant(
                restaurantID,
                editName.getText().toString(),
                editCategory.getText().toString(),
                editNeighborhood.getText().toString(),
                editPhoneNumber.getText().toString(),
                editWebsite.getText().toString(),
                editPostalAddress.getText().toString(),
                (int) ratingBar.getRating(),
                editComment.getText().toString(),
                false,
                editDate.getText().toString()
        );
        repository.insert(restaurant);
        Log.d("SaveRestaurant", "Category: " + restaurant.getCategory());
        Log.d("SaveRestaurant", "Address: " + restaurant.getAddress());
        finish();
    }

    private void showDatePickerDialog(final EditText editText, final Runnable onDateSelected) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Set selected date on EditText
                    editText.setText(selectedMonth + 1 + "/" + selectedDayOfMonth + "/" + selectedYear);
                    onDateSelected.run();
                }, year, month, dayOfMonth);

        // Show DatePickerDialog
        datePickerDialog.show();
    }
}
