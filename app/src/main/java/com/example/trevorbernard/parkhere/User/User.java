package com.example.trevorbernard.parkhere.User;

/**
 * Created by trevorbernard on 10/12/16.
 */

import java.util.ArrayList;



public class User {

    //implements Parcelable

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private ArrayList<String> reviews;
    private boolean isVerified;
    //private Image verificationPicture;
    //private Image profilePicture;
    private Rating rating;
    //private Search search; //this class needs to be made
    //private SpotManager spotManager;
   // private FireBaseUser fireBaseUser;


    /*
    public int describeContents(){
        return 0;
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };


    public User(Parcel in){
        String[] data = new String[4];

        this.email = data[0];
        this.firstName = data[1];
        this.lastName = data[2];
        this.phoneNumber = data[3];

      //  this.reviews = data[4];
        //this.isVerified = data[5];
        //this.rating = data[6];

    }

*/

    public User() {
        // TODO Auto-generated constructor stub
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    ArrayList<String> getReviews(){
        return reviews;
    }

    void addReview(String review){
        reviews.add(review);
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public boolean getIsVerified(){
        return isVerified;
    }

    public void setIsVerified(boolean verified){
        this.isVerified = verified;
    }

    public Rating getRating(){
        return rating;
    }

    /*
    public Image getProfilePicture(){
        return profilePicture;
    }

    public void setProfilePicture(Image picture){
        this.profilePicture = picture;
    }

    public Search getSearch(){
        return search;
    }

    public void setSearch(Search search){
        this.search = search;
    }

    public SpotManager getSpotManager(){
        return spotManager;
    }

    public void setSpotManager(SpotManager spotManager){
        this.spotManager = spotManager;
    }

    */

    public void Register(){
        //TODO
    }

    public void Login(){
        //TODO
    }
}
