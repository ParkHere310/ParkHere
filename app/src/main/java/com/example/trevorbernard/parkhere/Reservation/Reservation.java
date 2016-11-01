package com.example.trevorbernard.parkhere.Reservation;

/**
 * Created by trevorbernard on 10/12/16.
 */

public class Reservation {

    private String ownerUID;
    private String seekerUID;
    private String parkingSpotUID;
    private boolean isCompleted;
    private Transaction transaction;
    private boolean isRatedAndReviewed;
    private String UID;

    public Reservation(String ownerUID,
            String seekerUID,
            String parkingSpotUID,
            Transaction transaction){

        this.ownerUID = ownerUID;
        this.seekerUID = seekerUID;
        this.parkingSpotUID = parkingSpotUID;
        isCompleted = false;
        isRatedAndReviewed = false;

    }

    public Reservation() {};


    public void setParkingSpotUID(String UID) {

        this.parkingSpotUID = UID;
    }

    public void setCompleted(boolean completed) {

        isCompleted = completed;
    }

    public void setTransaction(Transaction transaction) {

        this.transaction = transaction;
    }

    public void setRatedAndReviewed(boolean ratedAndReviewed) {

        isRatedAndReviewed = ratedAndReviewed;

    }

    public String getOwnerUID() {

        return ownerUID;
    }

    public String getSeekerUID() {

        return seekerUID;
    }

    public String getParkingSpotUID() {

        return parkingSpotUID;
    }

    public boolean isCompleted() {

        return isCompleted;
    }

    public Transaction getTransaction() {

        return transaction;
    }

    public boolean isRatedAndReviewed() {

        return isRatedAndReviewed;
    }

    public String getUID() {

        return UID;
    }

    public void setUID(String UID) {

        this.UID = UID;
    }

    public void setOwnerUID(String ownerUID) {

        this.ownerUID = ownerUID;
    }

    public void setSeekerUID(String seekerUID) {

        this.seekerUID = seekerUID;
    }


}
