package com.k8.finalproject4_kelompok8.api;

import com.google.gson.GsonBuilder;
import com.k8.finalproject4_kelompok8.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static ApiServices getApiService(){
        HttpLoggingInterceptor interceptorObj = new HttpLoggingInterceptor();
        interceptorObj.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient clientObj = new OkHttpClient.Builder().addInterceptor(interceptorObj).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder().create()))
                .client(clientObj).build();

        return retrofit.create(ApiServices.class);
    }
}
