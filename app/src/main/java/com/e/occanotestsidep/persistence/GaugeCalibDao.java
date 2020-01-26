package com.e.occanotestsidep.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.e.occanosidetest.models.GaugeForCalibration;
import com.e.occanosidetest.models.Log;

import java.util.List;

@Dao
public interface GaugeCalibDao {

    @Insert
    long[] insertgauges(GaugeForCalibration... gauge);

    @Query("SELECT * FROM gauges")
    LiveData<List<GaugeForCalibration>> getGauge();

//    @Query("SELECT * FROM logs WHERE id = :id")
//    List<Log> getLogWithCustomQuery(int id);

//    @Query("SELECT * FROM logs WHERE content LIKE :content")
//    List<Log> getLogWithCustomLikeQuery(String content);

    @Delete
    int delete(GaugeForCalibration... gauge);

    @Update
    int update(GaugeForCalibration... gauge);
}
