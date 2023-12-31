package com.example.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class AddSubCategoryActivity extends AppCompatActivity {

    EditText etvSubCategoryName;
    AppCompatSpinner selectCategorySpinner;
    Button btnSubAdd,btnCancel;
    RestCall restCall;
    Tools tools;
    boolean isEdit = false;
    String selectedCategoryId, selectedCategoryName, subcategory_name, category_id, selectedSubCategoryId;
    int selectedPos = 0;
    com.example.SignInSignUp.PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_category);

        etvSubCategoryName = findViewById(R.id.etvSubCategoryName);
        selectCategorySpinner = findViewById(R.id.selectCategorySpinner);
        btnSubAdd = findViewById(R.id.btnSubAdd);
        btnCancel = findViewById(R.id.btnCancel);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools = new Tools(this);

        preferenceManager = new PreferenceManager(this); //Always create a class object before using any method of it

        getCateCall();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString("category_id") != null) {

            isEdit = true;
            category_id = bundle.getString("category_id");
            selectedSubCategoryId = bundle.getString("sub_category_id");
            subcategory_name = bundle.getString("subCategoryName");

            etvSubCategoryName.setText(subcategory_name);
            selectCategorySpinner.setEnabled(false);

            btnSubAdd.setText("Edit");
        } else {
            isEdit = false;
            btnSubAdd.setText("Add");
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubAdd.setOnClickListener(view -> {
            if (selectCategorySpinner != null && etvSubCategoryName.getText().toString().trim().equalsIgnoreCase("")) {
                etvSubCategoryName.setError("Enter Sub-Category Name");
                etvSubCategoryName.requestFocus();
            } else {
                if (isEdit) {
                        editSubCategoryCall();
//                        Toast.makeText(AddSubCategoryActivity.this, "Select Category", Toast.LENGTH_SHORT).show();
                } else {
                    addSubCategoryCall();
                }
            }
        });
    }

    private void addSubCategoryCall() {
        tools.showLoading();
        restCall.AddSubCategory("AddSubCategory", selectedCategoryId, etvSubCategoryName.getText().toString().trim(), preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            Toast.makeText(AddSubCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                etvSubCategoryName.setText("");
                                
                                finish();
                            } else {
                                Toast.makeText(AddSubCategoryActivity.this, subCategoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    private void editSubCategoryCall() {
        tools.showLoading();
        restCall.EditSubCategory("EditSubCategory", category_id,
                        etvSubCategoryName.getText().toString(), selectedSubCategoryId, preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            Toast.makeText(AddSubCategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                etvSubCategoryName.setText("");

                                finish();
                            } else {
                                Toast.makeText(AddSubCategoryActivity.this, subCategoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    private void getCateCall() {
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
                            Toast.makeText(AddSubCategoryActivity.this, "Error fetching Category list", Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onNext(CategoryListResponse categoryListResponse) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            if (categoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)
                                    && categoryListResponse.getCategoryList() != null
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

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddSubCategoryActivity.this, android.R.layout.simple_spinner_dropdown_item, categoryNameArray);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                selectCategorySpinner.setAdapter(arrayAdapter);

                                selectCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                        selectedPos = position;
                                        if (selectedPos >= 0 && selectedPos < categoryIdArray.length) {
                                            selectedCategoryId = categoryIdArray[selectedPos];
                                            selectedCategoryName = categoryNameArray[selectedPos];

//                                                Toast.makeText(AddSubCategoryActivity.this, "" + selectedCategoryId, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            Toast.makeText(AddSubCategoryActivity.this, categoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });
    }
}