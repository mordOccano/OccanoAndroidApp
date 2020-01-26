package com.e.occanotestsidep.persistence.asyncLog;

import android.os.AsyncTask;

import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.LogDao;

public class UpdateAsyncTask extends AsyncTask<Log, Void, Void> {

    private LogDao mLogDao;

    public UpdateAsyncTask(LogDao dao) {
        mLogDao = dao;
    }


    @Override
    protected Void doInBackground(Log... logs) {
        mLogDao.update(logs);
        return null;
    }
}
