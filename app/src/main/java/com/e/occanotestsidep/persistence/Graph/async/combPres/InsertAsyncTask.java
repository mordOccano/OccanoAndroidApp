package com.e.occanotestsidep.persistence.Graph.async.combPres;


import android.os.AsyncTask;

import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.Graph.daoGraph.CombPresDao;
import com.e.occanotestsidep.persistence.LogDao;
import com.e.occanotestsidep.ui.models.CombPresForGraph;

public class InsertAsyncTask extends AsyncTask<CombPresForGraph, Void, Void> {

    private CombPresDao combPresDao;

    public InsertAsyncTask(CombPresDao dao) {
        combPresDao = dao;
    }


    @Override
    protected Void doInBackground(CombPresForGraph... combPresForGraphs) {
        combPresDao.insert(combPresForGraphs);
        return null;
    }
}
