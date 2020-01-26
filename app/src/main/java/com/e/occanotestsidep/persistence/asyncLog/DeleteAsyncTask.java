package com.e.occanotestsidep.persistence.asyncLog;

import android.os.AsyncTask;

import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.LogDao;


public class DeleteAsyncTask extends AsyncTask<Log, Void, Void> {

    private LogDao mLogDao;

    public DeleteAsyncTask(LogDao dao) {
        mLogDao = dao;
    }

    @Override
    protected Void doInBackground(Log... notes) {
        mLogDao.delete(notes);
        return null;
    }
}
