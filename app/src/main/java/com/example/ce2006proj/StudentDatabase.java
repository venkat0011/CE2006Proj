package com.example.ce2006proj;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDatabase
{
    static Student student;
    public static void FetchChildren() {
        Query query = UserDatabase.ref.child(UserDatabase.FirebaseKey).child("Children").orderByChild("name");
        ValueEventListener valueEventListener = new ValueEventListener() {
            ArrayList<Student>students = new ArrayList<>();
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Student student = snapshot.getValue(Student.class);
                        students.add(student);
                        Log.e("name in loop", student.getName());
                    }
                    UserDatabase.user.setChildren_List(students);
                }
                else
                {
                    UserDatabase.user.setChildren_List(students);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
    }

}
