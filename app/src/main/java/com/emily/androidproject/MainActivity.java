package com.emily.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText UsernameField;
    EditText PasswordField;
    Button UserInfo;
    Button PasswordInfo;
    Button ViewPassword;
    Button Connect;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsernameField = findViewById(R.id.username_txt);
        PasswordField = findViewById(R.id.password_txt);
        UserInfo = findViewById(R.id.user_info);
        PasswordInfo = findViewById(R.id.password_info);
        ViewPassword = findViewById(R.id.viewPassword);
        Connect = findViewById(R.id.connect);


        //view password when προβολή button is being pressed
        ViewPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        PasswordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        PasswordField.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }
        });


        //show the dialog when i in Password field is pressed
        PasswordInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPasswordDialog();

            }
        });

        //show the dialog when i in UserId field is pressed
        UserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showUserIDDialog();

            }
        });

        //Log In
        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(UsernameField.getText().toString()) || TextUtils.isEmpty(PasswordField.getText().toString())){

                    Toast.makeText(MainActivity.this,"Username/Password required!", Toast.LENGTH_LONG).show();
                }else{
                    //proceed to login
                    try {
                        login();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

    }

    //dialog window for pop up message
    private void showPasswordDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.dim_layout,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView)view.findViewById(R.id.mytext)).setText(getResources().getString(R.string.passinfo));

        AlertDialog alertDialog = builder.create();

        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();

    }
    //dialog window for pop up message
    private void showUserIDDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.dim_layout,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView)view.findViewById(R.id.mytext)).setText(getResources().getString(R.string.userinfo));

        AlertDialog alertDialog = builder.create();

        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();

    }


    //login method
    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(UsernameField.getText().toString());
        loginRequest.setPassword(PasswordField.getText().toString());
        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){

                    Toast.makeText(MainActivity.this,"Login Successful!", Toast.LENGTH_LONG).show();
                    //get to the main page after login
                    startActivity(new Intent(MainActivity.this, MainPage.class));

                }else {
                    Toast.makeText(MainActivity.this,"Login Failed!", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Throwable " +t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }




}