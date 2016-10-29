package com.example.trevorbernard.parkhere.Connectors;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.trevorbernard.parkhere.User.Rating;
import com.example.trevorbernard.parkhere.User.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by metzm on 10/16/2016.
 */

public class AuthenticationConnector {
    public static User register(FirebaseUser user, String fName, String lName, String pn, Bitmap pic) {

        // Transform profile pic to byte array
        // Your Bitmap.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        final ArrayList<String> downloadUrls = new ArrayList<String>();
        // puts byte array in storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://parkhere-70b24.appspot.com");
        StorageReference imagesRef = storageRef.child("ProfilePics").child(user.getUid());
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                downloadUrls.add(downloadUrl.toString());
            }
        });

        if(downloadUrls.isEmpty()){
            downloadUrls.add("-1");
        }
        User newUser = new User(user,fName,lName, pn, downloadUrls.get(0));
        newUser.setFirstName(fName);
        newUser.setLastName(lName);
        newUser.setEmail(user.getEmail());
        newUser.setIsVerified(true);
        newUser.setUID(user.getUid());
        newUser.setPhoneNumber(pn);
        List<String> reviews = new ArrayList<String>();
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
