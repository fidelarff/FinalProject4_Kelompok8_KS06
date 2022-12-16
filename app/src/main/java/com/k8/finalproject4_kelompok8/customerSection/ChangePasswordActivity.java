package com.k8.finalproject4_kelompok8.customerSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.api.ApiClient;
import com.k8.finalproject4_kelompok8.userAccount.UserAccountActivity;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;
import com.k8.finalproject4_kelompok8.utils.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText password, confirmpass;
    Button update;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        password=findViewById(R.id.newpass);
        confirmpass =findViewById(R.id.newpassconfirm);
        update=findViewById(R.id.updateBtn);
        back=findViewById(R.id.backArrow);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    updatePassword();
                }
            }
        });
    }

    private void updatePassword() {
        Call<LoginResponse> edit_password= ApiClient.getApiService().edit_password(
                SharedPreferenceUtils.getStringPreference(this, Constants.API_KEY),password.getText().toString());
        edit_password.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        Toast.makeText(ChangePasswordActivity.this, "Password successfully changed.", Toast.LENGTH_LONG).show();
                        SharedPreferenceUtils.setBooleanPreference(ChangePasswordActivity.this, Constants.IS_LOGGED_IN_KEY, false);
                        SharedPreferenceUtils.setStringPreference(ChangePasswordActivity.this, Constants.API_KEY, "");
                        Intent intent=new Intent(ChangePasswordActivity.this, UserAccountActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private boolean validate() {
        String pass = password.getText().toString();
        String confirm = confirmpass.getText().toString();
        if (pass.isEmpty() || confirm.isEmpty() ) {
            Toast.makeText(ChangePasswordActivity.this, "Fields cannot be left empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (pass.length() >=6 ) {
                if(pass.equals(confirm)){
                    return true;
                }else{
                    confirmpass.setError("Password did not match!");
                    return  false;
                }
            }else{
                password.setError("Password should be greater than 5!!");
                return false;
            }

        }
    }
}