package com.example.trevorbernard.parkhere.User;

/**
 * Created by mac on 10/22/16.
 */


public class Rating {

    private int totalRating;
    private int numRatings;


    public Rating() {
        totalRating = 0;
        numRatings = 0;
    }

    public void addRating(int rating){
        numRatings++;
        totalRating += rating;
    }

    public double getRating(){
        return (double) (totalRating/numRatings);
    }


}