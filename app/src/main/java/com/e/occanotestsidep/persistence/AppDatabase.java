package com.e.occanotestsidep.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.e.occanosidetest.models.GaugeForCalibration;
import com.e.occanosidetest.models.Log;

@Database(entities = {Log.class, GaugeForCalibration.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    };
//
//    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE users "
//                    + " ADD COLUMN last_update INTEGER");
//        }
//    };
    public static final String DATABASE_NAME = "app_db";

    private static AppDatabase instance;


    static AppDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME
            )
//                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract LogDao getLogDao();
    public abstract GaugeCalibDao getGaugeCalibDao();
}
