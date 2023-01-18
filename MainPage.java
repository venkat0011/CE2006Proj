package com.example.ce2006proj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }


    public void ModifyParticulars(View view)
    {
        OptionControl.option_context = MainPage.this;
        OptionControl.ModifyParticulars();

    }
    public void FindSchool(View view)
    {
        OptionControl.option_context = MainPage.this;
        OptionControl.FindSchool();
    }
}