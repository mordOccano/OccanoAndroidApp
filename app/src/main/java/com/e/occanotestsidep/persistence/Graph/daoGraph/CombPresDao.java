package com.e.occanotestsidep.persistence.Graph.daoGraph;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.ui.models.CombPresForGraph;

import java.util.List;

@Dao
public interface CombPresDao {

    @Insert
    long[] insert(CombPresForGraph... combPresForGraphs);

    @Query("SELECT * FROM comb_pres_details_for_graph")
    LiveData<List<CombPresForGraph>> getCombPresForGraph();

//    @Query("SELECT * FROM logs WHERE id = :id")
//    List<Log> getLogWithCustomQuery(int id);

//    @Query("SELECT * FROM logs WHERE content LIKE :content")
//    List<Log> getLogWithCustomLikeQuery(String content);

    @Delete
    int delete(CombPresForGraph... combPresForGraphs);

    @Update
    int update(CombPresForGraph... combPresForGraphs);
}
