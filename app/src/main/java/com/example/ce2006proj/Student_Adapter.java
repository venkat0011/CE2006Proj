package com.example.ce2006proj;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class Student_Adapter extends RecyclerView.Adapter<Student_Adapter.ViewHolder> {

    private Context mCtx;
    private List<Student> students;
    private String option = "";

    public Student_Adapter(Context mCtx, List<Student> students,String option) {
        this.mCtx = mCtx;
        this.students = students;
        this.option = option;
    }



    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.student_display, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = UserDatabase.user.getChildren_List().get(position);
        holder.textViewName.setText(student.getName());
        holder.textViewCitizen.setText(student.getCitizenship());
        holder.textViewPostal.setText(student.getLocation());
        holder.textViewAge.setText(String.valueOf(student.getAge()));
    }


    @Override
    public int getItemCount() {
        return students.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewPostal, textViewAge, textViewCitizen;

        public ViewHolder( View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.Student_name);
           textViewAge = itemView.findViewById(R.id.student_age);
            textViewPostal =itemView.findViewById(R.id.student_postal);
            textViewCitizen = itemView.findViewById(R.id.student_citizen);
            if(option=="modify")
            {
                itemView.findViewById(R.id.modify_student).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                        builder.setTitle("Modify Child");
                        // set the custom layout
                        View customLayout = LayoutInflater.from(mCtx).inflate(R.layout.add_student, null);
                        builder.setView(customLayout);

                        // intialise all the textboxes with the student name

                        EditText name = customLayout.findViewById(R.id.child_name);
                        EditText location = customLayout.findViewById(R.id.location);
                        Student oldstudent = ModifyParticulars_control.GetStudentList().get(getAdapterPosition());

                        name.setText(oldstudent.getName());
                        location.setText(oldstudent.getLocation());

                        Spinner spinner = customLayout.findViewById(R.id.citizenship_status);
                        List items = Arrays.asList(new String[] {"Citizen","PR","Other"});
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(mCtx, android.R.layout.simple_spinner_dropdown_item, items);
                        spinner.setAdapter(adapter1);
                        int index = items.indexOf(oldstudent.getCitizenship());
                        spinner.setSelection(index);

                        Spinner age_spinner = customLayout.findViewById(R.id.age_spinner);
                        List age_group = Arrays.asList(new String[]{ "2 to 18 mths", "18 mths to 2 yrs old", "3 yrs old", "4 yrs old", "5 yrs old", "6 yrs old"});
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(mCtx, android.R.layout.simple_spinner_dropdown_item, age_group);
                        age_spinner.setAdapter(adapter2);
                        int index1 = age_group.indexOf(oldstudent.getAge());
                        age_spinner.setSelection(index1);



                        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // send data from the AlertDialog to the Activity
                                String error_message = ModifyParticulars_control.UpdateStudent(name.getText().toString(),location.getText().toString()
                                        ,spinner.getSelectedItem().toString(),age_spinner.getSelectedItem().toString(),oldstudent);
                                Snackbar.make(view, error_message, Snackbar.LENGTH_LONG)
                                        .show();
                                Log.e("as",spinner.getSelectedItem().toString());
                                notifyDataSetChanged();

                            }
                        });
                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();




                        Log.e("but",UserDatabase.user.getChildren_List().get(getAdapterPosition()).getName());
                    }
                });
            }
            else
            {
                itemView.findViewById(R.id.modify_student).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Student selectedStudent = ModifyParticulars_control.GetStudentList().get(getAdapterPosition());
                        // call the other function here
                        FindSchool_Control.setStudent(selectedStudent);
                        OptionControl.AdvanceMode();
                        Log.e("Th","This way works");
                    }
                });
            }



        }

    }
}
