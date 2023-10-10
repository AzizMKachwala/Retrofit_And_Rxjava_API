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
import com.example.retrofitandrxjavaapidemo.R;

public class SignInFragment extends Fragment {

    EditText etvEmail, etvPassword;
    Button btnSignIn,btnResetPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        etvEmail = view.findViewById(R.id.etvEmail);
        etvPassword = view.findViewById(R.id.etvPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnResetPassword = view.findViewById(R.id.btnResetPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(etvEmail.getText().toString().trim() != null) {
//                    etvEmail.setError("Enter Email");
//                    etvEmail.requestFocus();
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