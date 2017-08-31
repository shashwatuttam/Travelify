package com.example.suche.travelify.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.suche.travelify.Model.Reviews;
import com.example.suche.travelify.R;

import java.util.List;

/**
 * Created by Shashwat Aggarwal on 4/18/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {


    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();
    private Context mcontext;
    private List<Reviews> mrev;

    // View lookup cache
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView text;
        public TextView url;
        public RatingBar ratingBar;
        public ViewHolder(View v){
            super(v);
            name      =     (TextView)  v.findViewById(R.id.authorTextView);
            text      =     (TextView)  v.findViewById(R.id.textTextView);
            url       =     (TextView)  v.findViewById(R.id.urlTextView);
            ratingBar =     (RatingBar) v.findViewById(R.id.authorratingRatingBar);

        }
    }


    public ReviewAdapter(Context context, List<Reviews> rev){
        mrev = rev;
        mcontext = context;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public  void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data item for this position
        Reviews review = mrev.get(position);

        if(review.getAuthorName()!= null)
            holder.name.setText(review.getAuthorName());
        if(review.getAuthorText()!= null)
            holder.text.setText(review.getAuthorText());
        if(review.getAuthorURL()!= null)
            holder.url.setText(review.getAuthorURL());


        if (review.hasAuthorText()){
            holder.text.setVisibility(View.VISIBLE);
        } else {
            holder.text.setVisibility(View.GONE);
        }

        if (review.hasAuthorURL()){
            holder.url.setVisibility(View.VISIBLE);
        } else {
            holder.url.setVisibility(View.GONE);
        }

        // Rating
        int x = review.getAuthorRating();
        if(x<0){ x=0; }
        if(x>5){ x=5; }
        holder.ratingBar.setRating((float)x);
    }

    @Override
    public int getItemCount() {
        return mrev.size();
    }
}
