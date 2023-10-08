package com.example.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.network.RestCall;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

//        CategoryListResponse categoryListResponse = CategoryListResponse.getCategoryList();
//        List<CategoryListResponse.Category> categoriesList =  cate goryListResponse.getCategoryList();  // Obtain from getCategory API

// Extract category names from the list
        String[] categories = {"1","2","3","4"};
//                = new String[categoriesList.size()];
//        for (int i = 0; i < categoriesList.size(); i++) {
//            categories[i] = String.valueOf(categoriesList.get(i).getCategoryName());
//        }

        ArrayAdapter<String> categoryList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryList);

        etvSubCategorySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (apiSubCategoryRecyclerViewAdapter != null) {
                    apiSubCategoryRecyclerViewAdapter.Search(charSequence, subCategoryListRecyclerView);
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
