package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.User.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by metzm on 10/16/2016.
 */

public class AuthenticationConnector {
    public static User register(FirebaseUser user) {
        User newUser = new User();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(user.getUid()).setValue(newUser);
        return newUser;
    }

    public static User authenticate(FirebaseUser user) {
        User newUser = new User();

        final ArrayList<User> list = new ArrayList<User>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query queryRef = mDatabase.orderByChild("Users").equalTo(user.getUid()); //test email address
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User newUser = postSnapshot.getValue(User.class);
                    list.add(newUser);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(list.isEmpty()) return null;
        return list.get(0);
    }
}
