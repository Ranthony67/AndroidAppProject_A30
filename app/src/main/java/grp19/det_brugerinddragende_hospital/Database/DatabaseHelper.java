package grp19.det_brugerinddragende_hospital.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import grp19.det_brugerinddragende_hospital.MainApplication;
import grp19.det_brugerinddragende_hospital.Models.BaseModel;
import grp19.det_brugerinddragende_hospital.Models.Child;
import grp19.det_brugerinddragende_hospital.Models.Report;

/**
 * Gracefully learned from this source:
 * https://github.com/codepath/android_guides/wiki/Local-Databases-with-SQLiteOpenHelper
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "reportingDatabase";
    private static final int DATABASE_VERSION = 12;

    private static DatabaseHelper mInstance = null;

    public static DatabaseHelper getInstance() {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DatabaseHelper(MainApplication.getAppContext());
        }
        return mInstance;
    }

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

    public boolean insertModel(BaseModel model) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            int id = (int) db.insertOrThrow(model.tableName(), null, model.getContentValues());
            db.setTransactionSuccessful();
            model.id = id;

            Log.v("DatabaseHelper", "Inserted model");
        } catch (Exception e) {
            return false;
        } finally {
            db.endTransaction();
        }

        return true;
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

    public <T extends BaseModel> ArrayList<T> getAll(Class<T> cls) throws Exception {
        return getAll(cls, "");
    }

    public <T extends BaseModel> ArrayList<T> getAll(Class<T> cls, String whereClause) throws Exception {
        T info = cls.newInstance();
        String tableName = info.tableName();

        ArrayList<T> objectList = new ArrayList<>();

        String where =  whereClause.isEmpty() ? "" : " WHERE " + whereClause;
        String sql = String.format("SELECT * FROM %s%s ORDER BY id DESC", tableName, where);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    T object = cls.newInstance();
                    object.populateFromCursor(cursor);
                    objectList.add(object);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.v("DatabaseHelper", "GetAll Exception: " + e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return objectList;
    }

    public <T extends BaseModel> T find(Class<T> cls, int id) throws Exception {
        T info = cls.newInstance();
        String tableName = info.tableName();

        String sql = String.format("SELECT * FROM %s WHERE id = %s", tableName, id);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName, null, String.format("id=%s", id), null, null, null, null, null);

        T model = null;

        try {
            cursor.moveToFirst();
            T object = cls.newInstance();
            object.populateFromCursor(cursor);
            model = object;

        } catch (Exception e) {
            Log.v("DatabaseHelper", "Find Exception: " + e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return model;
    }

    public Boolean update(BaseModel object) {
        SQLiteDatabase db = getReadableDatabase();
        int affected = db.update(object.tableName(), object.getContentValues(), "id=" + object.id, null);

        if (affected == 1) {
            return true;
        }

        return false;
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

