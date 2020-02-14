package com.example.android_lab_assignment;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.security.acl.LastOwnerException;

@Database(entities = FavLocation.class , exportSchema = false , version = 1)
public abstract class LocationDB extends RoomDatabase
{

    public static final String DB_NAME = "user_db";

    private static  LocationDB uInstance;


    public static LocationDB getInstance(Context context)
    {
        if(uInstance == null)
        {
            uInstance = Room.databaseBuilder(context.getApplicationContext(), LocationDB.class,LocationDB.DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return uInstance;
    }


    public abstract LocationDao daoObjct();
}
