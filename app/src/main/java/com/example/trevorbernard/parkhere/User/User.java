package com.example.trevorbernard.parkhere.User;

/**
 * Created by trevorbernard on 10/12/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class User implements Parcelable{

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private ArrayList<String> reviews;
    private boolean isVerified;
    //private Image verificationPicture;
    //private Image profilePicture;
    private Rating rating;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    //private Search search; //this class needs to be made
    //private SpotManager spotManager;
   // private FireBaseUser fireBaseUser;
    private String UID;



    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeByte((byte) (isVerified ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel in) {
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
       phoneNumber = in.readString();
        isVerified = in.readByte() != 0;
    }


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

    public ArrayList<String> getReviews(){
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
