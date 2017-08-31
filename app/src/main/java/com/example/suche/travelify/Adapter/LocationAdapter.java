package com.example.suche.travelify.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.suche.travelify.Model.BasicLocation;
import com.example.suche.travelify.R;

import java.util.List;

import com.squareup.picasso.Picasso;

import static android.R.attr.width;

/**
 * Created by Shashwat Aggarwal on 3/31/2017.
 */

public class LocationAdapter extends ArrayAdapter<BasicLocation>{

    private String BASE_URL = "https://maps.googleapis.com/maps/api/place/photo";
    private static final String LOG_TAG = LocationAdapter.class.getSimpleName();
    private Context context;

    // View lookup cache
    private static class ViewHolder {
        ImageView image;
        TextView title;
        TextView address;
        TextView schedule;
        TextView price;
        TextView phone;
        RatingBar ratingBar;
    }

    public LocationAdapter(Context context, int ResourceId, List<BasicLocation> locations){
        super(context,0,locations);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        // Get the data item for this position
        BasicLocation location = getItem(position);

        ViewHolder viewHolder;   // view lookup cache stored in tag

        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            viewHolder.image        = (ImageView) convertView.findViewById(R.id.imageImageView);
            viewHolder.title        = (TextView) convertView.findViewById(R.id.titleTextView);
            viewHolder.address      = (TextView) convertView.findViewById(R.id.addressTextView);
            viewHolder.ratingBar    = (RatingBar) convertView.findViewById(R.id.ratingRatingBar);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }
        else{
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(location.getTitle());
        viewHolder.address.setText(location.getAddress());


        if (location.hasAddress()){
            viewHolder.address.setVisibility(View.VISIBLE);
        } else {
            viewHolder.address.setVisibility(View.GONE);
        }


        // Rating
        double x = location.getRating();
        if(x<0){ x=0; }
        if(x>5){ x=5; }
        viewHolder.ratingBar .setRating((float)x);

        // Image
        if(location.hasImageRef()) {
            int width = location.getImageWidth();
            if (width > 600)
                width = 600;
            Uri baseuri = Uri.parse(BASE_URL);
            Uri.Builder uribuilder = baseuri.buildUpon();
            uribuilder.appendQueryParameter("photoreference", location.getImageRef());
            uribuilder.appendQueryParameter("maxwidth", Integer.toString(width));
            uribuilder.appendQueryParameter("key", "AIzaSyChQ-8VvW6H9rOlXA4o7mhunVdsTfkM9UY");
            //url made
            String url = uribuilder.toString();
            Picasso.with(context)
                    .load(url)
                    .resize(600, 600)
                    .centerCrop()
                    .into(viewHolder.image);
        }
        else{
            viewHolder.image.setImageResource(R.drawable.s12);
        }
        return convertView;
    }
}
