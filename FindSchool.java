package com.example.ce2006proj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

public class FindSchool extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_school);
    }

    public void SimpleMode(View view)
    {

    }

    public void AdvanceMode(View view)
    {
        // create the pop up screen to select the student
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select the Child you wish to find a school for");
        // set the custom layout
        View customLayout = getLayoutInflater().inflate(R.layout.student_recycler, null);
        RecyclerView rvContacts = (RecyclerView) customLayout.findViewById(R.id.selectStudent);
        // Create adapter passing in the sample user data
        Student_Adapter adapter = new Student_Adapter(this,ModifyParticulars_control.GetStudentList(),"select");
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        builder.setView(customLayout);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
