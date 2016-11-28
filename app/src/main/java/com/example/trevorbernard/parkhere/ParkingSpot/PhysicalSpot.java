package com.example.trevorbernard.parkhere.ParkingSpot;

import com.example.trevorbernard.parkhere.User.Rating;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metzm on 11/22/2016.
 */

public class PhysicalSpot {

    private String name;
    private String imageURL;
    private String description;
    private List<String> reviews;
    private boolean isSUV;
    private boolean isCovered;
    private boolean isHandicap;
    private Rating rating;
    //private Address address;
    private double longitude;
    private double latitude;
    private String address;
    private String UID;
    private String ownerUID;

    private int timesListed = 0;

    public PhysicalSpot(){

    }

    public PhysicalSpot(String name,
                       String description,
                       boolean isSUV,
                       boolean isCovered,
                       boolean isHandicap,
                       String address,
                       double latitude, double longitude){

        // private String imageURL; need to add in later some how

        reviews = new ArrayList<String>();

        this.name = name;
        this.description = description;
        this.isSUV = isSUV;
        this.isCovered = isCovered;
        this.isHandicap = isHandicap;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;

        ownerUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
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

    public boolean isHandicap() {
        return isHandicap;
    }

    public void setHandicap(boolean handicap) {
        isHandicap = handicap;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getOwnerUID() {
        return ownerUID;
    }

    public void setOwnerUID(String ownerUID) {
        this.ownerUID = ownerUID;
    }

    public int getTimesListed() {
        return timesListed;
    }

    public void setTimesListed(int timesListed) {
        this.timesListed = timesListed;
    }

    public void incrementTimesListed() {
        timesListed++;
    }
}
