package com.example.ce2006proj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckUser.context = MainActivity.this;

    }


    public void Register(View view)
    {

        TextView textView = findViewById(R.id.UsernameText);
        String Username = textView.getText().toString();
        TextView textView1 = findViewById(R.id.PasswordText);
        String Password = textView1.getText().toString();

        // mask the password ( to be completed)

        // check if the password and username is valid
         CheckUser.CheckRegisterUser(Username,Password, new UserAutenticationCallback() {
             @Override
             public void onResponse(String text) {
                 setText(text);
             }
         });


    };
    public void Login(View view)
    {
        CheckUser.context = MainActivity.this;
        TextView textView = findViewById(R.id.UsernameText);
        String Username = textView.getText().toString();
        TextView textView1 = findViewById(R.id.PasswordText);
        String Password = textView1.getText().toString();

        CheckUser.CheckLoginUser(Username,Password, new UserAutenticationCallback() {
            @Override
            public void onResponse(String text) {
                setText(text);
            }
        });
    }
    public void  setText(String text)
    {
        Log.e("text at main",text);
        TextView textView2 = findViewById((R.id.textView));
        textView2.setText(text);
    }



}