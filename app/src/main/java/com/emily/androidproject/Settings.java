package com.emily.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {
    Button feedback;
    Button language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //elements declarations
        language = findViewById(R.id.lang_btn);

        //language button method on click
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Settings.this, Language.class));

            }
        });

        feedback = findViewById(R.id.feed_btn);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Feedback.class));
            }
        });
    }
}