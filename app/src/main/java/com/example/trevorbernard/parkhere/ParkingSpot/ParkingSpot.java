package com.example.trevorbernard.parkhere.ParkingSpot;

import android.location.Address;

import com.example.trevorbernard.parkhere.User.Rating;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by trevorbernard on 10/12/16.
 */

public class ParkingSpot {

    public static void test2() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query queryRef = mDatabase.child("ParkingSpots").orderByChild("longitude").startAt(-120).endAt(110);

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //parkingSpots.add(snapshot.getValue(ParkingSpot.class));
                    ParkingSpot ps = snapshot.getValue(ParkingSpot.class);
                    System.out.println("ps: " + ps.getName() + " " + ps.getAddress());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static void test() {
        System.out.println("running");
        ParkingSpot ps = new ParkingSpot();
        ps.setName("Spot 1");
        Address address = new Address(Locale.US);
        ps.setLatitude(34.052235);
        ps.setLongitude(-118.243683);
        ps.setAddress("123 Fake Street");
        ps.setCovered(false);
        ps.setPrice(200);
        Date start = new Date();
        Date end = new Date(start.getTime() + 100000L);
        TimeWindow tw = new TimeWindow(start,end);
        ps.setTimeWindow(tw);
        List<String> reviews = new ArrayList<String>();
        reviews.add("asdf");
        reviews.add("fds");
        ps.setReviews(reviews);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("ParkingSpots").push().getKey();
        ps.setUID(uid);

        mDatabase.child("ParkingSpots").child(uid).setValue(ps);
    }



    private String name;
    private String imageURL;
    private String description;
    private List<String> reviews;
    private int price;
    private boolean isSUV;
    private boolean isCovered;
    private boolean isHandicap;
    private Rating rating;


    //private Address address;
    private double longitude;
    private double latitude;
    private String address;
    private TimeWindow timeWindow;
    private String UID;
    private String occupantUID;
    private String ownerUID;


    public double getLatitude() {

        return latitude;
    }

    public void setLatitude(double latidude) {

        this.latitude = latidude;
    }

    public double getLongitude() {

        return longitude;
    }

    public void setLongitude(double longitude) {

        this.longitude = longitude;
    }

    public String getOccupantUID() {

        return occupantUID;
    }

    public void setOccupantUID(String occupantUID) {

        this.occupantUID = occupantUID;
    }

    public String getOwnerUID() {

        return ownerUID;
    }

    public void setOwnerUID(String ownerUID) {

        this.ownerUID = ownerUID;
    }

    public ParkingSpot() {


    }

    public ParkingSpot(String name,
                       String description,
                       int price,
                       boolean isSUV,
                       boolean isCovered,
                       boolean isHandicap,
                       String address,
                       Date start,
                       Date end){

        timeWindow = new TimeWindow(start,end);
       // private String imageURL; need to add in later some how

        reviews = new ArrayList<String>();

        this.name = name;
        this.description = description;
        this.price = price;
        this.isSUV = isSUV;
        this.isCovered = isCovered;
        this.isHandicap = isHandicap;
        this.address = address;

        //TODO:
        //set lat and long from the address that came in
        //private double longitude;
        //private double latitude;

        //TODO: someone do the timewindow
       //private TimeWindow timeWindow;

        ownerUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        occupantUID = "-1"; // No occupant
    }

    public void setTimeWindow(TimeWindow timeWindow) {

        this.timeWindow = timeWindow;

    }

    public TimeWindow getTimeWindow() {

        return this.timeWindow;

    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }


    public String getImageURL() {

        return imageURL;
    }

    public void setImageURL(String imageURL) {

        this.imageURL = imageURL;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public List<String> getReviews() {

        return reviews;
    }

    public void setReviews(List<String> reviews) {

        this.reviews = reviews;
    }

    public int getPrice() {

        return price;
    }

    public void setPrice(int price) {

        this.price = price;
    }

    public boolean isSUV() {

        return isSUV;
    }

    public void setSUV(boolean SUV) {

        isSUV = SUV;
    }

    public boolean isCovered() {

        return isCovered;
    }

    public void setCovered(boolean covered) {

        isCovered = covered;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public String getUID() {

        return this.UID;
    }

    public void setUID(String UID) {

        this.UID = UID;
    }

    public boolean isHandicap() {
        return isHandicap;
    }

    public Rating getRating() {
        return rating;
    }

    public void setHandicap(boolean handicap) {
        isHandicap = handicap;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
