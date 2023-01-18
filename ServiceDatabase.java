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
//import java.util.ArrayList;
//
//public class ServiceDatabase {
//    static DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Services")
//                                    .child("result").child("records");
//    public static void FetchServices()
//    {
//        Query query = ref.orderByChild("type_of_citizenship").;
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            ArrayList<Student> students = new ArrayList<>();
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Student student = snapshot.getValue(Student.class);
//                        students.add(student);
//                        Log.e("name in loop", student.getName());
//                    }
//                    callback.onResponse(students);
//                }
//                else
//                {
//                    UserDatabase.user.setChildren_List(students);
//                }
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
