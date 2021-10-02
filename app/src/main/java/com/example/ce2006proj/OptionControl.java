package com.example.ce2006proj;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.snackbar.Snackbar;

public class OptionControl
{
    static Context option_context;
    public static void ModifyParticulars()
    {
        // need to call the modify particulars page
        Intent i = new Intent(option_context, ModifyParticulars.class);
        option_context.startActivity(i);

    }
    public static void FindSchool()
    {
        Intent i = new Intent(option_context, FindSchool.class);
        option_context.startActivity(i);
    }

    public static void AdvanceMode()
    {
        Intent i = new Intent(option_context, MapsActivity.class);
        option_context.startActivity(i);
    }


}
