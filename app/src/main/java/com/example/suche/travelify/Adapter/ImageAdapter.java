package com.example.suche.travelify.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.suche.travelify.Model.Details;
import com.example.suche.travelify.Model.PlaceImage;
import com.example.suche.travelify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Shashwat Aggarwal on 4/18/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private static final String LOG_TAG = ImageAdapter.class.getSimpleName();
    private List<PlaceImage> mrev;
    private String BASE_URL = "https://maps.googleapis.com/maps/api/place/photo";
    private static String key = "AIzaSyChQ-8VvW6H9rOlXA4o7mhunVdsTfkM9UY";  //agg
    //private static String key = "AIzaSyDiBvamlXvfV-8x3JBEwq9pyZYd5sbkGW8";  //uttam
    private Context mcontext;

    // View lookup cache
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public ViewHolder(ImageView img){
            super(img);
            image = img;
        }
    }

    public ImageAdapter(Context context,List<PlaceImage> rev){
        mrev = rev;
        mcontext = context;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ImageView v = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public  void onBindViewHolder(ViewHolder holder, int position) {

        PlaceImage temp = mrev.get(position);
        if(temp.hasImageRef()) {
            int width = temp.getImageWidth();
            if (width > 1000)
                width = 1000;

            Uri baseuri = Uri.parse(BASE_URL);
            Uri.Builder uribuilder = baseuri.buildUpon();
            uribuilder.appendQueryParameter("photoreference", temp.getImageRef());
            uribuilder.appendQueryParameter("maxwidth", Integer.toString(width));
            uribuilder.appendQueryParameter("key", key);
            //url made
            String url = uribuilder.toString();
            Picasso.with(mcontext)
                    .load(url)
                    .resize(1000,1000)
                    .centerCrop()
                    .into(holder.image);
        }
        else{
            holder.image.setImageResource(R.drawable.s12);
        }
    }

        @Override
    public int getItemCount() {
            return mrev.size();
        }
 }
