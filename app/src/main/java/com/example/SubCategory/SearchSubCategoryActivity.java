package com.example.SubCategory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.SignInSignUp.PreferenceManager;
import com.example.AppUtils.Tools;
import com.example.AppUtils.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.cate.CategoryListResponse;
import com.example.networkResponse.subcate.SubCategoryListResponse;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SearchSubCategoryActivity extends AppCompatActivity {

    EditText etvSubCategorySearch;
    FloatingActionButton btnAddSubCategory;
    AppCompatSpinner categorySpinner;
    RecyclerView subCategoryListRecyclerView;
    APISubCategoryRecyclerViewAdapter apiSubCategoryRecyclerViewAdapter;
    RestCall restCall;
    Tools tools;
    int selectedPos = 0;
    String selectedCategoryId, selectedCategoryName;
    SwipeRefreshLayout swipeRefreshLayout;
    com.example.SignInSignUp.PreferenceManager preferenceManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sub_category);

        etvSubCategorySearch = findViewById(R.id.etvSubCategorySearch);
        btnAddSubCategory = findViewById(R.id.btnAddSubCategory);
        categorySpinner = findViewById(R.id.categorySpinnerSubCategory);
        subCategoryListRecyclerView = findViewById(R.id.subCategoryListRecyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools = new Tools(this);

        preferenceManager = new PreferenceManager(this);

        getCateCall();

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

        btnAddSubCategory.setOnClickListener(view -> startActivity(new Intent(SearchSubCategoryActivity.this, AddSubCategoryActivity.class)));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetSubCategory();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCateCall();
    }

    private void getCateCall() {
        tools.showLoading();
        restCall.getCategory("getCategory",preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> Toast.makeText(SearchSubCategoryActivity.this, "Error fetching Category list", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onNext(CategoryListResponse categoryListResponse) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            if (categoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT) && categoryListResponse.getCategoryList() != null
                                    && categoryListResponse.getCategoryList().size() > 0) {

                                List<CategoryListResponse.Category> categories = categoryListResponse.getCategoryList();
                                String[] categoryNameArray = new String[categories.size() + 1];
                                String[] categoryIdArray = new String[categories.size() + 1];

                                categoryNameArray[0] = "Select Category";
                                categoryIdArray[0] = "-1";

                                for (int i = 0; i < categories.size(); i++) {
                                    categoryNameArray[i + 1] = categories.get(i).getCategoryName();
                                    categoryIdArray[i + 1] = categories.get(i).getCategoryId();
                                }

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchSubCategoryActivity.this, android.R.layout.simple_spinner_dropdown_item, categoryNameArray);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                categorySpinner.setAdapter(arrayAdapter);

                                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                        selectedPos = position;
                                        if (selectedPos >= 0 && selectedPos < categoryIdArray.length) {
                                            selectedCategoryId = categoryIdArray[selectedPos];
                                            selectedCategoryName = categoryNameArray[selectedPos];

                                            if (selectedCategoryId.equalsIgnoreCase("-1")) {
                                                Toast.makeText(SearchSubCategoryActivity.this, "Select Category", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                GetSubCategory();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        });
                    }
                });
        apiSubCategoryRecyclerViewAdapter = new APISubCategoryRecyclerViewAdapter(SearchSubCategoryActivity.this, new ArrayList<>());
    }

    private void GetSubCategory(){
        tools.showLoading();
        restCall.getSubCategory("getSubCategory",selectedCategoryId,preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> Toast.makeText(SearchSubCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)
                                    && subCategoryListResponse.getSubCategoryList() != null
                                    && subCategoryListResponse.getSubCategoryList().size() > 0) {

                                LinearLayoutManager layoutManager = new LinearLayoutManager(SearchSubCategoryActivity.this);
                                subCategoryListRecyclerView.setLayoutManager(layoutManager);

                                apiSubCategoryRecyclerViewAdapter = new APISubCategoryRecyclerViewAdapter(SearchSubCategoryActivity.this,subCategoryListResponse.getSubCategoryList());
                                subCategoryListRecyclerView.setAdapter(apiSubCategoryRecyclerViewAdapter);

                                apiSubCategoryRecyclerViewAdapter.SetUpInterface(new APISubCategoryRecyclerViewAdapter.SubCategoryClick() {
                                    @Override
                                    public void SubEditClick(SubCategoryListResponse.SubCategory subCategory) {
                                        String categoryId = subCategory.getCategoryId();
                                        String selectedSubCategoryId = subCategory.getSubCategoryId();
                                        String subCategoryName = subCategory.getSubcategoryName();

                                        Intent i = new Intent(SearchSubCategoryActivity.this, AddSubCategoryActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("category_id", categoryId);
                                        bundle.putString("sub_category_id", selectedSubCategoryId);
                                        bundle.putString("subCategoryName", subCategoryName);
                                        i.putExtras(bundle);
                                        startActivity(i);
                                        finish();
                                    }

                                    @Override
                                    public void SubDeleteClick(SubCategoryListResponse.SubCategory subCategory) {
                                        String subCategoryId = subCategory.getSubCategoryId();

                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchSubCategoryActivity.this);
                                        alertDialog.setTitle("Alert!!");
                                        alertDialog.setMessage("Are you sure, you want to delete Sub Category " + subCategory.getSubcategoryName());

                                        alertDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                                            deleteSubCategoryCall(subCategoryId);
                                            dialogInterface.dismiss();
                                        });

                                        alertDialog.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                                        alertDialog.show();
                                    }
                                });
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        });
                    }
                });
    }

    private void deleteSubCategoryCall(String subCategoryId) {
        tools.showLoading();
        restCall.DeleteSubCategory("DeleteSubCategory", subCategoryId,preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> Toast.makeText(SearchSubCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                GetSubCategory();
                            }
                            Toast.makeText(SearchSubCategoryActivity.this, subCategoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });
    }

}
