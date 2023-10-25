package com.example.SignInSignUp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    Tools tools;
    ImageView imgPasswordCloseEye;
    String password = "Hide";

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
        imgPasswordCloseEye = view.findViewById(R.id.imgPasswordCloseEye);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools = new Tools(getContext());

//        if(Tools.isValidEmail(etvEmail.getText().toString().trim())){
//            Toast.makeText(getContext(), "Valid Email", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(getContext(), "InValid Email", Toast.LENGTH_SHORT).show();
//        }

        imgPasswordCloseEye.setOnClickListener(v -> {

            if (password.equals("Hide")) {
                password = "Show";
                etvPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                etvPassword.setSelection(etvPassword.length());
                imgPasswordCloseEye.setImageResource(R.drawable.ceye);
            } else {
                password = "Hide";
                etvPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etvPassword.setSelection(etvPassword.length());
                imgPasswordCloseEye.setImageResource(R.drawable.baseline_eye_24);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etvFirstName.getText().toString().trim().isEmpty()){
                    etvFirstName.setError("Enter First Name");
                    etvFirstName.requestFocus();
                }
                else if (etvLastName.getText().toString().trim().isEmpty()){
                    etvLastName.setError("Enter Last Name");
                    etvLastName.requestFocus();
                }
                else if (!Tools.isValidEmail(etvEmail.getText().toString().trim())){
                    etvEmail.setError("Enter Valid Email");
                    Toast.makeText(getContext(), "Email Address must contain @ and .com in it", Toast.LENGTH_SHORT).show();
                    etvEmail.requestFocus();
                }
                else if (!isValidPassword(etvPassword.getText().toString().trim())) {
                    etvPassword.setError("Enter Valid Password");
                    Toast.makeText(getContext(), "Password Must Consist Of Minimum length of 7 with At-least 1 UpperCase," +
                            "1 LowerCase, 1 Number & 1 Special Character", Toast.LENGTH_SHORT).show();
                    etvPassword.requestFocus();
                }
                else {
                    Toast.makeText(getContext(), "Login to Start", Toast.LENGTH_SHORT).show();
                    addUser();
                }

            }

            private boolean isValidPassword(String password) {
                return password.length() >= 7 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")
                        && password.matches(".*\\d.*") && password.matches(".*[@#$%^&+=].*");
            }
        });

        btnResetPassword.setOnClickListener(view1 -> Toast.makeText(getContext(), "RESET MY PASSWORD CLICKED", Toast.LENGTH_SHORT).show());

        return view;
    }

    public void addUser() {
        tools.showLoading();
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
                        getActivity().runOnUiThread(() -> {
                            tools.stopLoading();
                            Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        getActivity().runOnUiThread(() -> {
                            tools.stopLoading();
                            if (userResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                etvFirstName.setText("");
                                etvLastName.setText("");
                                etvEmail.setText("");
                                etvPassword.setText("");

                                startActivity(new Intent(getContext(), SignInSignUpActivity.class));
                            } else {
                                Toast.makeText(getContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}