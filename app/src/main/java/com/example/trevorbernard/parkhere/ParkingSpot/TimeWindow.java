package com.example.trevorbernard.parkhere.ParkingSpot;


import java.util.Date;

/**
 * Created by trevorbernard on 10/19/16.
 */

public class TimeWindow {

    Date startDateTime;
    Date endDateTime;

    // if no start and end date are specified, default constructor
    // sets end date to now and start date to 1 hour ago
    public TimeWindow() {
        endDateTime = new Date();
        startDateTime = new Date(endDateTime.getTime() - 3_600_000L);
    }

    public TimeWindow(Date start, Date end) {
        this.startDateTime = start;
        this.endDateTime = end;
    }

}
