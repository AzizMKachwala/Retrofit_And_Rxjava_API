package com.example.SignInSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.SignInSignUp.fragment.SignInFragment;
import com.example.SignInSignUp.fragment.SignUpFragment;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SignInSignUpActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    SignInFragment signInFragment;
    SignUpFragment signUpFragment;
    TextView txtHeading1, txtHeading2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);

        tabLayout = findViewById(R.id.tabMode);
        viewPager2 = findViewById(R.id.viewPager);
        txtHeading1 = findViewById(R.id.txtHeading1);
        txtHeading2 = findViewById(R.id.txtHeading2);

        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

        viewPager2.setAdapter(new MVPAdapter(SignInSignUpActivity.this));

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Sign In");
                }
                else if (position == 1){
                    tab.setText("Sign Up");
                }
            }
        }).attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 0){
                    txtHeading1.setText("Welcome Back");
                    txtHeading2.setText("Log in to your account to get an update");
                }
                else {
                    txtHeading1.setText("Hello");
                    txtHeading2.setText("Register to create a new account");
                }
            }
        });

    }
}
