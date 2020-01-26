package com.e.occanotestsidep.persistence.asyncGauge;


import android.os.AsyncTask;

import com.e.occanosidetest.models.GaugeForCalibration;
import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.GaugeCalibDao;
import com.e.occanotestsidep.persistence.LogDao;

public class InsertAsyncTask extends AsyncTask<GaugeForCalibration, Void, Void> {

    private GaugeCalibDao calibDao;

    public InsertAsyncTask(GaugeCalibDao dao) {
        calibDao = dao;
    }


    @Override
    protected Void doInBackground(GaugeForCalibration... gaugeForCalibrations) {
        calibDao.insertgauges(gaugeForCalibrations);
        return null;
    }
}
