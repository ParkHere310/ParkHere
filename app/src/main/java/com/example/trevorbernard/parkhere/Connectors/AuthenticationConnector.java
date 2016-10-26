package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.User.Rating;
import com.example.trevorbernard.parkhere.User.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metzm on 10/16/2016.
 */

public class AuthenticationConnector {
    public static User register(FirebaseUser user) {
        User newUser = new User();
        newUser.setFirstName("Donald");
        newUser.setLastName("Trump");
        newUser.setEmail(user.getEmail());
        newUser.setIsVerified(true);
        newUser.setUID(user.getUid());
        newUser.setPhoneNumber("5555555555");
        List<String> reviews = new ArrayList<String>();
        reviews.add("asdf");
        reviews.add("fds");
        newUser.setReviews(reviews);
        newUser.setRating(new Rating());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(user.getUid()).setValue(newUser);
        return newUser;
    }

    public static User authenticate(FirebaseUser user) {
        User newUser = new User();

        final ArrayList<User> list = new ArrayList<User>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Query queryRef = mDatabase.orderByChild("uid").equalTo(user.getUid()); //test email address
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User newUser = postSnapshot.getValue(User.class);
                    list.add(newUser);
                }
                //list.add(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(list.isEmpty()) return null;
        return list.get(0);
    }
}
