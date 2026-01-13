package com.shoewala.app.utils;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseUtil {

    private static FirebaseAuth auth;

    public static FirebaseAuth getAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
