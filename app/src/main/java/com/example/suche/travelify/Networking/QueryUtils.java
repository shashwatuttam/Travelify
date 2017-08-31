package com.example.suche.travelify.Networking;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.suche.travelify.Model.BasicLocation;
import com.example.suche.travelify.Model.Details;
import com.example.suche.travelify.Model.PlaceImage;
import com.example.suche.travelify.Model.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.rating;

/**
 * Created by Shashwat Aggarwal on 4/17/2017.
 */

public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String GPS_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static String key = "AIzaSyChQ-8VvW6H9rOlXA4o7mhunVdsTfkM9UY";  //agg
    //private static String key = "AIzaSyDiBvamlXvfV-8x3JBEwq9pyZYd5sbkGW8";  //uttam

    private QueryUtils() {
    }

    /**
     * Query the Google Places dataset and return a list of {@link BasicLocation} objects.
     */
    public static List<BasicLocation> fetchLocationdata(String requestUrl){
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<BasicLocation> locations = extractFeatureFromJson(jsonResponse);

        return locations;
    }
    public static Details fetchPlacedata(String requestUrl){
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        Details place = extractDetailFeatureFromJson(jsonResponse);

        return place;
    }

    public static String fetchlocality(double latitude,double longitude){

        String latlng = Double.toString(latitude)+","+Double.toString(longitude);

        Uri baseuri = Uri.parse(GPS_BASE_URL);
        Uri.Builder uribuilder = baseuri.buildUpon();
        uribuilder.appendQueryParameter("latlng",latlng);
        //uribuilder.appendQueryParameter("result_type","locality");
        uribuilder.appendQueryParameter("key",key);

        URL url = createUrl(uribuilder.toString());
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractlocalitygps(jsonResponse);
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url==null)
            return jsonResponse;


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);        // milli-seconds
            urlConnection.setConnectTimeout(15000);     // milli-seconds
            urlConnection.setRequestMethod("GET");

            if(urlConnection.getResponseCode()== HttpURLConnection.HTTP_OK){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the Nearby Places JSON results.", e);
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**

     * Convert the {@link InputStream} into a String which contains the

     * whole JSON response from the server.

     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**

     * Return a list of {@link BasicLocation} objects that has been built up from

     * parsing the given JSON response.

     */

    private static List<BasicLocation> extractFeatureFromJson(String jsonString){
        if(TextUtils.isEmpty(jsonString)){
            return null;
        }
        List<BasicLocation> locations = new ArrayList<>();
        try{
            JSONObject mainobj = new JSONObject(jsonString);
            JSONArray resultsarray = mainobj.getJSONArray("results");

            for(int i=0; i<resultsarray.length(); i++){
                //basic details
                JSONObject currLocation = resultsarray.getJSONObject(i);
                String name = currLocation.getString("name");
                String address = currLocation.getString("formatted_address");
                if(!currLocation.has("rating")) {
                    Double rating = 0.0;
                }
                else{
                    Double rating = currLocation.getDouble("rating");
                }
                String placeid = currLocation.getString("place_id");
                //photo
                if(!currLocation.has("photos")) {
                    locations.add(new BasicLocation(name,"",address,rating,placeid,"","","",null,-1,-1));
                    continue;
                }
                JSONArray photoarray = currLocation.getJSONArray("photos");
                JSONObject photo1obj = photoarray.getJSONObject(0);
                String img_ref = photo1obj.getString("photo_reference");
                int img_width = photo1obj.getInt("width");
                int img_height = photo1obj.getInt("height");
                locations.add(new BasicLocation(name,"",address,rating,placeid,"","","",img_ref,img_width,img_height));
            }
        }catch (JSONException e){
            Log.e(LOG_TAG, "Error parsing JSON results!");
        }
        return locations;
    }

    // Details Extractor
    private static Details extractDetailFeatureFromJson(String jsonString){
        if(TextUtils.isEmpty(jsonString)){
            return null;
        }
        Details Place = null;
        try{
            JSONObject mainobj = new JSONObject(jsonString);
            JSONObject currplace = mainobj.getJSONObject("result");

            if(mainobj.getString("status").equals("OK")){
                //basic details

                String placeid = currplace.getString("place_id");

                String name,address,phone, website,img_ref;
                Double rating;
                int price,img_width, img_height;
                String[] schedule;
                List<Reviews> reviews;
                List<PlaceImage> images;
                boolean open;

                if(!currplace.has("name")) {
                    name = null;
                }
                else{
                    name = currplace.getString("name");
                }
                if(!currplace.has("formatted_address")) {
                    address = null;
                }
                else{
                    address = currplace.getString("formatted_address");
                }
                if(!currplace.has("formatted_phone_number")) {
                    phone = null;
                }
                else{
                    phone = currplace.getString("formatted_phone_number");
                }

                if(!currplace.has("rating")) {
                    rating = 0.0;
                }
                else{
                    rating = currplace.getDouble("rating");
                }
                if(!currplace.has("price_level")) {
                    price = -1;
                }
                else{
                    price = currplace.getInt("price_level");
                }
                if(!currplace.has("website")) {
                    website = null;
                }
                else{
                    website = currplace.getString("website");
                }

                if(!currplace.has("opening_hours")) {
                    schedule = null;
                    open = false;
                }
                else {
                    JSONObject openobj = currplace.getJSONObject("opening_hours");
                    open = openobj.getBoolean("open_now");
                    JSONArray schedulearray = openobj.getJSONArray("weekday_text");
                    if(schedulearray.length() > 0) {
                        schedule = new String[schedulearray.length()];
                        for (int i = 0; i < schedulearray.length(); i++) {
                            schedule[i] = schedulearray.getString(i);
                        }
                    }
                    else
                    {
                        schedule = null;
                    }
                }

                if(!currplace.has("reviews")) {
                    reviews = null;
                }
                else {
                    JSONArray reviewarray = currplace.getJSONArray("reviews");
                    if (reviewarray.length() > 0) {
                        reviews = new ArrayList<>();
                        for (int i = 0; i < reviewarray.length(); i++) {
                            JSONObject reviewobj = reviewarray.getJSONObject(i);
                            String author_name, author_url, text;
                            int author_rating;
                            if (!reviewobj.has("author_name")) {
                                author_name = null;
                            } else {
                                author_name = reviewobj.getString("author_name");
                            }

                            if (!reviewobj.has("author_url")) {
                                author_url = null;
                            } else {
                                author_url = reviewobj.getString("author_url");
                            }

                            if (!reviewobj.has("text")) {
                                text = null;
                            } else {
                                text = reviewobj.getString("text");
                            }

                            if (!reviewobj.has("rating")) {
                                author_rating = 0;
                            } else {
                                author_rating = reviewobj.getInt("rating");
                            }
                            reviews.add(new Reviews(author_name,text,author_rating,author_url));
                        }
                    }
                    else{
                        reviews = null;
                    }
                }
                //photo
                if(!currplace.has("photos")) {
                    images = null;
                }
                else {
                    JSONArray photoarray = currplace.getJSONArray("photos");
                    if (photoarray.length() > 0) {
                        images = new ArrayList<>();
                        for (int i = 0; i < photoarray.length(); i++) {
                            JSONObject photo1obj = photoarray.getJSONObject(i);
                            img_ref = photo1obj.getString("photo_reference");
                            img_width = photo1obj.getInt("width");
                            img_height = photo1obj.getInt("height");
                            images.add(new PlaceImage(img_ref,img_width,img_height));
                        }
                    } else {
                        images = null;
                    }
                }
                Place = new Details(name, address, rating, placeid, phone, open,schedule, website,reviews,price,images);
                return Place;
            }

        }catch (JSONException e){
            Log.e(LOG_TAG, "Error parsing JSON results!");
        }
        return Place;
    }
    //gps json extractor
    private static String extractlocalitygps(String jsonString){
        if(TextUtils.isEmpty(jsonString)){
            return null;
        }
        String ans = null;

        try{
            JSONObject mainobj = new JSONObject(jsonString);
            JSONArray results = mainobj.getJSONArray("results");
            JSONArray result0 = results.getJSONObject(0).getJSONArray("address_components");
            for(int i=0; i<result0.length(); i++){
                JSONObject resultthis = result0.getJSONObject(i);
                JSONArray trytpe = resultthis.getJSONArray("types");
                Log.d("O","YO");
                if(trytpe.toString().contains("\"locality\"")){
                    ans = resultthis.getString("long_name");
                    Log.d("OUT",ans);
                    break;
                }
            }
            return ans;

        }catch (JSONException e){
            Log.e(LOG_TAG, "Error parsing GPS JSON results!");
        }
        return ans;
    }
}
