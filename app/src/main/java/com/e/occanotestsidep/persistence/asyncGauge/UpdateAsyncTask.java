package com.e.occanotestsidep.persistence.asyncGauge;

import android.os.AsyncTask;

import com.e.occanosidetest.models.GaugeForCalibration;
import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.GaugeCalibDao;
import com.e.occanotestsidep.persistence.LogDao;

public class UpdateAsyncTask extends AsyncTask<GaugeForCalibration, Void, Void> {

    private GaugeCalibDao calibDao;

    public UpdateAsyncTask(GaugeCalibDao dao) {
        calibDao = dao;
    }


    @Override
    protected Void doInBackground(GaugeForCalibration... gaugeForCalibrations) {
        calibDao.update(gaugeForCalibrations);
        return null;
    }
}
