package com.example.ce2006proj;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SchoolDatabase
{
    static DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Schools")
            .child("result").child("records");
    public static void FetchSchools(SchoolCallBack schoolCallBack, String centre_code)
    {
        Query query = ref.orderByChild("centre_code").equalTo(centre_code);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Schools school = dataSnapshot.getValue(Schools.class);
                        schoolCallBack.onCallback(school);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }


    // overload the method to include the list of filters
}
