package com.example.ce2006proj;

import android.app.Service;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.Value;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceDatabase {
    static DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Services")
            .child("result").child("records");

    // this is the first instance where we will call the fuinction. This one will not have a price range
    // under the filter tab then we  will include a slider to show price range
    public static void FetchServices(ServiceCallBack serviceCallBack, String centrecode) {
        Query query = ref.orderByChild("centre_code").equalTo(centrecode);
        ArrayList<Services> services = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.e("as", "snapshot exist for services for " + centrecode);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Services service = dataSnapshot.getValue(Services.class);
                        services.add(service);

                    }
                    //call the interface to send this list to the control class
                    serviceCallBack.onResponse(services);
                } else {
                    serviceCallBack.onResponse(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);

    }


    public static void FetchServices(ServiceCallBack serviceCallBack) {
        String age = FindSchool_Control.AgeGroup(StudentDatabase.student.getAge());
        String citizenship = FindSchool_Control.Citizenship(StudentDatabase.student.getCitizenship());
        Query query = ref.orderByChild("levels_offered").equalTo(age);
        ArrayList<Services> services = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren())
                    {

                        Services service = dataSnapshot.getValue(Services.class);
                        if(service.getType_of_citizenship().equals(citizenship))
                        {
                            services.add(service);
                        }

                    }
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
