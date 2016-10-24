package com.example.trevorbernard.parkhere.ParkingSpot;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by trevorbernard on 10/19/16.
 */

public class TimeWindow {

    public long startDateTime;
    public long endDateTime;
    // if no start and end date are specified, default constructor
    // sets end date to now + 15 days (rounded to nearest hour)
    // and start date to 1 hour before
    public TimeWindow() {
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.add(Calendar.DAY_OF_YEAR,15);

        endDateTime = calendar.getTime().getTime();
        calendar.add(Calendar.HOUR,-1);
        startDateTime = new Date(endDateTime).getTime();
    }

    public TimeWindow(Date start, Date end) {
        if(start.after(end)) {
            throw new IllegalArgumentException("In TimeWindow Constructor: " +
                    "start Date/Time is after end Date/Time");
        }
        this.startDateTime = start.getTime();
        this.endDateTime = end.getTime();
    }

}
