package com.example.suche.travelify.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.suche.travelify.Model.Details;
import com.example.suche.travelify.Networking.QueryUtils;
import com.example.suche.travelify.Networking.UrlUtils;

import java.util.List;

/**
 * Created by Shashwat Aggarwal on 4/17/2017.
 */

public class DetailLoader extends AsyncTaskLoader<Details> {
    private String mplaceid, url;

    public DetailLoader(Context context, String a){
        super(context);
        mplaceid = a;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Details loadInBackground() {
        url = UrlUtils.createDetailsUrl(mplaceid);
        if(url==null)
            return null;
        Details place = QueryUtils.fetchPlacedata(url);
        return place;
    }
}
