package grp19.det_brugerinddragende_hospital.Models;

import android.content.ContentValues;
import android.database.Cursor;

import grp19.det_brugerinddragende_hospital.Database.DatabaseHelper;

public abstract class BaseModel {
    public int id;

    public String tableName() {
        return getClass().getSimpleName().toLowerCase();
    }

    public abstract ContentValues getContentValues();
    public abstract void populateFromCursor(Cursor cursor);

    public Boolean save() {
        return DatabaseHelper.getInstance().insertModel(this);
    }
}
