package com.example.SignInSignUp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.SignInSignUp.fragment.SignInFragment;
import com.example.SignInSignUp.fragment.SignUpFragment;

public class MVPAdapter extends FragmentStateAdapter {

    public MVPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new SignUpFragment();
            case 0:
            default:
                return new SignInFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
