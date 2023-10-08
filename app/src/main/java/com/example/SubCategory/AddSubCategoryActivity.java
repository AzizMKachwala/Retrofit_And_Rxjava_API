package com.example.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Category.AddCategoryActivity;
import com.example.Category.SearchCategoryActivity;
import com.example.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.CategoryListResponse;
import com.example.networkResponse.SubCategoryCommonResponse;
import com.example.networkResponse.SubCategoryListResponse;
import com.example.retrofitandrxjavaapidemo.R;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddSubCategoryActivity extends AppCompatActivity {

    EditText etvSubCategoryName;
    AppCompatSpinner selectCategorySpinner;
    Button btnSubAdd;
    RestCall restCall;
    boolean isEdit = false;
    String subcategory_name,category_id,sub_category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_category);

        etvSubCategoryName = findViewById(R.id.etvSubCategoryName);
        selectCategorySpinner = findViewById(R.id.selectCategorySpinner);
        btnSubAdd = findViewById(R.id.btnSubAdd);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        List<CategoryListResponse.Category> categoriesList = new ArrayList<>();
        String[] categories = new String[categoriesList.size() + 1];
        categories[0] = "Select Category";

        for (int i = 0; i < categoriesList.size(); i++) {
            categories[i + 1] = categoriesList.get(i).getCategoryId();
        }

        ArrayAdapter<String> ArrayList = new ArrayAdapter<>(AddSubCategoryActivity.this, android.R.layout.simple_spinner_item, categories);
        ArrayList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategorySpinner.setAdapter(ArrayList);

        selectCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (getIntent() != null && getIntent().getStringExtra("category_id") != null) {

            isEdit = true;
            category_id = getIntent().getStringExtra("category_id");
            subcategory_name = getIntent().getStringExtra("subcategory_name");
            sub_category_id = getIntent().getStringExtra("sub_category_id");

            etvSubCategoryName.setText(subcategory_name);
            btnSubAdd.setText("Edit");

        } else {
            isEdit = false;
            btnSubAdd.setText("Add");
        }

        btnSubAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectCategorySpinner != null && etvSubCategoryName.getText().toString().trim().equalsIgnoreCase("")) {
                    etvSubCategoryName.setError("Enter Sub-Category Name");
                    etvSubCategoryName.requestFocus();
                } else {
                    if (isEdit) {
                        editSubCategoryCall();
                    } else {
                        addSubCategoryCall();
                    }
                }
            }
        });
    }

    private void addSubCategoryCall() {
        restCall.AddSubCategory("AddSubCategory", "", etvSubCategoryName.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddSubCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                    etvSubCategoryName.setText("");

                                    startActivity(new Intent(AddSubCategoryActivity.this, SearchSubCategoryActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(AddSubCategoryActivity.this, subCategoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }

    private void editSubCategoryCall() {
        restCall.EditSubCategory("EditSubCategory", "", etvSubCategoryName.getText().toString(), "")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddSubCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                    etvSubCategoryName.setText("");

                                    startActivity(new Intent(AddSubCategoryActivity.this, SearchSubCategoryActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(AddSubCategoryActivity.this, subCategoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }

}