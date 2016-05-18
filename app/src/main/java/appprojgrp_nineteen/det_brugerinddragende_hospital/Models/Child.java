package appprojgrp_nineteen.det_brugerinddragende_hospital.Models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Child extends BaseModel {
    public static final String TABLE_NAME = "children";

    public String name;
    public String department;

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("department", department);

        return values;
    }

    public static void createTable(SQLiteDatabase db) {
        String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "department TEXT" +
                ")";

        db.execSQL(CREATE_WEATHER_TABLE);
    }
}
