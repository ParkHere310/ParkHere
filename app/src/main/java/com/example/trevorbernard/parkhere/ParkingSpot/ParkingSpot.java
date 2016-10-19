package com.example.trevorbernard.parkhere.ParkingSpot;

import android.location.Location;
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
    private Location location;
    private TimeWindow timeWindow;
    private String UID;

    public ParkingSpot() {}

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getUID() {
        return this.UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
