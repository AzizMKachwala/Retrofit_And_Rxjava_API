package com.example.StartUp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.AppUtils.VariableBag;
import com.example.Category.SearchCategoryActivity;
import com.example.Product.SearchProductActivity;
import com.example.ProductCatalog.ProductCatalogActivity;
import com.example.SignInSignUp.PreferenceManager;
import com.example.SignInSignUp.SignInSignUpActivity;
import com.example.SubCategory.SearchSubCategoryActivity;
import com.example.retrofitandrxjavaapidemo.R;

public class HomePageActivity extends AppCompatActivity {

    Button btnCategory,btnSubCategory,btnSignOut,btnAddProduct,btnProductCatalogue;
    TextView txtLoginName;
    PreferenceManager preferenceManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        txtLoginName = findViewById(R.id.txtLoginName);
        btnCategory = findViewById(R.id.btnCategory);
        btnSubCategory = findViewById(R.id.btnSubCategory);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnProductCatalogue = findViewById(R.id.btnProductCatalogue);

        preferenceManager = new PreferenceManager(this);

        String name = preferenceManager.getKeyValueString(VariableBag.KEY_FIRSTNAME);
        String lastname = preferenceManager.getKeyValueString(VariableBag.KEY_LASTNAME);

        txtLoginName.setText(name + " " + lastname);

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

        btnProductCatalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, ProductCatalogActivity.class);
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
