package appprojgrp_nineteen.det_brugerinddragende_hospital.Models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Report extends BaseModel {
    public static final String TABLE_NAME = "report";

    public int remoteId;
    public String name;
    public String comment;
    public int childId;

    // Local variable, not present from API json
    public boolean syncedWithRemote = false;

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("remote_id", remoteId);
        values.put("name", name);
        values.put("comment", comment);
        values.put("child_id", childId);

        values.put("synced_with_remote", syncedWithRemote);

        return values;
    }

    public static void createTable(SQLiteDatabase db) {
        String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                "id INTEGER PRIMARY KEY," +
                "remote_id INTEGER," +
                "name TEXT," +
                "comment TEXT," +
                "child_id INTEGER," +
                "synced_with_remote INTEGER DEFAULT 0" +
                ")";

        db.execSQL(CREATE_WEATHER_TABLE);
    }
}
