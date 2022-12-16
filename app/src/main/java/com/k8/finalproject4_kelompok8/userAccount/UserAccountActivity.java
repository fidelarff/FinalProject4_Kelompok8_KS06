package com.k8.finalproject4_kelompok8.userAccount;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.userAccount.fragments.LoginFragment;

public class UserAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        LoginFragment loginFragment= new LoginFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,loginFragment).commit();
    }
}