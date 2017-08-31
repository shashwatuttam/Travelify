package com.example.suche.travelify.Model;
import android.util.Log;

/**
 * Created by Shashwat Uttam on 4/4/2017.
 */

public class BasicLocation {

    private String mtitle;
    private String mdescription;
    private String maddress;
    private double mrating;
    private String mplaceid;
    private String mphone;
    private String mschedule;
    private String mprice;
    private String mimageRef ;
    private int mimageWidth;
    private int mimageHeight;

    public BasicLocation(String title , String description , String address, double rating, String placeid,
                         String phone, String schedule, String price, String imageref, int imageWidth, int imageHeight)
    {
        mtitle          = title;
        mdescription    = description;
        maddress        = address;
        mphone          = phone;
        mschedule       = schedule;
        mprice          = price;
        mrating         = rating;
        mplaceid        = placeid;
        mimageRef       = imageref;
        mimageWidth     = imageWidth;
        mimageHeight    = imageHeight;
    }

    public String getTitle() {
        return mtitle;
    }

    public String getDescription() {
        return mdescription;
    }

    public String getAddress() {
        return maddress;
    }

    public String getPhone() {
        return mphone;
    }

    public String getSchedule() {
        return mschedule;
    }

    public String getPrice() {
        return mprice;
    }

    public double getRating(){
        return mrating;
    }
    public String getPlaceid(){
        return mplaceid;
    }
    public String getImageRef(){ return mimageRef; }
    public int getImageWidth(){ return mimageWidth; }
    public int getImageHeight(){ return mimageHeight; }


    public boolean hasPrice(){
        return getPrice() != null;
    }

    public boolean hasPhone(){
        return getPhone() != null;
    }

    public boolean hasAddress(){
        return getAddress() != null;
    }

    public boolean hasSchedule(){
        return getSchedule() != null;
    }

    public boolean hasRating(){
        return getRating() != -1;
    }
    public boolean hasPlaceid(){
        return getPlaceid() != null;
    }
    public boolean hasImageRef(){
        return getImageRef() != null;
    }

    public boolean hasImageWidth(){
        return getImageWidth() != -1;
    }

    public boolean hasImageHeight(){
        return getImageHeight() != -1;
    }


    @Override
    public String toString() {
       return getTitle() + "\n" + getDescription() + "\n" + getAddress() + "\n" + getPhone() + "\n"
               + getPrice() + "\n" + getSchedule() + "\n" + getRating() + "\n" + getPlaceid() + "\n"
               + getImageRef()+ "\n" + getImageWidth() + "\n" + getImageHeight();
    }

}