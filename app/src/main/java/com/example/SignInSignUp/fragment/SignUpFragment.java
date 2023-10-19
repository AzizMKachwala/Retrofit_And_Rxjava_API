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

import com.example.SignInSignUp.SignInSignUpActivity;
import com.example.AppUtils.Tools;
import com.example.AppUtils.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.UserResponse;
import com.example.retrofitandrxjavaapidemo.R;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SignUpFragment extends Fragment {

    EditText etvFirstName, etvLastName, etvEmail, etvPassword;
    Button btnSignUp, btnResetPassword;
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

//        if(Tools.isValidEmail(etvEmail.getText().toString().trim())){
//            Toast.makeText(getContext(), "Valid Email", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(getContext(), "InValid Email", Toast.LENGTH_SHORT).show();
//        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etvFirstName.getText().toString().trim().isEmpty() || etvLastName.getText().toString().trim().isEmpty()
                        || !Tools.isValidEmail(etvEmail.getText().toString().trim())
                        || !isValidPassword(etvPassword.getText().toString().trim())) {
                    Toast.makeText(getContext(), "Enter Valid Details", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Login to Start", Toast.LENGTH_SHORT).show();
                    addUser();
                }
            }

            private boolean isValidPassword(String password) {
                return password.length() >= 7 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")
                        && password.matches(".*\\d.*") && password.matches(".*[@#$%^&+=].*");
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

    public void addUser() {
        restCall.AddUser("AddUser", etvFirstName.getText().toString().trim(), etvLastName.getText().toString().trim(),
                        etvEmail.getText().toString().trim(), etvPassword.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                    etvFirstName.setText("");
                                    etvLastName.setText("");
                                    etvEmail.setText("");
                                    etvPassword.setText("");

                                    startActivity(new Intent(getContext(), SignInSignUpActivity.class));
                                } else {
                                    Toast.makeText(getContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }
}