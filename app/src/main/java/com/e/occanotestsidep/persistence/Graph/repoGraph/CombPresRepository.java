package com.e.occanotestsidep.persistence.Graph.repoGraph;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.AppDatabase;
import com.e.occanotestsidep.persistence.Graph.async.combPres.DeleteAsyncTask;
import com.e.occanotestsidep.persistence.Graph.async.combPres.InsertAsyncTask;
import com.e.occanotestsidep.persistence.Graph.async.combPres.UpdateAsyncTask;
import com.e.occanotestsidep.ui.models.CombPresForGraph;

import java.util.List;

public class CombPresRepository
{
    private AppDatabase appDatabase;


    public CombPresRepository(Context context){
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insertTaskComb(CombPresForGraph combPresForGraph){
        new InsertAsyncTask(appDatabase.getCombPresDao()).execute(combPresForGraph);
    }

    public void updateComb(CombPresForGraph combPresForGraph){
        new UpdateAsyncTask(appDatabase.getCombPresDao()).execute(combPresForGraph);

    }

    public LiveData<List<CombPresForGraph>> retriveCombTask(){
        return appDatabase.getCombPresDao().getCombPresForGraph();
    }

    public void deleteComb(CombPresForGraph combPresForGraph){
        new DeleteAsyncTask(appDatabase.getCombPresDao()).execute(combPresForGraph);
    }
}
