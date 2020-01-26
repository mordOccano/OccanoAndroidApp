package com.e.occanotestsidep.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.e.occanosidetest.models.Log;
import com.e.occanotestsidep.persistence.asyncLog.DeleteAsyncTask;
import com.e.occanotestsidep.persistence.asyncLog.InsertAsyncTask;
import com.e.occanotestsidep.persistence.asyncLog.UpdateAsyncTask;

import java.util.List;

public class LogRepository
{
    private AppDatabase appDatabase;


    public LogRepository(Context context){
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insertLogTask(Log log){
        new InsertAsyncTask(appDatabase.getLogDao()).execute(log);
    }

    public void updateLog(Log log){
        new UpdateAsyncTask(appDatabase.getLogDao()).execute(log);

    }

    public LiveData<List<Log>> retriveLogTask(){
        return appDatabase.getLogDao().getLogs();
    }

    public void deleteLog(Log log){
        new DeleteAsyncTask(appDatabase.getLogDao()).execute(log);
    }
}
