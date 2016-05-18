package appprojgrp_nineteen.det_brugerinddragende_hospital.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.BaseModel;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Report;

/**
 * Gracefully learned from this source:
 * https://github.com/codepath/android_guides/wiki/Local-Databases-with-SQLiteOpenHelper
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "reportingDatabase";
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Report.createTable(db);
        Child.createTable(db);
        Log.v("DatabaseHelper", "Created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Report.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Child.TABLE_NAME);
            onCreate(db);
        }
    }

    public void insertModel(BaseModel model) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            db.insertOrThrow(model.tableName(), null, model.getContentValues());
            db.setTransactionSuccessful();
            Log.v("DatabaseHelper", "Inserted model");
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }

    public void deleteModel(BaseModel model) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(model.tableName(), "id=" + model.id, null);
            db.setTransactionSuccessful();
            Log.v("DatabaseHelper", "Deleted model successfull");
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }

/*
    private List<WeatherInfo> getAllWeatherInfo(int limit) {
        List<WeatherInfo> weatherInfoList = new ArrayList<>();

        String WEATHER_INFO_SELECT_QUERY = String.format("SELECT * FROM %s ORDER BY id DESC LIMIT " + limit, TABLE_REPORT);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(WEATHER_INFO_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    weatherInfoList.add(weatherFromCursor(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.v("DatabaseHelper", "getAllWeatherInfo exception: " + e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return weatherInfoList;
    }
    */

    /*
    private WeatherInfo weatherFromCursor(Cursor cursor) {
        WeatherInfo newWi = new WeatherInfo();

        newWi.setId(cursor.getInt(cursor.getColumnIndex(KEY_WI_ID)));
        newWi.setDescription(cursor.getString(cursor.getColumnIndex(KEY_WI_DESCRIPTION)));
        newWi.setTemperature(cursor.getDouble(cursor.getColumnIndex(KEY_WI_TEMPERATURE)));
        newWi.setTimestamp(cursor.getInt(cursor.getColumnIndex(KEY_WI_TIMESTAMP)));
        newWi.setCityName(cursor.getString(cursor.getColumnIndex(KEY_WI_CITY_NAME)));
        newWi.setIcon(cursor.getString(cursor.getColumnIndex(KEY_WI_ICON)));
        newWi.setWeatherId(cursor.getInt(cursor.getColumnIndex(KEY_WI_WEATHER_ID)));

        return newWi;
    }

    // Delete all WeatherData
    public void deleteAllWeatherData() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_REPORT, null, null);
            db.setTransactionSuccessful();
            Log.v("DatabaseHelper", "Deleted all weather elements");
        } catch (Exception e) {
            Log.v("DatabaseHelper", "deleteAllWeatherData exception: " + e);
        } finally {
            db.endTransaction();
        }
    }
    */
}

