package com.example.suche.travelify.Model;

import java.util.List;

/**
 * Created by Shashwat Aggarwal on 4/17/2017.
 */

public class Details {


        private String mtitle;
        private String maddress;
        private double mrating;
        private String mplaceid;
        private String mphone;
        private String[] mschedule;
        private boolean mopen;
        private String mwebsite;
        private List<Reviews> mreviews;
        private int mprice;
        private List<PlaceImage> mimage;


        public Details(String title , String address, double rating, String placeid,
                             String phone,boolean open, String schedule[], String website, List<Reviews> reviews,int price, List<PlaceImage> images)
        {
            super();
            mtitle          = title;
            maddress        = address;
            mphone          = phone;
            mschedule       = schedule;
            mopen           =   open;
            mwebsite        = website;
            mreviews        = reviews;
            mprice          = price;
            mrating         = rating;
            mplaceid        = placeid;
            mimage          = images;
        }

        public String getTitle() {
            return mtitle;
        }

        public String getAddress() {
            return maddress;
        }

        public String getPhone() {
            return mphone;
        }

        public String[] getSchedule() {
            return mschedule;
        }

        public String getWebsite() {
            return mwebsite;
        }

        public List<Reviews> getReviews() {
            return mreviews;
        }

        public int getPrice() {
            return mprice;
        }

        public double getRating(){
            return mrating;
        }

        public String getPlaceid(){
            return mplaceid;
        }

        public List<PlaceImage> getImage(){ return mimage; }


        public boolean isopen() {
            return mopen;
        }

        public boolean hasTitle(){
            return getTitle() != null;
        }
        public boolean hasAddress(){
            return getAddress() != null;
        }
        public boolean hasPhone(){
            return getPhone() != null;
        }
        public boolean hasSchedule(){
            return getSchedule() != null;
        }
        public boolean hasWebsite(){
            return getWebsite() != null;
        }

        public boolean hasReviews(){
            return getReviews() != null;
        }

        public boolean hasPrice(){
            return getPrice() != -1;
        }

        public boolean hasRating(){
            return getRating() != -1;
        }
        public boolean hasPlaceid(){
            return getPlaceid() != null;
        }

}
