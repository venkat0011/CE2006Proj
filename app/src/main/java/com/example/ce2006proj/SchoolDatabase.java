package com.example.ce2006proj;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SchoolDatabase
{
    static DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("School")
            .child("result").child("records");
    static ArrayList<Schools>schoolsArrayList = new ArrayList<>();
    public static void FetchSchools(SchoolCallBack schoolCallBack)
    {
        Query query = ref.orderByChild("centre_code");
        // get the district list
        List<String> district_postals = FindSchool_Control.FindDistrict();
        ArrayList<Schools> schoolsArrayList = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Log.e("fa","Fetching schools");

                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Schools school = dataSnapshot.getValue(Schools.class);
                        if(district_postals.contains(school.getPostal_code().substring(0,2)))
                        {
                            schoolsArrayList.add(school);
                        }

                    }
                    schoolCallBack.onCallback(schoolsArrayList);

                    }
                else
                {
                    Log.e("It","snap shot doesnt exist");
                }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public static void FetchSchools(SchoolCallBack schoolCallBack, String centre_code)
    {
        schoolsArrayList.clear();
        Query query = ref.orderByChild("centre_code").equalTo(centre_code);
        ArrayList<Schools>schools = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        Schools school = dataSnapshot.getValue(Schools.class);
                        schools.add(school);
                        break;

                    }
                    schoolCallBack.onCallback(schools);
                }
                else
                {
                    schoolCallBack.onCallback(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }


}
