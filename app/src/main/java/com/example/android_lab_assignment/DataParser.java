package com.example.android_lab_assignment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String, String> getPlace(JSONObject jsonObject)
    {
        HashMap<String, String> place = new HashMap<>();
        String placeName = "N/A";
        String vicinity = "N/A";
        String latitide = "";
        String longitude = "";
        String reference = "";

        try
        {
            if (!jsonObject.isNull("name"))
                placeName = jsonObject.getString("name");

            if (!jsonObject.isNull("vicinity"))
                placeName = jsonObject.getString("vicinity");

            latitide = jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = jsonObject.getString("reference");

            place.put("placeName", placeName);
            place.put("vivinity", vicinity);
            place.put("latitude", latitide);
            place.put("longitude", longitude);
            place.put("reference", reference);

        }
        catch (JSONException e)
        {
                e.printStackTrace();
        }

        return place;

    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> place = null;

        for(int i=0; i<count; i++)
        {
            try {
                place = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(place);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    public List<HashMap<String,String>> parse (String jsonData)
    {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
    public HashMap<String, String> parseDistance(String jsonData) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getDuration(jsonArray);
    }

    private HashMap<String, String> getDuration(JSONArray googleDirectionJson) {
        HashMap<String, String> googleDirectionMap = new HashMap<>();
        String duration = "";
        String distance = "";

        try {
            duration = googleDirectionJson.getJSONObject(0).getJSONObject("duration").getString("text");
            distance = googleDirectionJson.getJSONObject(0).getJSONObject("distance").getString("text");

            googleDirectionMap.put("duration", duration);
            googleDirectionMap.put("distance", distance);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googleDirectionMap;
    }

    public String[] parseDirections(String jsonData) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPaths(jsonArray);
    }

    public String getPath(JSONObject googlePathJson) {
        String polyLine = "";
        try {
            polyLine = googlePathJson.getJSONObject("polyline").getString("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return polyLine;
    }

    public String[] getPaths(JSONArray googleStepsJson) {
        int count = googleStepsJson.length();
        String[] polylines = new String[count];

        for (int i=0; i<count; i++) {
            try {
                polylines[i] = getPath(googleStepsJson.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return polylines;
    }
}
