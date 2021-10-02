package com.example.ce2006proj;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserDatabase
{
    static User user ;
    static String FirebaseKey;
    static DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");
    public static void RegisterUser(User user)
    {

        // first need to check if the user is found in the system
        // if the user is found then we should display an error message
        ref= ref.push();
        ref.child("username").setValue(user.getUsername());
        ref.child("password").setValue(user.getPassword());
        ref.child("Children").setValue(user.getChildren_List());

    }

    public static void FetchUser(FirebaseUserCallback callback, String Username) {
        Query query = ref.orderByChild("username").equalTo(Username);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User name = snapshot.getValue(User.class);
                        FirebaseKey = snapshot.getKey();
                        Log.e("name in loop", name.getUsername());
                        callback.onResponse(name);
                        break;
                    }
                }
                else
                {
                    User name=  null;
                    callback.onResponse(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public static void UpdateUser(User user) {
        HashMap hashMap = new HashMap();
        hashMap.put("username",user.getUsername());
        hashMap.put("password",user.getPassword());
        hashMap.put("Children",user.getChildren_List());
        ref.child(FirebaseKey).updateChildren(hashMap);

    }


}



