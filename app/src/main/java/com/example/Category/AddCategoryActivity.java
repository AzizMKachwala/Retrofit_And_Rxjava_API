package com.example.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.SignInSignUp.PreferenceManager;
import com.example.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.cate.CategoryCommonResponse;
import com.example.retrofitandrxjavaapidemo.R;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class AddCategoryActivity extends AppCompatActivity {

    EditText etvCategoryName;
    Button btnAdd;
    RestCall restCall;
    boolean isEdit = false;
    String category_name,category_id;
    com.example.SignInSignUp.PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        etvCategoryName = findViewById(R.id.etvCategoryName);
        btnAdd = findViewById(R.id.btnAdd);
        preferenceManager = new PreferenceManager(this);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        preferenceManager = new PreferenceManager(this);

        if (getIntent() != null && getIntent().getStringExtra("category_id") != null) {

            isEdit = true;
            category_id = getIntent().getStringExtra("category_id");
            category_name = getIntent().getStringExtra("category_name");

            etvCategoryName.setText(category_name);
            btnAdd.setText("Edit");
        } else {
            isEdit = false;
            btnAdd.setText("Add");
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etvCategoryName.getText().toString().trim().equalsIgnoreCase("")) {
                    etvCategoryName.setError("Enter Category Name");
                    etvCategoryName.requestFocus();
                } else {
                    if (isEdit) {
                        editCategoryCall();
                    } else {
                        addCategoryCall();
                    }
                }
            }
        });
    }

    public void editCategoryCall() {
        restCall.EditCategory("EditCategory", etvCategoryName.getText().toString(), category_id,preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn((Schedulers.newThread()))
                .subscribe(new Subscriber<CategoryCommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CategoryCommonResponse categoryCommonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (categoryCommonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                    etvCategoryName.setText("");

                                    startActivity(new Intent(AddCategoryActivity.this, SearchCategoryActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(AddCategoryActivity.this, categoryCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }

    public void addCategoryCall() {

        restCall.AddCategory("AddCategory", preferenceManager.getUserId(), etvCategoryName.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CategoryCommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CategoryCommonResponse categoryCommonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (categoryCommonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                    etvCategoryName.setText("");

                                    startActivity(new Intent(AddCategoryActivity.this, SearchCategoryActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(AddCategoryActivity.this, categoryCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });
    }

}
