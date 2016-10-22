package com.example.trevorbernard.parkhere.ParkingSpot;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by trevorbernard on 10/19/16.
 */

public class TimeWindow {

    Date startDateTime;
    Date endDateTime;
    // if no start and end date are specified, default constructor
    // sets end date to now + 15 days (rounded to nearest hour)
    // and start date to 1 hour before
    public TimeWindow() {
        endDateTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDateTime);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.add(Calendar.DAY_OF_YEAR,15);

        endDateTime = calendar.getTime();
        calendar.add(Calendar.HOUR,-1);
        startDateTime = new Date(endDateTime.getTime());
    }

    public TimeWindow(Date start, Date end) {
        if(start.after(end)) {
            throw new IllegalArgumentException("In TimeWindow Constructor: " +
                    "start Date/Time is after end Date/Time");
        }
        this.startDateTime = start;
        this.endDateTime = end;
    }

}
