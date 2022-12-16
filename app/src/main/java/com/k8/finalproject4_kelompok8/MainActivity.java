package com.k8.finalproject4_kelompok8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.k8.finalproject4_kelompok8.customerSection.HomeActivity;
import com.k8.finalproject4_kelompok8.userAccount.UserAccountActivity;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent= new Intent(this, UserAccountActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean is_logged=
                        SharedPreferenceUtils.getBooleanPreference(MainActivity.this, Constants.IS_LOGGED_IN_KEY,false);

                if(is_logged){

                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                }
                else{
                    startActivity(intent);
                }
                finish();
            }
        },1000);
    }
}