package com.e.occanotestsidep.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.e.occanosidetest.models.GaugeForCalibration;
import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.asyncGauge.*;


import java.util.List;

public class GaugeRepository {

    private AppDatabase appDatabase;


    public GaugeRepository(Context context){
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insertGaugeTask(GaugeForCalibration gauge){
        new InsertAsyncTask(appDatabase.getGaugeCalibDao()).execute(gauge);
    }

    public void updateGauge(GaugeForCalibration gauge){
        new UpdateAsyncTask(appDatabase.getGaugeCalibDao()).execute(gauge);

    }

    public LiveData<List<GaugeForCalibration>> retriveGaugeTask(){
        return appDatabase.getGaugeCalibDao().getGauge();
    }

    public void deleteGauge(GaugeForCalibration gauge){
        new DeleteAsyncTask(appDatabase.getGaugeCalibDao()).execute(gauge);
    }
}
