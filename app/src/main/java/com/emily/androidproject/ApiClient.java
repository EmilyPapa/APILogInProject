package com.emily.androidproject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //this is the main client and Retrofit builder
    //Builder will be accessed every time we make API calls
    //Converts Json objects to Java Objects with GsonConverterFactory

    private static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://3nt-demo-backend.azurewebsites.net/")
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    //accessing the builder
    public static UserService getUserService(){
        UserService userService = getRetrofit().create(UserService.class);

        return userService;
    }


}
