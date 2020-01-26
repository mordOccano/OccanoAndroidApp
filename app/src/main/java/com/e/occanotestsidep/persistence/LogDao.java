package com.e.occanotestsidep.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.e.occanosidetest.models.Log;

import java.util.List;

@Dao
public interface LogDao {

    @Insert
    long[] insertNotes(Log... logs);

    @Query("SELECT * FROM logs")
    LiveData<List<Log>> getLogs();

//    @Query("SELECT * FROM logs WHERE id = :id")
//    List<Log> getLogWithCustomQuery(int id);

//    @Query("SELECT * FROM logs WHERE content LIKE :content")
//    List<Log> getLogWithCustomLikeQuery(String content);

    @Delete
    int delete(Log... logs);

    @Update
    int update(Log... logs);
}
