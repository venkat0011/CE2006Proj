package com.example.ce2006proj;

import android.app.Service;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceDatabase {
    static DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Services")
                                    .child("result").child("records");
    static ArrayList<Services> services = new ArrayList<>();

    // this is the first instance where we will call the fuinction. This one will not have a price range
    // under the filter tab then we  will include a slider to show price range
    public static void FetchServices(ServiceCallBack serviceCallBack, String agegroup,String citizenship)
    {
        Query query = ref.orderByChild("levels_offered").equalTo(agegroup);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        Services service = dataSnapshot.getValue(Services.class);
                        if(service.getCitizenship().equals(citizenship))
                        {
                            services.add(service);
                        }

                    }
                    //call the interface to send this list to the control class
                    serviceCallBack.onResponse(services);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);

        }

}
