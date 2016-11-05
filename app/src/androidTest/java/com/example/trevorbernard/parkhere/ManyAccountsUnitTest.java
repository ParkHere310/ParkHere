package com.example.trevorbernard.parkhere;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by metzm on 11/5/2016.
 */

public class ManyAccountsUnitTest {

    private Query queryRef,queryRef2;

    @Before
    public void setUp() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
        }
    }

    @Test
    public void manyUsersTest() throws Exception {
        for(int i = 0; i < 10000; i++) {
            final String un = "unitTest" + i + "@test.com";
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(un, "test1!").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseAuth.getInstance().signInAnonymously();
                            assertNotNull(FirebaseAuth.getInstance());
                        }
                    });
                }
            });
            thread.start();
            FirebaseAuth.getInstance().signOut();
        }
    }
}
