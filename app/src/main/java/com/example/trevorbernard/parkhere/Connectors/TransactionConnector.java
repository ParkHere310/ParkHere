package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.example.trevorbernard.parkhere.User.User;

/**
 * Created by metzm on 10/16/2016.
 */

public class TransactionConnector {
    public static Reservation rentSpot(ParkingSpot spot, User user) {
        return new Reservation();
    }

    public static boolean addReservation(Reservation res) {
        return true;
    }

    public static boolean cancelReservation(Reservation res, User user) {
        return true;
    }
}
