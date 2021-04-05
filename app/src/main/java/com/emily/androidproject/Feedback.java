package com.emily.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Feedback extends AppCompatActivity {

   EditText feedbackText;
   Button submitButton;
   Button addPhotoButton;
   Button backbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //elements declarations
       feedbackText = findViewById(R.id.feedback_txt);
       submitButton = findViewById(R.id.submit);
       addPhotoButton = findViewById(R.id.add_photo);
       backbutton = findViewById(R.id.back_btn2);


       //back to Settings page button
       backbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(Feedback.this, Settings.class));
           }
       });





    }
}