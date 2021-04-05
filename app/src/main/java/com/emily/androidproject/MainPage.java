package com.emily.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainPage extends AppCompatActivity {
    Button settings;
    Button play;
    Button books;
    Button misc;
    Button link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //elements declarations
        play = findViewById(R.id.play_btn);
        books = findViewById(R.id.books_btn);
        misc = findViewById(R.id.misc_btn);
        link = findViewById(R.id.link_btn);
        settings = findViewById(R.id.settings_btn);

        //settings button method on click
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, Settings.class));
            }
        });

        //failed attempt for get method to get books for api.
        // I am missing authorization token which I wasn't able to fetch from main activity
        // I only receive "ERROR" toast in my app

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://3nt-demo-backend.azurewebsites.net/")
                .client(okHttpClient)
                .build();

        BookService bookService = retrofit.create(BookService.class);
        Call<List<Book>> call = bookService.getBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                if (response.isSuccessful()){

                    Toast.makeText(MainPage.this, "SUCCESS!", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainPage.this, "ERROR!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });


    }
}