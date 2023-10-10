package com.example.SignInSignUp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.HomePageActivity;
import com.example.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.retrofitandrxjavaapidemo.R;

public class SignUpFragment extends Fragment {

    EditText etvFirstName,etvLastName,etvEmail,etvPassword;
    Button btnSignUp,btnResetPassword;
    RestCall restCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        etvFirstName = view.findViewById(R.id.etvFirstName);
        etvLastName = view.findViewById(R.id.etvLastName);
        etvEmail = view.findViewById(R.id.etvEmail);
        etvPassword = view.findViewById(R.id.etvPassword);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnResetPassword = view.findViewById(R.id.btnResetPassword);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(etvFirstName.getText().toString().trim() != null) {
//                    etvFirstName.setError("Enter First Name");
//                    etvFirstName.requestFocus();
//                }
//                else {
                    startActivity(new Intent(getContext(), HomePageActivity.class));
//                }
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "RESET MY PASSWORD CLICKED", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}