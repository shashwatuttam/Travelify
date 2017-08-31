package com.example.suche.travelify.Model;

/**
 * Created by Shashwat Aggarwal on 4/18/2017.
 */

public class Reviews{
    private String mname;
    private String mtext;
    private int mrating;
    private String murl;

    public Reviews(String name , String text, int rating, String url){
        mname = name;
        mtext = text;
        mrating = rating;
        murl = url;
    }
    public String getAuthorName() {
        return mname;
    }

    public String getAuthorText() {
        return mtext;
    }

    public String getAuthorURL() {
        return murl;
    }

    public int getAuthorRating() {
        return mrating;
    }

    public boolean hasAuthorRating(){
        return getAuthorRating() != -1;
    }
    public boolean hasAuthorName(){
        return getAuthorName() != null;
    }
    public boolean hasAuthorText(){
        return getAuthorText() != null;
    }
    public boolean hasAuthorURL(){
        return getAuthorURL() != null;
    }

}
