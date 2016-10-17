package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.example.trevorbernard.parkhere.User.User;

import java.util.ArrayList;

/**
 * Created by metzm on 10/16/2016.
 */

public class UserConnector {
    public static ArrayList<ParkingSpot> getListingsForUser(User user) {
        ArrayList<ParkingSpot> list = new ArrayList<ParkingSpot>();
        return list;
    }

    public static ArrayList<Reservation> getReservationsforUser(User user){
        ArrayList<Reservation> list = new ArrayList<Reservation>();
        return list;
    }
    public static ArrayList<Reservation> getPastReservationsforUser(User user){
        ArrayList<Reservation> list = new ArrayList<Reservation>();
        return list;
    }

    public static boolean addReviewToUser(User submitter, User reviewed, String rev){
        return true;
    }

    public static boolean addReviewToSpot(User submitter, ParkingSpot reviewed, String rev){
        return true;
    }

    public static boolean checkIn(User user, Reservation res) {
        return true;
    }
}
