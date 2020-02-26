package com.e.occanotestsidep.persistence.Graph.async.fuel;

import android.os.AsyncTask;

import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.Graph.daoGraph.FuelDao;
import com.e.occanotestsidep.persistence.LogDao;
import com.e.occanotestsidep.ui.models.FuelForGraph;

public class UpdateAsyncTask extends AsyncTask<FuelForGraph, Void, Void> {

    private FuelDao fuelDao;

    public UpdateAsyncTask(FuelDao dao) {
        fuelDao = dao;
    }


    @Override
    protected Void doInBackground(FuelForGraph... fuelForGraphs) {
        fuelDao.update(fuelForGraphs);
        return null;
    }
}
