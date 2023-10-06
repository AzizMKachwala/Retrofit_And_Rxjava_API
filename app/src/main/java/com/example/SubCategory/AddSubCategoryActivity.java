package com.example.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.retrofitandrxjavaapidemo.R;

public class AddSubCategoryActivity extends AppCompatActivity {

    EditText etvSubCategoryName;
    AppCompatSpinner selectCategorySpinner;
    Button btnSubAdd;
    RestCall restCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_category);

        etvSubCategoryName = findViewById(R.id.etvSubCategoryName);
        selectCategorySpinner = findViewById(R.id.selectCategorySpinner);
        btnSubAdd = findViewById(R.id.btnSubAdd);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        btnSubAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etvSubCategoryName.getText().toString().trim().equalsIgnoreCase("")) {
                    etvSubCategoryName.setError("Enter Sub-Category Name");
                    etvSubCategoryName.requestFocus();
                }
            }
        });
    }
}