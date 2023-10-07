package com.example.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.network.RestCall;
import com.example.networkResponse.CategoryCommonResponse;
import com.example.networkResponse.CategoryListResponse;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Flow;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchSubCategoryActivity extends AppCompatActivity {

    EditText etvSubCategorySearch;
    FloatingActionButton btnAddSubCategory;
    AppCompatSpinner categorySpinner;
    RecyclerView subCategoryListRecyclerView;
    APISubCategoryRecyclerViewAdapter apiSubCategoryRecyclerViewAdapter;
    RestCall restCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sub_category);

        etvSubCategorySearch = findViewById(R.id.etvSubCategorySearch);
        btnAddSubCategory = findViewById(R.id.btnAddSubCategory);
        categorySpinner = findViewById(R.id.categorySpinner);
        etvSubCategorySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (apiSubCategoryRecyclerViewAdapter != null) {
//                    apiSubCategoryRecyclerViewAdapter.Search(charSequence, subCategoryListRecyclerView);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnAddSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchSubCategoryActivity.this, AddSubCategoryActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getSubCategoryCall();
    }


//    private void updateCategorySpinner(List<CategoryListResponse.Category> categories) {
//        // Create an adapter for the spinner
//        ArrayAdapter<CategoryListResponse.Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Set the adapter to the spinner
//        categorySpinner.setAdapter(adapter);
//    }

//    private void getSubCategoryCall() {
//
//        restCall.getSubCategory("getSubCategory", "")
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Subscriber<SubCategoryCommonResponse>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(SearchSubCategoryActivity.this, "Error fetching Sub Category list", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onNext(SubCategoryCommonResponse subCategoryCommonResponse) {
//                            runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        });
//                    }
//                });
//    }
//
//    private void deleteSubCategoryCall(){
//        restCall.DeleteSubCategory("DeleteSubCategory","")
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Subscriber<SubCategoryCommonResponse>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(SearchSubCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onNext(SubCategoryCommonResponse subCategoryCommonResponse) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        });
//
//                    }
//                });
//    }

}
