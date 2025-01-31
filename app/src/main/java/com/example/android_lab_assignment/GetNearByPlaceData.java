package com.example.android_lab_assignment;

import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class GetNearByPlaceData extends AsyncTask<Object, String, String> {
    GoogleMap googleMap;
    String placeData;
    String url;


    @Override
    protected String doInBackground(Object... objects) {
        googleMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        FetchUrl fetchUrl = new FetchUrl();
        try {
            placeData = fetchUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return placeData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearByPlaceList = null;
        DataParser parser = new DataParser();
        nearByPlaceList = parser.parse(s);
        showNearByPlaces(nearByPlaceList);

    }

    private void showNearByPlaces(List<HashMap<String, String>> nearByList)
    {
        for(int i=0;i<nearByList.size();i++)
        {
            HashMap<String, String> place = nearByList.get(i);

            String placeName = place.get("placeName");
            String vicinity = place.get("vicinity");
            double lat = Double.parseDouble(place.get("latitude"));
            double lng = Double.parseDouble(place.get("longitude"));

            LatLng latLng = new LatLng(lat, lng);

            // Marker Option
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(placeName + " : "  + vicinity)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            googleMap.addMarker(markerOptions);
        }
    }
}
