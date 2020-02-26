package com.e.occanotestsidep.persistence.Graph.repoGraph;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.AppDatabase;
import com.e.occanotestsidep.persistence.Graph.async.fuel.DeleteAsyncTask;
import com.e.occanotestsidep.persistence.Graph.async.fuel.InsertAsyncTask;
import com.e.occanotestsidep.persistence.Graph.async.fuel.UpdateAsyncTask;
import com.e.occanotestsidep.ui.models.FuelForGraph;

import java.util.List;

public class FuelRepository
{
    private AppDatabase appDatabase;


    public FuelRepository(Context context){
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insertFuelTask(FuelForGraph fuel){
        new InsertAsyncTask(appDatabase.getFuelDao()).execute(fuel);
    }

    public void updateLog(FuelForGraph fuel){
        new UpdateAsyncTask(appDatabase.getFuelDao()).execute(fuel);

    }

    public LiveData<List<FuelForGraph>> retriveLogTask(){
        return appDatabase.getFuelDao().getFuels();
    }

    public void deleteLog(FuelForGraph fuel){
        new DeleteAsyncTask(appDatabase.getFuelDao()).execute(fuel);
    }
}
