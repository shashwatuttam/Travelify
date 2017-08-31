package com.example.suche.travelify.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.suche.travelify.Adapter.LocationAdapter;
import com.example.suche.travelify.Detail.DetailActivity;
import com.example.suche.travelify.Loaders.LocationLoader;
import com.example.suche.travelify.Model.BasicLocation;
import com.example.suche.travelify.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shashwat Aggarwal on 3/31/2017.
 */
public class trainstationFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<BasicLocation>>{

    private static final int LocationLoader_ID = 12;
    private LocationAdapter adapter;
    private String LOG_TAG;
    private ImageView mEmptyStateTextView;
    private View rootView;
    public trainstationFragment(){
        // empty public constructor.
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_locations, container, false);

        // Create a List of Locations.
        final List<BasicLocation> locations = new ArrayList<>();


        // Initialize Adapter.
        adapter = new LocationAdapter(getActivity(),-1, locations);

        // Attach Adapter to List View.
        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        mEmptyStateTextView = (ImageView) rootView.findViewById(R.id.empty_view);
        mEmptyStateTextView.setImageResource(R.drawable.noconnection);
        mEmptyStateTextView.setVisibility(View.GONE);
        //listView.setEmptyView(mEmptyStateTextView);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Perform action on click
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra("placeid",locations.get(position).getPlaceid());
                // Start the new activity
                startActivity(i);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader.
            loaderManager.initLoader(LocationLoader_ID, null, this);
        } else {
            // Error
            View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            //mEmptyStateTextView.setImageResource(R.drawable.noconnection);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }

        final android.support.v4.widget.SwipeRefreshLayout mySwipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
                        mySwipeRefreshLayout.setRefreshing(false);
                        myUpdateOperation();

                    }
                }
        );

        return rootView;
    }

    //Loader methods override
    @Override
    public android.support.v4.content.Loader<List<BasicLocation>> onCreateLoader(int id, Bundle args) {
        return new LocationLoader(getContext(),4,1);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<BasicLocation>> loader, List<BasicLocation> data) {
        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setVisibility(View.GONE);
        adapter.clear();

        if(data!=null && !data.isEmpty()){
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<BasicLocation>> loader) {
        adapter.clear();
    }

    public void myUpdateOperation(){
        adapter.clear();
        mEmptyStateTextView.setVisibility(View.GONE);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader.
            loaderManager.restartLoader(LocationLoader_ID, null, this);
        } else {
            // Error
            View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            //mEmptyStateTextView.setImageResource(R.drawable.noconnection);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }
    }
}
