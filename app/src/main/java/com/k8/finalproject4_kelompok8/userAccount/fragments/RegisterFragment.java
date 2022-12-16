package com.k8.finalproject4_kelompok8.userAccount.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.api.ApiClient;
import com.k8.finalproject4_kelompok8.userAccount.UserAccountActivity;
import com.k8.finalproject4_kelompok8.utils.responses.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
    TextView loginTV;
    EditText nameET,passwordET,confirmPassET,emailET, contactET;
    Button registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameET=view.findViewById(R.id.nameET);
        passwordET=view.findViewById(R.id.passwordET);
        confirmPassET=view.findViewById(R.id.confirmPasswordET);
        emailET=view.findViewById(R.id.emailET);
        contactET=view.findViewById(R.id.contactET);
        loginTV=view.findViewById(R.id.loginTV);
        registerBtn=view.findViewById(R.id.registerBtn);

        onClickListener();
    }

    private void onClickListener() {
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, loginFragment).commit();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    callApiToRegister(nameET.getText().toString(),emailET.getText().toString(),passwordET.getText().toString(),
                            contactET.getText().toString());
                }
            }
        });
    }

    private void callApiToRegister(String name, String email, String password, String contact) {

        Call<RegisterResponse> registerResponseCall= ApiClient.getApiService().register(name,email,password, contact);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), UserAccountActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });

    }

    private boolean validate(){
        String name=nameET.getText().toString();
        String password= passwordET.getText().toString();
        String confirmPass= confirmPassET.getText().toString();
        String email= emailET.getText().toString();
        String contact= contactET.getText().toString();

        if(name.isEmpty()||password.isEmpty()|| confirmPass.isEmpty()||email.isEmpty()||contact.isEmpty()){
            Snackbar.make(getView(),"The required fields are empty",Snackbar.LENGTH_LONG).show();
            return false;
        }else{
            if(confirmPass.equals(password)) {

                if (password.length() >= 6) {

                    if (contact.length() >= 10) {
                        return true;
                    } else {
                        contactET.setError("The phone number must be greater than 9 digits");
                        return false;
                    }

                } else {
                    passwordET.setError("The password must be greater than 5");
                    return false;

                }

            }else{
                confirmPassET.setError("Password did not match");
                return false;
            }

        }
    }
}