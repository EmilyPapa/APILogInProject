package com.emily.androidproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


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
    Button Return;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //elements declarations
        UsernameField = findViewById(R.id.username_txt);
        PasswordField = findViewById(R.id.password_txt);
        UserInfo = findViewById(R.id.user_info);
        PasswordInfo = findViewById(R.id.password_info);
        ViewPassword = findViewById(R.id.viewPassword);
        Connect = findViewById(R.id.connect);
        Return = findViewById(R.id.return_btn);



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

        //Log In with validations
        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                     //Check Credentials
                     validateUserID();
                     validatePassword();

                    //proceed to login
                    try {
                        login();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }



            }
        });

    }




    //UserId Validator
    private boolean validateUserID(){

        String userInput = UsernameField.getText().toString().trim();
        if(userInput.isEmpty()){

            UsernameField.setError("Field can't be empty!");
            return false;

        }
        else if(!RegExValidators.USER.matcher(userInput).matches()){

            UsernameField.setError("Invalid UserID type!");
            return false;
        }else{
            UsernameField.setError(null);
            return true;
        }
    }
    //Password validator
    private boolean validatePassword(){

        String userInput = PasswordField.getText().toString().trim();
        if(userInput.isEmpty()){

            PasswordField.setError("Field can't be empty!");
            return false;

        }
        else if(!RegExValidators.PASSWORD.matcher(userInput).matches()){

            PasswordField.setError("Invalid Password type!");
            return false;
        }else{
            PasswordField.setError(null);
            return true;
        }
    }



    //dialog window for pop up message for password
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
    //dialog window for pop up message for username
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
    //alert dialog for error if user has set wrong user name and password
    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.error_layout,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)

        );
        builder.setView(view);
        ((TextView)view.findViewById(R.id.mytext)).setText(getResources().getString(R.string.error));
        ((Button)view.findViewById(R.id.return_btn)).setText(getResources().getString(R.string.btn_mesg));
        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.return_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

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




                } else {
                    //show alert dialog if an error happens due to user validation
                     showAlertDialog();


                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Throwable " +t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }




}
