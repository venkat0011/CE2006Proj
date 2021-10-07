//package com.example.ce2006proj;
//
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//
//public class ServiceDatabase {
//    static DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Services")
//                                    .child("result").child("records");
//
//    // this is the first instance where we will call the fuinction. This one will not have a price range
//    // under the filter tab then we will include a slider to show price range
//    public static void FetchServices(ArrayList postal_codes)
//    {
//        Query query = ref.orderByChild("type_of_citizenship");
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            ArrayList<Student> students = new ArrayList<>();
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//
//        query.addListenerForSingleValueEvent(valueEventListener);
//    }
//}
