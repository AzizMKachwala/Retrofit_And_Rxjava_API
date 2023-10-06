package com.example.Category;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.CategoryListResponse;
import com.example.networkResponse.CommonResponse;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SearchCategoryActivity extends AppCompatActivity {

    EditText etvSearch;
    RecyclerView categoryListRecyclerView;
    APICategoryRecyclerViewAdapter apiRecyclerViewAdapter;
    FloatingActionButton btnAddCategory;
    RestCall restCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);

        etvSearch = findViewById(R.id.etvSearch);
        categoryListRecyclerView = findViewById(R.id.categoryListRecyclerView);
        btnAddCategory = findViewById(R.id.btnAddCategory);

        categoryListRecyclerView.setLayoutManager(new LinearLayoutManager(SearchCategoryActivity.this));

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        etvSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (apiRecyclerViewAdapter!=null){
                    apiRecyclerViewAdapter.Search(charSequence, categoryListRecyclerView);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchCategoryActivity.this, AddCategoryActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCategoryCall();
    }

    private void getCategoryCall() {

//        Toast.makeText(this, "No Entry", Toast.LENGTH_LONG).show();
//    }

        restCall.getCategory("getCategory")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SearchCategoryActivity.this, "Error fetching category list", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                    @Override
                    public void onNext(CategoryListResponse categoryListResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
                                        }

                                        public void DeleteClick(CategoryListResponse.Category category) {

                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchCategoryActivity.this);
                                            alertDialog.setTitle("Alert!!");
                                            alertDialog.setMessage("Are you sure, you want to delete " + category.getCategoryName());

                                            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    DeleteCategoryCall(category.getCategoryId());
                                                    dialogInterface.dismiss();

                                                }
                                            });

                                            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            alertDialog.show();

                                        }
                                    });
                                } else {
                                    Toast.makeText(SearchCategoryActivity.this, categoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }

    public void DeleteCategoryCall(String category_id) {
        restCall.DeleteCategory("DeleteCategory",category_id)
                .subscribeOn(Schedulers.io())
                .observeOn((Schedulers.newThread()))
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SearchCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                    Toast.makeText(SearchCategoryActivity.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    getCategoryCall();
                                } else {
                                    Toast.makeText(SearchCategoryActivity.this, ""+commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                             }
                        });
                    }
                });
    }
}
