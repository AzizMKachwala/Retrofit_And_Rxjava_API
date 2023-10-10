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
import com.example.SubCategory.AddSubCategoryActivity;
import com.example.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.UserResponse;
import com.example.retrofitandrxjavaapidemo.R;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignInFragment extends Fragment {

    EditText etvEmail, etvPassword;
    Button btnSignIn, btnResetPassword;
    RestCall restCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        etvEmail = view.findViewById(R.id.etvEmail);
        etvPassword = view.findViewById(R.id.etvPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnResetPassword = view.findViewById(R.id.btnResetPassword);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
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

    public void loginUser() {
        restCall.LoginUser("LoginUser", etvEmail.getText().toString().trim(), etvPassword.getText().toString().trim())
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
                                    Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getContext(), HomePageActivity.class));
                                } else {
                                    Toast.makeText(getContext(), "Login failed. Check your credentials.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }

}