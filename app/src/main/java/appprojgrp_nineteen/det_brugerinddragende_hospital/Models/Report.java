package appprojgrp_nineteen.det_brugerinddragende_hospital.Models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Report extends BaseModel {
    public static final String TABLE_NAME = "report";

    public int remoteId;
    public String comment;
    public int childId;

    public Boolean medicine = false;
    public Boolean food = false;
    public Boolean diaper = false;

    // Local variable, not present from API json
    public boolean syncedWithRemote = false;

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("remote_id", remoteId);
        values.put("comment", comment);
        values.put("child_id", childId);

        values.put("medicine", medicine);
        values.put("food", food);
        values.put("diaper", diaper);

        values.put("synced_with_remote", syncedWithRemote);

        return values;
    }

    @Override
    public Boolean save() {
        if (childId == 0){
            return false;
        }

        return super.save();
    }

    public void populateFromCursor(Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex("id"));
        remoteId = cursor.getInt(cursor.getColumnIndex("remoteId"));
        comment = cursor.getString(cursor.getColumnIndex("comment"));
        childId = cursor.getInt(cursor.getColumnIndex("childId"));

        medicine = cursor.getInt(cursor.getColumnIndex("medicine")) == 1 ? true : false;
        food = cursor.getInt(cursor.getColumnIndex("food")) == 1 ? true : false;
        diaper = cursor.getInt(cursor.getColumnIndex("diaper")) == 1 ? true : false;
    }

    public static void createTable(SQLiteDatabase db) {
        String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                "id INTEGER PRIMARY KEY," +
                "remote_id INTEGER," +
                "comment TEXT," +
                "child_id INTEGER," +
                "synced_with_remote INTEGER DEFAULT 0," +

                "medicine INTEGER DEFAULT 0," +
                "food INTEGER DEFAULT 0," +
                "diaper INTEGER DEFAULT 0" +
                ")";

        db.execSQL(CREATE_WEATHER_TABLE);
    }
}
