package com.k8.finalproject4_kelompok8.userAccount.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.api.ApiClient;
import com.k8.finalproject4_kelompok8.customerSection.HomeActivity;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;
import com.k8.finalproject4_kelompok8.utils.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    TextView signUpTV;
    Button loginBtn;
    EditText usernameET,passwordET;
    ProgressBar loginProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUpTV=view.findViewById(R.id.sigupTV);
        loginBtn=view.findViewById(R.id.loginBtn);
        usernameET=view.findViewById(R.id.emailET);
        passwordET=view.findViewById(R.id.passwordET);
        loginProgress=view.findViewById(R.id.loginProgress);

        onClickListener();
    }

    private void onClickListener() {
        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment= new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,registerFragment).commit();

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    toggleLoading(true);
                    callApiToLogin(usernameET.getText().toString(), passwordET.getText().toString());
                }
            }
        });
    }

    private void toggleLoading(boolean isLoading) {
        if (isLoading)
            loginProgress.setVisibility(View.VISIBLE);
        else
            loginProgress.setVisibility(View.GONE);
    }

    private void callApiToLogin(String email, String password) {
        Call<LoginResponse> loginResponseCall= ApiClient.getApiService().login(email,password);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (!loginResponse.getError()) {

                        SharedPreferenceUtils.setStringPreference(getContext(), Constants.API_KEY, loginResponse.getApiKey());
                        SharedPreferenceUtils.setBooleanPreference(getContext(), Constants.IS_LOGGED_IN_KEY, true);

                        SharedPreferenceUtils.setStringPreference(getContext(), Constants.ID, loginResponse.getUserId().toString());
                        SharedPreferenceUtils.setStringPreference(getContext(), Constants.NAME, loginResponse.getFullName());
                        SharedPreferenceUtils.setStringPreference(getContext(), Constants.EMAIL, loginResponse.getEmail());
                        SharedPreferenceUtils.setStringPreference(getContext(), Constants.CONTACT, loginResponse.getContactNo());
                        SharedPreferenceUtils.setStringPreference(getContext(), Constants.PROFILE_PIC, loginResponse.getProfilePic());


                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        Toast.makeText(getActivity(), "You are Successfully logged in", Toast.LENGTH_LONG).show();
                        startActivity(intent);


                    }
                    else {
                        Snackbar.make(getView(),"Wrong details! Please input correct details!",Snackbar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                toggleLoading(false);
            }
        });
    }

    private Boolean validate(){
        String username=usernameET.getText().toString();
        String password= passwordET.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            //Toast.makeText(getActivity(), "Fields should not be left empty!", Toast.LENGTH_SHORT).show();
            Snackbar.make(getView(),"Fields should not be left empty!",Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}