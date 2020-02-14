package com.example.android_lab_assignment;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Entity(tableName="favlocation")
public class FavLocation implements Parcelable
{

    @PrimaryKey(autoGenerate = true)

    int id;

    @ColumnInfo(name = "latitude")
    double latitude;

    @ColumnInfo(name = "longitude")
    double longitude;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "address")
    String address = "";

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static Creator<FavLocation> getCREATOR() {
        return CREATOR;
    }

    public FavLocation(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.CANADA);
        date = formatter.format(new Date());
    }

    protected FavLocation(Parcel in) {
        id = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        date = in.readString();
    }

    public static final Creator<FavLocation> CREATOR = new Creator<FavLocation>() {
        @Override
        public FavLocation createFromParcel(Parcel in) {
            return new FavLocation(in);
        }

        @Override
        public FavLocation[] newArray(int size) {
            return new FavLocation[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(date);
    }

    public static class DataParser {
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

    }
}
