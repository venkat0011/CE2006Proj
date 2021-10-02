package com.example.ce2006proj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ModifyParticulars extends AppCompatActivity {
    Student_Adapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_particulars);
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.viewStudents);
        // Create adapter passing in the sample user data
         adapter = new Student_Adapter(this,ModifyParticulars_control.GetStudentList(),"modify");
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        TextView textView = findViewById(R.id.display_student);
        textView.setText(ModifyParticulars_control.CheckChildrenList());
    }

    public void AddChild(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ADD Child");
        // set the custom layout
        View customLayout = getLayoutInflater().inflate(R.layout.add_student, null);
        builder.setView(customLayout);

        Spinner spinner = customLayout.findViewById(R.id.citizenship_status);
        String[] items = new String[]{"1","2","3"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter1);
        final String[] citizenship = {""};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                citizenship[0] = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        // add a button
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                EditText name = customLayout.findViewById(R.id.child_name);
                EditText location = customLayout.findViewById(R.id.location);
                SeekBar seekBar = customLayout.findViewById(R.id.seekBar);
                String error_message = ModifyParticulars_control.AddStudent(name.getText().toString(),location.getText().toString()
                ,citizenship[0],seekBar.getProgress());
                Snackbar.make(view, error_message, Snackbar.LENGTH_LONG)
                        .show();
                adapter.notifyDataSetChanged();





//                sendDialogDataToActivity(editText.getText().toString());
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // add a function to modfiy the user particulars

    // add a function to remove a student also

//


}
