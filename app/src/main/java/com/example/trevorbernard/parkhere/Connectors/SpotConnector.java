package com.example.trevorbernard.parkhere.Connectors;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.PhysicalSpot;
import com.example.trevorbernard.parkhere.User.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

/**
 * Created by metzm on 10/16/2016.
 */

public class SpotConnector {


    public static boolean postSpot(ParkingSpot spot) {
        /*
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
         */
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("ParkingSpots").push().getKey(); //gets new unique id
        spot.setUID(uid);
        mDatabase.child("ParkingSpots").child(uid).setValue(spot);
        return true;
    }

    public static boolean postSpot(ParkingSpot spot, Bitmap pic) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("ParkingSpots").push().getKey(); //gets new unique id

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        final ArrayList<String> downloadUrls = new ArrayList<String>();
        // puts byte array in storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://parkhere-70b24.appspot.com");
        StorageReference imagesRef = storageRef.child("SpotPics").child(uid);
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
        spot.setImageURL(downloadUrls.get(0));

        spot.setUID(uid);
        mDatabase.child("ParkingSpots").child(uid).setValue(spot);
        return true;
    }

    public static boolean postPhysicalSpot(ParkingSpot spot) {
        /*
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
         */
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("PhysicalSpots").push().getKey(); //gets new unique id
        spot.setUID(uid);
        mDatabase.child("PhysicalSpots").child(uid).setValue(spot);
        return true;
    }

    public static boolean postPhysicalSpot(ParkingSpot spot, Bitmap pic) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("PhysicalSpots").push().getKey(); //gets new unique id

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        final ArrayList<String> downloadUrls = new ArrayList<String>();
        // puts byte array in storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://parkhere-70b24.appspot.com");
        StorageReference imagesRef = storageRef.child("SpotPics").child(uid);
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
        spot.setImageURL(downloadUrls.get(0));

        spot.setUID(uid);
        mDatabase.child("PhysicalSpots").child(uid).setValue(spot);
        return true;
    }

    public static boolean editSpot(ParkingSpot spot) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ParkingSpots").child(spot.getUID()).setValue(spot);
        return true;
    }

    public static boolean editPhysicalSpot(PhysicalSpot spot) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("PhysicalSpots").child(spot.getUID()).setValue(spot);
        return true;
    }

    public static boolean removeSpot(ParkingSpot spot, User user) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ParkingSpots").child(spot.getUID()).setValue(null);
        return true;
    }

    public static boolean removePhysicalSpot(PhysicalSpot spot, User user) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("PhysicalSpots").child(spot.getUID()).setValue(null);
        return true;
    }

    public static boolean removePhysicalSpot(PhysicalSpot spot) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("PhysicalSpots").child(spot.getUID()).setValue(null);
        return true;
    }

    public static boolean removePhysicalSpot(String spot) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("PhysicalSpots").child(spot).setValue(null);
        return true;
    }

    public static boolean removeSpot(String spotUid) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ParkingSpots").child(spotUid).removeValue();
        return true;
    }

    public static ParkingSpot getParkingSpotFromUID(String uID) {
        //System.out.println(uID);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        final ArrayList<ParkingSpot> list = new ArrayList<ParkingSpot>();
        Query queryRef = mDatabase.orderByChild("uid").equalTo(uID);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    list.add(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       return new ParkingSpot();

    }
}
