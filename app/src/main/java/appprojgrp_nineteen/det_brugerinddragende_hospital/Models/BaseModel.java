package appprojgrp_nineteen.det_brugerinddragende_hospital.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Database.DatabaseHelper;
import appprojgrp_nineteen.det_brugerinddragende_hospital.MainApplication;

public abstract class BaseModel {
    public int id;

    public String tableName() {
        return getClass().getSimpleName().toLowerCase();
    }

    public abstract ContentValues getContentValues();
    public abstract void populateFromCursor(Cursor cursor);

    public void save() {
        DatabaseHelper.getInstance().insertModel(this);
    }
}
