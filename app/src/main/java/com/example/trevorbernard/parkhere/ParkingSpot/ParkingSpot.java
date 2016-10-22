package com.example.trevorbernard.parkhere.ParkingSpot;

import android.content.Context;
import android.location.Geocoder;
import java.io.IOException;
import java.util.List;
import android.location.Address;
import android.media.Image;
import com.example.trevorbernard.parkhere.User.User;

import java.util.ArrayList;

/**
 * Created by trevorbernard on 10/12/16.
 */

public class ParkingSpot {

    private String name;
    private User owner;
    private Image image;
    private String description;
    private ArrayList<String> reviews;
    private User occupant;
    private int price;
    private boolean isSUV;
    private boolean isCovered;
    private Address address;
    private TimeWindow timeWindow;
    private String UID;

    public ParkingSpot() {}

    public void setTimeWindow(TimeWindow timeWindow) { this.timeWindow = timeWindow; }

    public TimeWindow getTimeWindow() { return this.timeWindow; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public User getOccupant() {
        return occupant;
    }

    public void setOccupant(User occupant) {
        this.occupant = occupant;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    boolean setAdressFromString(String loc, Context con) {
        try {
            Geocoder geocoder = new Geocoder(con);
            List<Address> addresses;

            addresses = geocoder.getFromLocationName(loc, 5);
            if(addresses == null) {
               return false;
            } else {
                this.address = addresses.get(0);
            }

        } catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("ioe in setLocation from string in ParkingSpot");
        }
        return true;
    }

    public String getUID() {
        return this.UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
