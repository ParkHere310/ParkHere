package com.example.trevorbernard.parkhere.User;

/**
 * Created by trevorbernard on 10/12/16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.trevorbernard.parkhere.Connectors.UserConnector;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class User implements Parcelable{

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<String> reviews;
    private boolean isVerified;
    //private Image verificationPicture;
    //private Image profilePicture;
    private Rating rating;
    private transient FirebaseUser FbUsers;
    private String UID;
    private String profilePic;

    public String getUID() {

        return UID;
    }

    public void setUID(String UID) {

        this.UID = UID;
    }

    //private Search search; //this class needs to be made

    //private SpotManager spotManager;

    public FirebaseUser getFbUsers() {

        return FbUsers;
    }

    public void setFbUsers(FirebaseUser fbUsers) {

        FbUsers = fbUsers;
    }

    public int describeContents(){

        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);

        //WHO WROTE THIS, DO WE NEED IT?
        //dest.writeStrTrianium Universal [2-Pack] Dual Port Car Chargering(phoneNumber);
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

    public User(FirebaseUser fbuser, String firstName, String lastName, String phoneNumber, String profilePic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.profilePic = profilePic;
        reviews = new ArrayList<String>();
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

    public List<String> getReviews(){

        return reviews;
    }

    public void setReviews(List<String> list) {

        this.reviews = list;
    }

    void addReview(String review){

        //add review
        reviews.add(review);

        //add to database
        UserConnector.addReviewToUser(this, review);
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

    public void setRating(Rating rating) {

        this.rating = rating;
    }

    public Bitmap downloadProfilePic() {
        final ArrayList<Bitmap> bmps = new ArrayList<Bitmap>();

        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(this.profilePic);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                bmps.add(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        while(bmps.isEmpty()) {
            try{
                Thread.sleep(10);
            } catch (Exception e) {

            }
        }
        return bmps.get(0);
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

    public static void Register(){
        //TODO
    }

    public static void Login(){
        //TODO
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public static Creator getCREATOR() {
        return CREATOR;
    }
}
