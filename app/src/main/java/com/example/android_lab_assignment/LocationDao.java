package com.example.android_lab_assignment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.android_lab_assignment.FavLocation;
import java.util.List;

@Dao
public interface LocationDao
{

    @Insert
    void insert(FavLocation favLocation);

    @Delete
    void delete(FavLocation favLocation);

    @Update
    void update(FavLocation favLocation);

    @Query("Select * from favlocation")
    List<FavLocation> getDefault();


}
