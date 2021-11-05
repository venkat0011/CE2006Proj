package com.example.ce2006proj;

import android.service.autofill.DateTransformation;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
        DatabaseReference ref1= ref.push();
        ref1.child("username").setValue(user.getUsername());
        ref1.child("password").setValue(user.getPassword());
        ref1.child("Children").setValue(user.getChildren_List());
        ref1.child("Schools").setValue(user.getFav_school());
        Log.e("asd","register function called");

    }

    public static void FetchUser(FirebaseUserCallback firebaseUserCallback,String username)
    {
        Query query = ref.orderByChild("username").equalTo(username);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        User user1 = dataSnapshot.getValue(User.class);
                        FirebaseKey = dataSnapshot.getKey();
                        Log.e("key",FirebaseKey);
                        firebaseUserCallback.onResponse(user1);
                        break;

                    }
                }
                else
                {
                    User user1=  null;
                    firebaseUserCallback.onResponse(user1);
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
        hashMap.put("Schools",user.getFav_school());
        ref.child(FirebaseKey).updateChildren(hashMap);

    }

    public static void FetchFavSchools()
    {
        Query query = UserDatabase.ref.child(FirebaseKey).child("Schools");
        ValueEventListener valueEventListener = new ValueEventListener() {
            ArrayList<Schools> schools  = new ArrayList<>();
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Schools school = snapshot.getValue(Schools.class);
                        schools.add(school);
                    }
                    UserDatabase.user.setFav_school(schools);
                }
                else
                {
                    UserDatabase.user.setFav_school(schools);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
    }
}





