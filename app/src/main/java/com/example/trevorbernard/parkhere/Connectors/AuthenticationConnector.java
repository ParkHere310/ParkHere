package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.User.User;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by metzm on 10/16/2016.
 */

public class AuthenticationConnector {
    public static User register(FirebaseUser user) {
        User newUser = new User();
        return newUser;
    }

    public static User authenticate(FirebaseUser user) {
        User newUser = new User();
        return newUser;
    }
}
