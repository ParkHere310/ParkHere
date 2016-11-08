package com.example.trevorbernard.parkhere;

import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;

import com.example.trevorbernard.parkhere.Client.RegisterActivity;
import com.example.trevorbernard.parkhere.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

/**
 * Created by zilongxiao on 11/7/16.
 */

public class VerificationPictureTest {
    Query queryRef;

    @Before
    public void setUp() {
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword("test44@test.com", "test1!").addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                        }
                    }
            );
        }
    }

    //
    @Test
    public void Verification() throws Exception{
        final ArrayList<User> users = new ArrayList<User>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        queryRef = mDatabase;
        final ArrayList<Boolean> done = new ArrayList<Boolean>();
        done.add(false);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                User user = postSnapshot.getValue(User.class);
                                if(user.getRating() != null) users.add(user);
                            }
                            done.add(0, true);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (Exception e) {
                }
            }
        });
        thread.start();
        while(done.get(0) == false) {
            Thread.sleep(10);
        }

        for(User user : users) {
            assertTrue("User has a profile picture", user.getIsVerified());
        }
    }
}
