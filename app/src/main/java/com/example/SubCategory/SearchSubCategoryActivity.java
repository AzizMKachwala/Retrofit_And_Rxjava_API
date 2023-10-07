package com.example.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.network.RestCall;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SearchSubCategoryActivity extends AppCompatActivity {

    EditText etvSubCategorySearch;
    FloatingActionButton btnAddSubCategory;
    RecyclerView subCategoryListRecyclerView;
    APISubCategoryRecyclerViewAdapter apiSubCategoryRecyclerViewAdapter;
    AppCompatSpinner categorySpinner;
    RestCall restCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sub_category);

        etvSubCategorySearch = findViewById(R.id.etvSubCategorySearch);
        btnAddSubCategory = findViewById(R.id.btnAddSubCategory);
        categorySpinner = findViewById(R.id.categorySpinner);

        btnAddSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchSubCategoryActivity.this, AddSubCategoryActivity.class);
                startActivity(intent);
            }
        });

//        String[] blockData = {"Select Block", "Block A", "Block B", "Block C","Block D"};
//        ArrayAdapter<String> blockAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, blockData);
//        blockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        categorySpinner.setAdapter(blockAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}