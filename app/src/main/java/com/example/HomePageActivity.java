package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Category.AddCategoryActivity;
import com.example.Category.SearchCategoryActivity;
import com.example.SubCategory.SearchSubCategoryActivity;
import com.example.networkResponse.UserResponse;
import com.example.retrofitandrxjavaapidemo.R;

public class HomePageActivity extends AppCompatActivity {

    Button btnCategory,btnSubCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnCategory = findViewById(R.id.btnCategory);
        btnSubCategory = findViewById(R.id.btnSubCategory);

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
//                Toast.makeText(MainActivity.this, "SubCategory Will Be Added Soon", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomePageActivity.this, SearchSubCategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}