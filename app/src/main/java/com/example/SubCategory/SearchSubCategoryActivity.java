package com.example.SubCategory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.Category.AddCategoryActivity;
import com.example.Category.SearchCategoryActivity;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SearchSubCategoryActivity extends AppCompatActivity {

    EditText etvSubCategorySearch;
    FloatingActionButton btnAddSubCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sub_category);

        etvSubCategorySearch = findViewById(R.id.etvSubCategorySearch);
        btnAddSubCategory = findViewById(R.id.btnAddSubCategory);

        btnAddSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchSubCategoryActivity.this, AddSubCategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}