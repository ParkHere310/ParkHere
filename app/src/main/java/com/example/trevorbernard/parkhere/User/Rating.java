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

    public double calculateRating(){
        if(numRatings != 0) return(double) (totalRating/numRatings);
        else return 1.0;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }
}