package com.e.occanotestsidep.persistence.asyncGauge;

import android.os.AsyncTask;

import com.e.occanosidetest.models.GaugeForCalibration;
import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.GaugeCalibDao;
import com.e.occanotestsidep.persistence.LogDao;
import com.github.anastr.speedviewlib.Gauge;


public class DeleteAsyncTask extends AsyncTask<GaugeForCalibration, Void, Void> {

    private GaugeCalibDao calibDao;

    public DeleteAsyncTask(GaugeCalibDao dao) {
        calibDao = dao;
    }


    @Override
    protected Void doInBackground(GaugeForCalibration... gaugeForCalibrations) {
        calibDao.delete(gaugeForCalibrations);
        return null;
    }
}
