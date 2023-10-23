package com.example.Category;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.SignInSignUp.PreferenceManager;
import com.example.AppUtils.Tools;
import com.example.AppUtils.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.cate.CategoryListResponse;
import com.example.networkResponse.cate.CategoryCommonResponse;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SearchCategoryActivity extends AppCompatActivity {

    EditText etvSearch;
    RecyclerView categoryListRecyclerView;
    APICategoryRecyclerViewAdapter apiRecyclerViewAdapter;
    FloatingActionButton btnAddCategory;
    SwipeRefreshLayout swipeRefreshLayout;
    RestCall restCall;
    Tools tools;
    com.example.SignInSignUp.PreferenceManager preferenceManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);

        etvSearch = findViewById(R.id.etvCategorySearch);
        categoryListRecyclerView = findViewById(R.id.categoryListRecyclerView);
        btnAddCategory = findViewById(R.id.btnAddCategory);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        categoryListRecyclerView.setLayoutManager(new LinearLayoutManager(SearchCategoryActivity.this));

        preferenceManager = new PreferenceManager(this);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools = new Tools(this);

        etvSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (apiRecyclerViewAdapter != null) {
                    apiRecyclerViewAdapter.Search(charSequence, categoryListRecyclerView);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnAddCategory.setOnClickListener(view -> {
            Intent intent = new Intent(SearchCategoryActivity.this, AddCategoryActivity.class);
            startActivity(intent);
        });

        swipeRefreshLayout.setOnRefreshListener(this::getCategoryCall);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCategoryCall();
    }

    private void getCategoryCall() {
        tools.showLoading();
        restCall.getCategory("getCategory", preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        runOnUiThread(() -> {
                            tools.stopLoading();
                            Toast.makeText(SearchCategoryActivity.this, "Error fetching Category list", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        });
                    }

                    @Override
                    public void onNext(CategoryListResponse categoryListResponse) {

                        runOnUiThread(() -> {
                            tools.stopLoading();
                            if (categoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)
                                    && categoryListResponse.getCategoryList() != null
                                    && categoryListResponse.getCategoryList().size() > 0) {

                                LinearLayoutManager layoutManager = new LinearLayoutManager(SearchCategoryActivity.this);
                                categoryListRecyclerView.setLayoutManager(layoutManager);

                                apiRecyclerViewAdapter = new APICategoryRecyclerViewAdapter(categoryListResponse.getCategoryList());
                                categoryListRecyclerView.setAdapter(apiRecyclerViewAdapter);

                                apiRecyclerViewAdapter.SetUpInterface(new APICategoryRecyclerViewAdapter.CategoryClick() {
                                    @Override
                                    public void EditClick(CategoryListResponse.Category category) {
                                        Intent intent = new Intent(SearchCategoryActivity.this, AddCategoryActivity.class);
                                        intent.putExtra("category_id", category.getCategoryId());
                                        intent.putExtra("category_name", category.getCategoryName());
                                        startActivity(intent);
                                        finish();
                                    }

                                    public void DeleteClick(CategoryListResponse.Category category) {

                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchCategoryActivity.this);
                                        alertDialog.setTitle("Alert!!");
                                        alertDialog.setMessage("Are you sure, you want to delete Category " + category.getCategoryName());

                                        alertDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                                            DeleteCategoryCall(category.getCategoryId());
                                            dialogInterface.dismiss();
                                        });

                                        alertDialog.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                                        alertDialog.show();
                                    }

                                    @Override
                                    public void onSwitchChanged(CategoryListResponse.Category category, boolean isChecked) {
                                        String categoryId = category.getCategoryId();
                                        ActiveDeactiveCategoryCall(categoryId, isChecked);
                                    }

                                });
                            } else {
                                Toast.makeText(SearchCategoryActivity.this, categoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        });
                    }
                });
    }

    public void ActiveDeactiveCategoryCall(String categoryId, boolean isChecked){
        tools.showLoading();
        String status = isChecked ? "0" : "1";
        restCall.ActiveDeactiveCategory("ActiveDeactiveCategory", status, categoryId, preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CategoryCommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            Toast.makeText(SearchCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onNext(CategoryCommonResponse categoryCommonResponse) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            if (categoryCommonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                Toast.makeText(SearchCategoryActivity.this, "Category Status Updated: " + status, Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(SearchCategoryActivity.this, categoryCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });
    }

    public void DeleteCategoryCall(String category_id) {
        tools.showLoading();
        restCall.DeleteCategory("DeleteCategory", preferenceManager.getUserId(), category_id)
                .subscribeOn(Schedulers.io())
                .observeOn((Schedulers.newThread()))
                .subscribe(new Subscriber<CategoryCommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            Toast.makeText(SearchCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onNext(CategoryCommonResponse categoryCommonResponse) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            if (categoryCommonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                Toast.makeText(SearchCategoryActivity.this, categoryCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                getCategoryCall();
                            } else {
                                Toast.makeText(SearchCategoryActivity.this, "" + categoryCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}
