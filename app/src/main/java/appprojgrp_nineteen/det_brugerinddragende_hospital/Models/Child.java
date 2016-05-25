package appprojgrp_nineteen.det_brugerinddragende_hospital.Models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Child extends BaseModel {
    public static final String TABLE_NAME = "child";

    public String name;
    public String department;

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("name", name);
        values.put("department", department);

        return values;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public void populateFromCursor(Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex("id"));
        name = cursor.getString(cursor.getColumnIndex("name"));
        department = cursor.getString(cursor.getColumnIndex("department"));
    }

    public static void createTable(SQLiteDatabase db) {
        String CreateTableSql = "CREATE TABLE " + TABLE_NAME +
                "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "department TEXT" +
                ")";

        db.execSQL(CreateTableSql);
    }
}
