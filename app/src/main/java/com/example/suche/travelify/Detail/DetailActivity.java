package com.example.suche.travelify.Detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.suche.travelify.Adapter.ImageAdapter;
import com.example.suche.travelify.Adapter.ReviewAdapter;
import com.example.suche.travelify.Loaders.DetailLoader;
import com.example.suche.travelify.Model.Details;
import com.example.suche.travelify.Model.PlaceImage;
import com.example.suche.travelify.Model.Reviews;
import com.example.suche.travelify.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Shashwat Aggarwal on 4/21/2017.
 */

public class DetailActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Details> {

    String mPlaceId;
    private ImageView mEmptyStateTextView;
    private LinearLayout MainDetail;
    Activity context;
    private String BASE_URL = "https://maps.googleapis.com/maps/api/place/photo";


    final List<Reviews> rev = new ArrayList<>();
    final List<PlaceImage> img = new ArrayList<>();

    private RecyclerView.Adapter adapterIMG;
    private RecyclerView.Adapter adapterREV;

    private static class ViewHolder {
        RecyclerView image;
        TextView title;
        TextView address;
        TextView schedule;
        RecyclerView reviews;
        TextView price;
        TextView phone;
        TextView website;
        RatingBar ratingBar;
    }

    DetailActivity.ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = DetailActivity.this;


        adapterIMG = new ImageAdapter(this,img);
        adapterREV = new ReviewAdapter(this,rev);

        viewHolder              = new DetailActivity.ViewHolder();

        viewHolder.image        = (RecyclerView) findViewById(R.id.imagelistView);
        viewHolder.reviews      = (RecyclerView) findViewById(R.id.reviewlistView);
        
        viewHolder.reviews.setNestedScrollingEnabled(false);
        
        viewHolder.title        = (TextView) findViewById(R.id.titleTextView);
        viewHolder.address      = (TextView) findViewById(R.id.addressTextView);
        viewHolder.schedule     = (TextView) findViewById(R.id.scheduleTextView);
        viewHolder.price        = (TextView) findViewById(R.id.priceTextView);
        viewHolder.phone        = (TextView) findViewById(R.id.phoneTextView);
        viewHolder.website      = (TextView) findViewById(R.id.websiteTextView);
        viewHolder.ratingBar    = (RatingBar) findViewById(R.id.ratingRatingBar);
        mEmptyStateTextView     = (ImageView) findViewById(R.id.empty_view);
        MainDetail              =  (LinearLayout) findViewById(R.id.scrollViewMain);
        mEmptyStateTextView.setVisibility(View.GONE);

        viewHolder.image.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getApplicationContext()) {

                    private static final float SPEED = 300f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

        };
        viewHolder.image.setLayoutManager(layoutManager1);
        viewHolder.image.setAdapter(adapterIMG);

        viewHolder.reviews.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getApplicationContext()) {

                    private static final float SPEED = 300f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

        };

        viewHolder.reviews.setNestedScrollingEnabled(false);

        viewHolder.reviews.setLayoutManager(layoutManager2);
        viewHolder.reviews.setAdapter(adapterREV);

        Intent i = getIntent();
        mPlaceId = i.getStringExtra("placeid");


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
            // Initialize Loader.
            loaderManager.initLoader(0, null, this);
        } else {
            // Error.
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            MainDetail.setVisibility(View.GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }

        final FloatingActionButton googlemaps = (FloatingActionButton) findViewById(R.id.googlemaps);
        googlemaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,address;
                if(!viewHolder.title.getText().equals("")&&!viewHolder.address.getText().equals(""))
                {   name = viewHolder.title.getText().toString();
                    address = viewHolder.address.getText().toString();
                    String url = "http://maps.google.co.in/maps?q=" + name + "+" + address;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(getPackageManager()) != null) {
                        startActivity(i);
                    }
                }
            }
        });


    }

    //Loader methods override
    @Override
    public android.support.v4.content.Loader<Details> onCreateLoader(int id, Bundle args) {
        return new DetailLoader(context, mPlaceId);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Details> loader, Details place) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        if(place!=null) {
            mEmptyStateTextView.setVisibility(View.GONE);
            MainDetail.setVisibility(View.VISIBLE);
            Set(place);
        }
        else
        {
            MainDetail.setVisibility(View.GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Details> loader) {

        clear();
    }

    public void Set(Details place)
    {

        img.clear();
        adapterIMG.notifyDataSetChanged ();

        rev.clear();
        adapterREV.notifyDataSetChanged ();


        if(place.getImage()!=null){
            int curSize = adapterIMG.getItemCount();
            List<PlaceImage> newItems = place.getImage();
            img.addAll(newItems);
            adapterIMG.notifyItemRangeInserted(curSize, newItems.size());
        }


        if(place.getReviews()!=null){
            int curSize = adapterREV.getItemCount();
            List<Reviews> newItems = place.getReviews();
            rev.addAll(newItems);
            adapterREV.notifyItemRangeInserted(curSize, newItems.size());
        }

        viewHolder.title.setText(place.getTitle());
        viewHolder.address.setText(place.getAddress());
        viewHolder.phone.setText(place.getPhone());
        viewHolder.website.setText(place.getWebsite());

        if(place.isopen()){
            viewHolder.schedule.setText(R.string.open);
            viewHolder.schedule.setTextColor(Color.GREEN);
        }
        else{
            viewHolder.schedule.setText(R.string.closed);
            viewHolder.schedule.setTextColor(Color.RED);
        }
        int x = place.getPrice();
        if(x > 4) x = 4;
        String a;
        switch (x){
            case 0: a = "Free";
            case 1: a = "Inexpensive";
            case 2: a = "Moderate";
            case 3: a = "Expensive";
            case 4: a = "Very Expensive";
            default: a = "";
        }
        viewHolder.price.setText(a);
        if (!a.equals("")){
            viewHolder.price.setVisibility(View.VISIBLE);
        } else {
            viewHolder.price.setVisibility(View.GONE);
        }


        if (place.hasAddress()){
            viewHolder.address.setVisibility(View.VISIBLE);
        } else {
            viewHolder.address.setVisibility(View.GONE);
        }
        if (place.hasPhone()){
            viewHolder.phone.setVisibility(View.VISIBLE);
        } else {
            viewHolder.phone.setVisibility(View.GONE);
        }

        if (place.hasWebsite()){
            viewHolder.website.setVisibility(View.VISIBLE);
        } else {
            viewHolder.website.setVisibility(View.GONE);
        }

        // Rating
        double x1 = place.getRating();
        if(x1<0){ x1=0; }
        if(x1>5){ x1=5; }
        viewHolder.ratingBar.setRating((float)x1);


    }
    public void clear(){
        img.clear();
        adapterIMG.notifyDataSetChanged ();
        rev.clear();
        adapterREV.notifyDataSetChanged ();
        viewHolder.price.setText("");
        viewHolder.schedule.setText("");
        viewHolder.title.setText("");
        viewHolder.address.setText("");
        viewHolder.phone.setText("");
        viewHolder.website.setText("");
        viewHolder.ratingBar.setRating(0);
    }
    public void myUpdateOperation(){

        clear();

        mEmptyStateTextView.setVisibility(View.GONE);
        MainDetail.setVisibility(View.VISIBLE);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
            // Initialize the loader.
            loaderManager.restartLoader(0, null, this);
        } else {
            // Error
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            MainDetail.setVisibility(View.GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        myUpdateOperation();
        return super.onOptionsItemSelected(item);
    }

}
