package com.example.trevorbernard.parkhere.Reservation;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.User.User;

/**
 * Created by trevorbernard on 10/12/16.
 */

public class Reservation {

    User owner;
    User seeker;
    ParkingSpot parkingSpot;
    boolean isCompleted;
    Transaction transaction;
    boolean isRatedAndReviewed;

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setSeeker(User seeker) {
        this.seeker = seeker;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
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

    public User getOwner() {
        return owner;
    }

    public User getSeeker() {
        return seeker;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
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
}
