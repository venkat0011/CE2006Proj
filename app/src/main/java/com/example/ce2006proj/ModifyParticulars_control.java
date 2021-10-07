package com.example.ce2006proj;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ModifyParticulars_control
{
    private static Context context;
    private static Context ModifyParticularsContext;
        public static String CheckChildrenList()
        {
            if(UserDatabase.user.getChildren_List().isEmpty())
            {
                return "No children found please press the ADD button to add\n" +
                        "to add your child's information";
            }
            else
            {
                return "Choose the particulars that you wish to modify";
            }
        }

        public static boolean CheckStudentinfo(String name, String location, String age, String citizenship) {
            // postal code
//            Log.e("nam,citizen",name+" "+location+":"+ citizenship);
//            Log.e("a", String.valueOf(age));
            // checking postal code
            // postal codes are from 01 t0 82 excluding 74
            // this function is mainly to add a studnent in
            try
            {
                int location_int = Integer.parseInt(location.substring(0,2));
                if(location_int>=1 && location_int<=82 && location_int!=74 && location.length()==6)
                {
                    Log.e("asd", String.valueOf(location_int)+ " "+ location.length());
                    if(name.isEmpty())
                    {
                        return false;
                    }
                    else
                    {
                        return true;
                    }

                }
                else
                {
                    Log.e("ads","it entered the else block in the try");
                    return false;
                }
            }catch (Exception e)
            {
                Log.e("adsf","it entered teh catch block");
                return false;
            }


        }

        public static String UpdateStudent(String name, String location, String citizen,String age, Student oldStudent)
        {
            if(CheckStudentinfo(name,location,age,citizen))
            {
                // all the info is correct
                // remove the old student from the list
                ArrayList<Student> students = UserDatabase.user.getChildren_List();
                students.remove(oldStudent);
                Student student = new Student(name,location,age,citizen);
                students.add(student);
                UserDatabase.user.setChildren_List(students);
                UserDatabase.UpdateUser(UserDatabase.user);

                return "Child Updated Successfully";
            }
            else
            {
                return "Please enter a valid Postal code/ Name";
            }
        }

        public static String AddStudent(String name, String location, String citizen,String age)
        {
            if(CheckStudentinfo(name,location,age,citizen))
            {
                // all the info is correct
                Student student = new Student(name, location, age+2, citizen);
                ArrayList<Student> students = UserDatabase.user.getChildren_List();
                if(students.contains(student))
                {
                    return "This child already exists";
                }
                else
                {
                    students.add(student);
                    UserDatabase.user.setChildren_List(students);
                    UserDatabase.UpdateUser(UserDatabase.user);
                    return "Child Added Successfully";
                }

            }
            else
            {
                return "Please enter a valid Postal code/ Name";
            }

        }


        public static ArrayList<Student> GetStudentList()
        {
            return UserDatabase.user.getChildren_List();
        }





}
