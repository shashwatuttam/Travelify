package com.example.suche.travelify.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.suche.travelify.Fragment.AttractionsFragment;
import com.example.suche.travelify.Fragment.FoodFragment;
import com.example.suche.travelify.Fragment.HospitalsFragment;
import com.example.suche.travelify.Fragment.ParkFragment;
import com.example.suche.travelify.Fragment.SightSeeingFragment;
import com.example.suche.travelify.Fragment.airportFragment;
import com.example.suche.travelify.Fragment.atmFragment;
import com.example.suche.travelify.Fragment.movietheaterFragment;
import com.example.suche.travelify.Fragment.museumFragment;
import com.example.suche.travelify.Fragment.taxiStandFragment;
import com.example.suche.travelify.Fragment.templeFragment;
import com.example.suche.travelify.Fragment.trainstationFragment;
import com.example.suche.travelify.Fragment.zooFragment;
import com.example.suche.travelify.R;

/**
 * Created by Shashwat Aggarwal on 3/31/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter{


    // Count of Tabs.
    final private int PAGE_COUNT[] = {-1,3,4,3,3};

    private Context mContext;
    private int type;

    // Constructor.
    public FragmentAdapter(FragmentManager fm, Context context,int mtype){
        super(fm);
        mContext = context;
        type = mtype;
    }

    // Get Fragment.
    @Override
    public Fragment getItem(int position) {
        if(type==1) {
            switch (position) {
                case 0:
                    return new SightSeeingFragment();
                case 1:
                    return new AttractionsFragment();
                case 2:
                    return new templeFragment();
                default:    return null;
            }
        }
        if(type==2){
            switch (position){
                case 0:     return new FoodFragment();
                case 1:     return new ParkFragment();
                case 2:     return new museumFragment();
                case 3:     return new movietheaterFragment();
                default:    return null;
            }
        }
        if(type==3){
            switch (position){
                case 0:    return new atmFragment();
                case 1:    return new HospitalsFragment();
                case 2:     return new zooFragment();
                default:    return null;
            }
        }
        if(type==4) {
            switch (position) {
                case 0:     return new airportFragment();
                case 1:     return new trainstationFragment();
                case 2:     return new taxiStandFragment();
                default:
                    return null;
            }
        }
        else return null;
    }

    // Get No of Tabs.
    @Override
    public int getCount() {
        return PAGE_COUNT[type];
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(type==1) {
            switch (position) {
                case 0:     return mContext.getResources().getString(R.string.category_sightseeing);
                case 1:     return mContext.getResources().getString(R.string.category_attractions);
                case 2:     return mContext.getResources().getString(R.string.category_temple);
                default:    return null;
            }
        }
        if(type==2){
            switch (position){
                case 0:     return mContext.getResources().getString(R.string.category_food);
                case 1:     return mContext.getResources().getString(R.string.category_park);
                case 2:     return mContext.getResources().getString(R.string.category_museum);
                case 3:     return mContext.getResources().getString(R.string.category_movie);
                default:    return null;
            }
        }
        if(type==3){
            switch (position){
                case 0:    return mContext.getResources().getString(R.string.category_atm);
                case 1:    return mContext.getResources().getString(R.string.category_hospitals);
                case 2:     return mContext.getResources().getString(R.string.category_zoo);
                default:    return null;
            }
        }
        if(type==4) {
            switch (position) {
                case 0:    return mContext.getResources().getString(R.string.category_airport);
                case 1:    return mContext.getResources().getString(R.string.category_train);
                case 2:    return mContext.getResources().getString(R.string.category_taxi);
                default:    return null;
            }
        }
        else return null;
        // Generate title based on item position
    }

}