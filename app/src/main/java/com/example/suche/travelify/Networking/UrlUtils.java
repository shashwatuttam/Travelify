package com.example.suche.travelify.Networking;

import android.net.Uri;

import com.example.suche.travelify.StartScreen;

/**
 * Created by Shashwat Uttam on 4/4/2017.
 */

public class UrlUtils {

    private static final String LOG_TAG = UrlUtils.class.getSimpleName();
    //private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json";
    private static final String DETAIL_URL = "https://maps.googleapis.com/maps/api/place/details/json";
    private static String key = "AIzaSyChQ-8VvW6H9rOlXA4o7mhunVdsTfkM9UY";  //agg
    //private static String key = "AIzaSyDiBvamlXvfV-8x3JBEwq9pyZYd5sbkGW8";  //uttam
    private static String radius = "10000";
    //types
    private static String[] types1 = {"attractions","shopping_malls", "hindu_temple"};
    private static String[] types2 = {"restaurants", "park", "museum", "movie_theater"};
    private static String[] types3 = {"atm",  "hospitals", "zoo"};
    private static String[] types4 = {"airport", "train_station", "taxi_stand"};


    private UrlUtils(){
    }

    public static String createUrl(int typemain,int type){

        String placename;
        if(StartScreen.usegps==1){
            placename = QueryUtils.fetchlocality(StartScreen.latitude,StartScreen.longitude);
            if(placename == null){
                return null;
            }
        }
        else{
            placename = StartScreen.placename;
        }
        String location = Double.toString(StartScreen.latitude)+","+Double.toString(StartScreen.longitude);

        Uri baseuri = Uri.parse(BASE_URL);
        Uri.Builder uribuilder = baseuri.buildUpon();
        if(typemain==1){
            uribuilder.appendQueryParameter("query",types1[type]+" in "+placename);
        }
        if(typemain==2){
            uribuilder.appendQueryParameter("query",types2[type]+" in "+placename);
        }
        if(typemain==3){
            uribuilder.appendQueryParameter("query",types3[type]+" in "+placename);
        }
        if(typemain==4){
            uribuilder.appendQueryParameter("query",types4[type]+" in "+placename);
        }
        uribuilder.appendQueryParameter("key",key);

        return uribuilder.toString();
    }

    public static String createDetailsUrl(String placeid){
        Uri baseuri = Uri.parse(DETAIL_URL);
        Uri.Builder uribuilder = baseuri.buildUpon();
        uribuilder.appendQueryParameter("placeid",placeid);
        uribuilder.appendQueryParameter("key",key);
        return uribuilder.toString();
    }

}
