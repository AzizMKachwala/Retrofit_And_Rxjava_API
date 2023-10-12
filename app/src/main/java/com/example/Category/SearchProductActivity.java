package com.example.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.HomePageActivity;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SearchProductActivity extends AppCompatActivity {

    AppCompatSpinner categorySpinnerProduct,subCategorySpinnerProduct;
    EditText etvProductSearch;
    RecyclerView productRecyclerView;
    FloatingActionButton btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        categorySpinnerProduct = findViewById(R.id.categorySpinnerProduct);
        subCategorySpinnerProduct = findViewById(R.id.subCategorySpinnerProduct);
        etvProductSearch = findViewById(R.id.etvProductSearch);
        productRecyclerView = findViewById(R.id.productRecyclerView);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchProductActivity.this, AddProductActivity.class));
            }
        });
    }
}