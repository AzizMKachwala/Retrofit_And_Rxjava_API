package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Category.AddProductActivity;
import com.example.Category.SearchCategoryActivity;
import com.example.Category.SearchProductActivity;
import com.example.SignInSignUp.PreferenceManager;
import com.example.SignInSignUp.SignInSignUpActivity;
import com.example.SubCategory.SearchSubCategoryActivity;
import com.example.retrofitandrxjavaapidemo.R;

public class HomePageActivity extends AppCompatActivity {

    Button btnCategory,btnSubCategory,btnSignOut,btnAddProduct;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnCategory = findViewById(R.id.btnCategory);
        btnSubCategory = findViewById(R.id.btnSubCategory);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, SearchCategoryActivity.class);
                startActivity(intent);
            }
        });

        btnSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, SearchSubCategoryActivity.class);
                startActivity(intent);
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, SearchProductActivity.class);
                startActivity(intent);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PreferenceManager preferenceManager = new PreferenceManager(HomePageActivity.this);
                // Clear or reset user-related preferences
                preferenceManager.setUserLoggedIn(false);
                preferenceManager.setUserId("");

                // Redirect to the login screen
                Intent intent = new Intent(HomePageActivity.this, SignInSignUpActivity.class);
                // Set the FLAG_ACTIVITY_NEW_TASK and FLAG_ACTIVITY_CLEAR_TASK flags to clear the back stack
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
